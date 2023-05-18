package ru.wildberries.timetotravel.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "likedFlights")
data class LikedFlightEntity(
    @PrimaryKey val searchToken: String,
    val isLiked: Boolean
)