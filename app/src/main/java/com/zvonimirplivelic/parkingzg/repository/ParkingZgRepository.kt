package com.zvonimirplivelic.parkingzg

import androidx.lifecycle.LiveData
import com.zvonimirplivelic.parkingzg.db.ParkingZgDao
import com.zvonimirplivelic.parkingzg.db.Vehicle

class ParkingZgRepository(private val parkingZgDao: ParkingZgDao) {
    val getAllVehicles: LiveData<List<Vehicle>> = parkingZgDao.getAllTasks()

    suspend fun addVehicle(vehicle: Vehicle) {
        parkingZgDao.addVehicle(vehicle)
    }
}