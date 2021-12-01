package com.zvonimirplivelic.parkingzg.repository

import androidx.lifecycle.LiveData
import com.zvonimirplivelic.parkingzg.db.dao.ParkingZgDao
import com.zvonimirplivelic.parkingzg.db.model.Vehicle

class ParkingZgRepository(private val parkingZgDao: ParkingZgDao) {
    val getAllVehicles: LiveData<List<Vehicle>> = parkingZgDao.getAllTasks()

    suspend fun addVehicle(vehicle: Vehicle) {
        parkingZgDao.addVehicle(vehicle)
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
}