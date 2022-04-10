package com.zvonimirplivelic.parkingzg.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.ui.adapter.PaidTicketListAdapter
import com.zvonimirplivelic.parkingzg.viewmodel.ParkingZgViewModel

class PaidTicketListFragment : Fragment() {

    private val args by navArgs<PaidTicketListFragmentArgs>()

    private lateinit var viewModel: ParkingZgViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var ticketListAdapter: PaidTicketListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_paid_ticket_list, container, false)
        setHasOptionsMenu(true)

        ticketListAdapter = PaidTicketListAdapter()
        recyclerView = view.findViewById(R.id.rv_paid_tickets_list)

        recyclerView.apply {
            adapter = ticketListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel = ViewModelProvider(this)[ParkingZgViewModel::class.java]

        viewModel.getVehicleWithTickets(args.currentVehicle.vehicleId)
            .observe(viewLifecycleOwner) { ticketList ->
                ticketListAdapter.setData(ticketList)
            }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.ticket_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_all -> deleteAllTickets()
        }
        return super.onOptionsItemSelected(item)

    }

    private fun deleteAllTickets() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Delete all tickets?")
            setMessage("Do you want to delete all tickets for this vehicle?")
            setPositiveButton("Delete all") { _, _ ->

                viewModel.deleteTicketsForCurrentVehicle(args.currentVehicle.vehicleId)

                Toast.makeText(
                    requireContext(),
                    "Successfully deleted all tickets",
                    Toast.LENGTH_LONG
                ).show()
            }
            setNegativeButton("Cancel") { _, _ ->

            }
            create().show()
        }
    }

}