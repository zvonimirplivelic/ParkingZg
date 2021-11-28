package com.zvonimirplivelic.parkingzg.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ParkingZgDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addVehicle(vehicle: Vehicle)

    @Update
    fun updateVehicle(vehicle: Vehicle)

    @Query("SELECT * FROM vehicle_table ORDER BY vehicleId ASC")
    fun getAllTasks(): LiveData<List<Vehicle>>
}