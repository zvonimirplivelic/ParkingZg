package com.zvonimirplivelic.parkingzg.ui.listener

import com.zvonimirplivelic.parkingzg.db.model.Vehicle

interface ZonePaidClickedListener {
    fun onZonePaidClicked(currentVehicle: Vehicle, zonePaid: String, phoneNumber: String)
}