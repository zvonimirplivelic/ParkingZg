package com.zvonimirplivelic.parkingzg.db.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "vehicle_table")
data class Vehicle(
    @PrimaryKey(autoGenerate = true)
    val vehicleId: Int,
    val vehicleModel: String,
    val vehicleManufacturer: String,
    val vehicleRegistrationNumber: String,
    val vehiclePhoto: Bitmap
): Parcelable
