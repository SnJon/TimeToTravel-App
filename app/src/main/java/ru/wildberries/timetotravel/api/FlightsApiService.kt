package ru.wildberries.timetotravel.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.wildberries.timetotravel.BuildConfig
import ru.wildberries.timetotravel.dto.FlightsResponse

private const val BASE_URL = "${BuildConfig.BASE_URL}/"

private val logging = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private val okhttp = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okhttp)
    .build()

interface FlightsApiService {
    @Headers("accept: application/json, text/plain, */*", "content-type: application/json")
    @POST("GetCheap")
    fun getAll(@Body request: FlightsRequest): Call<FlightsResponse>
}

object FlightApi {
    val retrofitService: FlightsApiService by lazy {
        retrofit.create(FlightsApiService::class.java)
    }
}