package com.zvonimirplivelic.parkingzg.db.relations

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.zvonimirplivelic.parkingzg.db.model.Ticket
import com.zvonimirplivelic.parkingzg.db.model.Vehicle

data class VehicleWithTickets(
    @Embedded val vehicle: Vehicle,
    @Relation(
        parentColumn = "vehicleId",
        entityColumn = "ticketId"
    )
    val tickets: List<Ticket>
)
