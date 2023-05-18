package ru.wildberries.timetotravel.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.wildberries.timetotravel.api.FlightApi
import ru.wildberries.timetotravel.api.FlightsRequest
import ru.wildberries.timetotravel.dto.FlightsResponse

class FlightRepositoryImpl: FlightRepository {
    private val likedFlights = mutableMapOf<String, Boolean>()

    private val request = FlightsRequest("LED")
    override fun getAll(callback: FlightRepository.Callback<FlightsResponse>) {
        FlightApi.retrofitService.getAll(request).enqueue(object : Callback<FlightsResponse> {
            override fun onResponse(call: Call<FlightsResponse>, response: Response<FlightsResponse>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                } else {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }
            }

            override fun onFailure(call: Call<FlightsResponse>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }
        })
    }

    override fun likeByToken(token: String) {
        val isLiked = likedFlights[token] ?: false
        likedFlights[token] = !isLiked
    }

    override fun isFlightLiked(token: String): Boolean {
        return likedFlights[token] ?: false
    }
}