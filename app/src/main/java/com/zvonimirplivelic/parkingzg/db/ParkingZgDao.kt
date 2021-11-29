package com.zvonimirplivelic.parkingzg.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ParkingZgDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVehicle(vehicle: Vehicle)

    @Update
    suspend fun updateVehicle(vehicle: Vehicle)

    @Delete
    suspend fun deleteVehicle(vehicle: Vehicle)

    @Query("SELECT * FROM vehicle_table ORDER BY vehicleId ASC")
    fun getAllTasks(): LiveData<List<Vehicle>>
}