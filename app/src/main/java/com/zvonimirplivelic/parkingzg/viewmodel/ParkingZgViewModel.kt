package com.zvonimirplivelic.parkingzg

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.zvonimirplivelic.parkingzg.db.ParkingZgDatabase
import com.zvonimirplivelic.parkingzg.db.Vehicle
import kotlinx.coroutines.launch

class ParkingZgViewModel(application: Application): AndroidViewModel(application) {

    val getAllVehicles: LiveData<List<Vehicle>>
    private val repository: ParkingZgRepository

    init {
        val parkingZgDao = ParkingZgDatabase.getDatabase(application).parkingZgDao()
        repository = ParkingZgRepository(parkingZgDao)
        getAllVehicles = repository.getAllVehicles
    }

    fun addVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            repository.addVehicle(vehicle)
        }
    }
}