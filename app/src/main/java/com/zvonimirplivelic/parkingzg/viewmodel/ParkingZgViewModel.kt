package com.zvonimirplivelic.parkingzg.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.zvonimirplivelic.parkingzg.db.ParkingZgDatabase
import com.zvonimirplivelic.parkingzg.db.Vehicle
import com.zvonimirplivelic.parkingzg.repository.ParkingZgRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParkingZgViewModel(application: Application) : AndroidViewModel(application) {

    val getAllVehicles: LiveData<List<Vehicle>>
    private val repository: ParkingZgRepository

    init {
        val parkingZgDao = ParkingZgDatabase.getDatabase(application).parkingZgDao()
        repository = ParkingZgRepository(parkingZgDao)
        getAllVehicles = repository.getAllVehicles
    }

    fun addVehicle(vehicle: Vehicle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addVehicle(vehicle)
        }
    }

    fun updateVehicle(vehicle: Vehicle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateVehicle(vehicle)
        }
    }

    fun deleteVehicle(vehicle: Vehicle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteVehicle(vehicle)
        }
    }

    fun deleteAllVehicles() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllVehicles()
        }
    }
}