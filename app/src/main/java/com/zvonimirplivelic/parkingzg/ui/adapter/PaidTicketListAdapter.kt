package com.zvonimirplivelic.parkingzg.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.model.Ticket
import java.text.SimpleDateFormat

class PaidTicketListAdapter: RecyclerView.Adapter<PaidTicketListAdapter.PaidTicketViewHolder>() {

    private var ticketList = emptyList<Ticket>()

    class PaidTicketViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaidTicketViewHolder {
        return PaidTicketViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ticket_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: PaidTicketViewHolder,
        position: Int
    ) {
        val ticket = ticketList[position]
        val sdf = SimpleDateFormat("dd.MM.yyyy. HH:mm:ss ")
        val ticketTimeFormatted = sdf.format(ticket.ticketPaidTime)

        val tvTicketTimePaid: TextView = holder.itemView.findViewById(R.id.tv_ticket_time_paid)
        val tvTicketZonePaid: TextView = holder.itemView.findViewById(R.id.tv_ticket_zone_paid)

        tvTicketTimePaid.text = ticketTimeFormatted
        tvTicketZonePaid.text = ticket.ticketPaidZone
    }

    override fun getItemCount(): Int = ticketList.size

    fun setData(tickets: List<Ticket>) {
        this.ticketList = tickets
        notifyDataSetChanged()
    }
}