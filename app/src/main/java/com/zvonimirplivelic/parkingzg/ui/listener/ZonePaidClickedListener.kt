package com.zvonimirplivelic.parkingzg.ui.listener

interface ZonePaidClicked {
    fun <T>onZonePaidClicked(item: T, phoneNumber: String, plateNumber: String)

}