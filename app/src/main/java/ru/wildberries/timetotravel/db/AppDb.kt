package ru.wildberries.timetotravel.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.wildberries.timetotravel.dao.FlightDao
import ru.wildberries.timetotravel.entity.LikedFlightEntity

@Database(entities = [LikedFlightEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun flightDao(): FlightDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDb::class.java, "app.db")
                .allowMainThreadQueries()
                .build()
    }
}
