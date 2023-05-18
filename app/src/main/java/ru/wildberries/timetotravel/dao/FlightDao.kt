package ru.wildberries.timetotravel.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.wildberries.timetotravel.entity.LikedFlightEntity

@Dao
interface FlightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedFlight(likedFlight: LikedFlightEntity)

    @Query("SELECT * FROM likedFlights WHERE searchToken = :token")
    suspend fun getLikedFlight(token: String): LikedFlightEntity?
}