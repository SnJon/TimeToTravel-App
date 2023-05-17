package ru.wildberries.timetotravel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.wildberries.timetotravel.repository.FlightRepository
import ru.wildberries.timetotravel.repository.FlightRepositoryInMemoryImpl

class FlightViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FlightRepository = FlightRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun likeByToken(token: String) = repository.likeByToken(token)
}