package ru.wildberries.timetotravel.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.wildberries.timetotravel.dto.Flight

class FlightRepositoryInMemoryImpl : FlightRepository {

    private var flights = listOf(
        Flight(
            token = "LED180523MOWY100",
            startDate = "17 мая",
            endDate = "20 июня",
            startLocationCode = "LED",
            endLocationCode = "MOW",
            startCity = "Санкт-Петербург",
            endCity = "Москва",
            price = 4945,
            likeByMe = false
        ),

        Flight(
            token = "LED180523KZNY100",
            startDate = "18 мая",
            endDate = "25 июня",
            startLocationCode = "LED",
            endLocationCode = "KZN",
            startCity = "Санкт-Петербург",
            endCity = "Казань",
            price = 3300,
            likeByMe = false
        ),

        Flight(
            token = "LED190623CEEY101",
            startDate = "19 июня",
            endDate = "30 августа",
            startLocationCode = "LED",
            endLocationCode = "CEE",
            startCity = "Санкт-Петербург",
            endCity = "Череповец",
            price = 4376,
            likeByMe = false
        ),
        Flight(
            token = "LED230623KVKY100",
            startDate = "17 мая",
            endDate = "20 июня",
            startLocationCode = "LED",
            endLocationCode = "KVK",
            startCity = "Санкт-Петербург",
            endCity = "Апатиты",
            price = 4840,
            likeByMe = false
        )
    )

    private val data = MutableLiveData(flights)

    override fun getAll(): LiveData<List<Flight>> = data
    override fun likeByToken(token: String) {
       flights = flights.map {
           if (it.token != token) it else it.copy(likeByMe = !it.likeByMe)
       }
        data.value = flights
    }
}