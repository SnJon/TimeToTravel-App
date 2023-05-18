package ru.wildberries.timetotravel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.wildberries.timetotravel.db.AppDb
import ru.wildberries.timetotravel.dto.FlightsResponse
import ru.wildberries.timetotravel.model.FeedModel
import ru.wildberries.timetotravel.repository.FlightRepository
import ru.wildberries.timetotravel.repository.FlightRepositoryImpl


class FlightViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FlightRepository =
        FlightRepositoryImpl(AppDb.getInstance(context = application).flightDao())

    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data

    init {
        loadFlights()
    }

    fun loadFlights() {
        _data.value = FeedModel(loading = true)
        repository.getAll(object : FlightRepository.Callback<FlightsResponse> {
            override fun onSuccess(data: FlightsResponse) {
                viewModelScope.launch {
                    val flightsResponse = data
                    flightsResponse.flights.forEach { flight ->
                        flight.likeByMe = repository.isFlightLiked(flight.searchToken)
                    }

                    _data.postValue(
                        FeedModel(
                            flights = flightsResponse.flights,
                            empty = flightsResponse.flights.isEmpty()
                        )
                    )
                }

            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun likeByToken(token: String) {
        viewModelScope.launch {
            repository.likeByToken(token)
            updateFlights()
        }
    }

    private suspend fun updateFlights() {
        val currentState = _data.value ?: FeedModel()
        _data.value = currentState.copy(flights = currentState.flights.map {
            it.copy(
                likeByMe = repository.isFlightLiked(it.searchToken)
            )
        })
    }
}