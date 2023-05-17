package ru.wildberries.timetotravel.dto

data class Flight(
    val token: String,
    val startDate: String,
    val endDate: String,
    val startLocationCode: String,
    val endLocationCode:  String,
    val startCity: String,
    val endCity: String,
    val price: Int,
    val likeByMe: Boolean
)
