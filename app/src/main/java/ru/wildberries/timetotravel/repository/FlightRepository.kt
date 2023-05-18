package ru.wildberries.timetotravel.repository

import ru.wildberries.timetotravel.dto.FlightsResponse

interface FlightRepository {
    fun getAll(callback: Callback<FlightsResponse>)
    suspend fun likeByToken(token: String)

    suspend fun isFlightLiked(token: String): Boolean
    interface Callback<T> {
        fun onSuccess(data: T) {}
        fun onError(e: Exception) {}
    }
}