package com.zvonimirplivelic.parkingzg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zvonimirplivelic.parkingzg.db.model.Ticket
import com.zvonimirplivelic.parkingzg.db.model.Vehicle
import com.zvonimirplivelic.parkingzg.db.relations.VehicleWithTickets

@Dao
interface ParkingZgDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVehicle(vehicle: Vehicle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTicket(ticket: Ticket)

    @Update
    suspend fun updateVehicle(vehicle: Vehicle)

    @Delete
    suspend fun deleteVehicle(vehicle: Vehicle)

    @Query("DELETE FROM vehicle_table")
    fun deleteAllVehicles()

    @Query("SELECT * FROM vehicle_table WHERE vehicleId =:vehicleId")
    suspend fun getVehicleWithTickets(vehicleId: Int): List<VehicleWithTickets>

    @Query("SELECT * FROM vehicle_table ORDER BY vehicleId ASC")
    fun getAllVehicles(): LiveData<List<Vehicle>>
}