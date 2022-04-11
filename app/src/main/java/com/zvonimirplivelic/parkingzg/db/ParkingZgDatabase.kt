package com.zvonimirplivelic.parkingzg.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zvonimirplivelic.parkingzg.db.converters.Converters
import com.zvonimirplivelic.parkingzg.db.dao.ParkingZgDao
import com.zvonimirplivelic.parkingzg.db.model.Ticket
import com.zvonimirplivelic.parkingzg.db.model.Vehicle

@Database(entities = [Vehicle::class, Ticket::class], version = 6, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ParkingZgDatabase : RoomDatabase() {

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
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}