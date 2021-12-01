package com.zvonimirplivelic.parkingzg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zvonimirplivelic.parkingzg.db.model.Vehicle
import com.zvonimirplivelic.parkingzg.db.relations.VehicleWithTickets

@Dao
interface ParkingZgDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVehicle(vehicle: Vehicle)

    @Update
    suspend fun updateVehicle(vehicle: Vehicle)

    @Delete
    suspend fun deleteVehicle(vehicle: Vehicle)

    @Query("DELETE FROM vehicle_table")
    fun deleteAllVehicles()

    @Transaction
    @Query("SELECT * FROM vehicle_table WHERE vehicleId = :vehicleId")
    fun getVehicleWithTickets(vehicleId: Int): LiveData<List<VehicleWithTickets>>

    @Query("SELECT * FROM vehicle_table ORDER BY vehicleId ASC")
    fun getAllTasks(): LiveData<List<Vehicle>>
}