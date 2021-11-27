package com.zvonimirplivelic.parkingzg.db

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "vehicle_table")
data class Vehicle(
    @PrimaryKey(autoGenerate = true)
    val vehicleId: Int,
    val vehicleDetails: String,
    val vehicleManufacturer: String,
    val vehicleRegistrationNumber: String
): Parcelable
