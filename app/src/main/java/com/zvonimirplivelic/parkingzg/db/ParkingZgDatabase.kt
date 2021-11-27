package com.zvonimirplivelic.parkingzg.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Vehicle::class], version = 1, exportSchema = false)
public abstract class ParkingZgDatabase : RoomDatabase() {

    abstract fun parkingZgDao(): ParkingZgDao

    companion object {
        @Volatile
        private var INSTANCE: ParkingZgDatabase? = null

        fun getDatabase(context: Context): ParkingZgDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParkingZgDatabase::class.java,
                    "parkingzg_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}