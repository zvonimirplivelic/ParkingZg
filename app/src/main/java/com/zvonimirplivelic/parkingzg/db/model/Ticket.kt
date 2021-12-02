package com.zvonimirplivelic.parkingzg.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ticket_table"
)

data class Ticket(
    @PrimaryKey(autoGenerate = true)
    val ticketId: Int,
    val ticketPaidZone: String,
    val ticketPaidTime: Long,
    val ticketVehicleId: Int
)