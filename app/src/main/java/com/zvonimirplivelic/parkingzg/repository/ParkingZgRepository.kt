package com.zvonimirplivelic.parkingzg.repository

import androidx.lifecycle.LiveData
import com.zvonimirplivelic.parkingzg.db.dao.ParkingZgDao
import com.zvonimirplivelic.parkingzg.db.model.Ticket
import com.zvonimirplivelic.parkingzg.db.model.Vehicle

class ParkingZgRepository(private val parkingZgDao: ParkingZgDao) {
    val getAllVehicles: LiveData<List<Vehicle>> = parkingZgDao.getAllVehicles()

    fun getVehicleWithTickets(vehicleId: Int): LiveData<List<Ticket>> {
        return parkingZgDao.getVehicleWithTickets(vehicleId)
    }

    suspend fun addVehicle(vehicle: Vehicle) {
        parkingZgDao.addVehicle(vehicle)
    }

    suspend fun addTicket(ticket: Ticket) {
        parkingZgDao.addTicket(ticket)
    }

    suspend fun updateVehicle(vehicle: Vehicle) {
        parkingZgDao.updateVehicle(vehicle)
    }

    suspend fun deleteVehicle(vehicle: Vehicle) {
        parkingZgDao.deleteVehicle(vehicle)
    }

    fun deleteAllVehicles() {
        parkingZgDao.deleteAllVehicles()
    }

    fun deleteTicketsForCurrentVehicle(vehicleId: Int) {
        parkingZgDao.deleteTicketsForCurrentVehicle(vehicleId)
    }
}