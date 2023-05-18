package ru.wildberries.timetotravel.api

import com.google.gson.annotations.SerializedName

data class FlightsRequest(
    @SerializedName("startLocationCode") val startLocationCode: String
)