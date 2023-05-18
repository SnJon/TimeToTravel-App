package ru.wildberries.timetotravel.dto
data class Flight(
    val startDate: String,
    val endDate: String,
    val startLocationCode: String,
    val endLocationCode: String,
    val startCity: String,
    val endCity: String,
    val serviceClass: String,
    val seats: List<Seat>,
    val price: Int,
    val searchToken: String,
    var likeByMe: Boolean
)
