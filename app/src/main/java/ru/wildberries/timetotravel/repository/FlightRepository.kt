package ru.wildberries.timetotravel.repository

import androidx.lifecycle.LiveData
import ru.wildberries.timetotravel.dto.Flight

interface FlightRepository {
    fun getAll() : LiveData<List<Flight>>
    fun likeByToken(token: String)
}