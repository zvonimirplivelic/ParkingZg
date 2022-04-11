package com.zvonimirplivelic.parkingzg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zvonimirplivelic.parkingzg.db.model.Ticket
import com.zvonimirplivelic.parkingzg.db.model.Vehicle

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

    @Query("DELETE FROM ticket_table WHERE ticketVehicleId = :vehicleId")
    fun deleteTicketsForCurrentVehicle(vehicleId: Int)

    @Transaction
    @Query("SELECT * FROM ticket_table WHERE ticketVehicleId = :vehicleId")
    fun getVehicleWithTickets(vehicleId: Int): LiveData<List<Ticket>>

    @Query("SELECT * FROM vehicle_table ORDER BY vehicleId ASC")
    fun getAllVehicles(): LiveData<List<Vehicle>>
}