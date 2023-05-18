package ru.wildberries.timetotravel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.wildberries.timetotravel.dto.FlightsResponse
import ru.wildberries.timetotravel.model.FeedModel
import ru.wildberries.timetotravel.repository.FlightRepository
import ru.wildberries.timetotravel.repository.FlightRepositoryImpl


class FlightViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FlightRepository = FlightRepositoryImpl()

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
                _data.postValue(
                    FeedModel(
                        flights = data.flights.map { it.copy(likeByMe = repository.isFlightLiked(it.searchToken)) },
                        empty = data.flights.isEmpty()
                    )
                )
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun likeByToken(token: String) {
        repository.likeByToken(token)
        updateFlights()
    }

    private fun updateFlights() {
        val currentState = _data.value ?: FeedModel()
        _data.value = currentState.copy(flights = currentState.flights.map {
            it.copy(
                likeByMe = repository.isFlightLiked(it.searchToken)
            )
        })
    }
}