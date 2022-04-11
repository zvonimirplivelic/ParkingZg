package com.zvonimirplivelic.parkingzg.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.zvonimirplivelic.parkingzg.db.model.Ticket
import com.zvonimirplivelic.parkingzg.db.model.Vehicle

data class VehicleWithTickets(
    @Embedded val vehicle: Vehicle,
    @Relation(
        parentColumn = "vehicleId",
        entityColumn = "ticketVehicleId"
    )
    val tickets: List<Ticket> = emptyList()
)
