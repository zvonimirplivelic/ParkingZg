package com.zvonimirplivelic.parkingzg.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.telephony.SmsManager
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.model.Ticket
import com.zvonimirplivelic.parkingzg.db.model.Vehicle
import com.zvonimirplivelic.parkingzg.ui.adapter.VehicleListAdapter
import com.zvonimirplivelic.parkingzg.ui.listener.ZonePaidClickedListener
import com.zvonimirplivelic.parkingzg.viewmodel.ParkingZgViewModel
import java.util.*

class VehicleListFragment : Fragment(), ZonePaidClickedListener {

    private lateinit var viewModel: ParkingZgViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var vehicleListAdapter: VehicleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vehicle_list, container, false)
        setHasOptionsMenu(true)

        vehicleListAdapter = VehicleListAdapter(this)
        recyclerView = view.findViewById(R.id.rv_vehicle_list)

        recyclerView.apply {
            adapter = vehicleListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel = ViewModelProvider(this)[ParkingZgViewModel::class.java]
        viewModel.getAllVehicles.observe(viewLifecycleOwner, { vehicleList ->
            vehicleListAdapter.setData(vehicleList)
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.vehicle_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_all -> deleteAllVehicles()
            R.id.action_add_vehicle -> findNavController().navigate(R.id.action_vehicleListFragment_to_addVehicleFragment)
        }
        return super.onOptionsItemSelected(item)

    }

    private fun deleteAllVehicles() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Delete all vehicles?")
            setMessage("Do you want to delete all vehicles?")
            setPositiveButton("Delete all") { _, _ ->

                viewModel.deleteAllVehicles()

                Toast.makeText(
                    requireContext(),
                    "Successfully deleted all vehicles",
                    Toast.LENGTH_LONG
                ).show()
            }
            setNegativeButton("Cancel") { _, _ ->

            }
            create().show()
        }
    }

    override fun onZonePaidClicked(
        currentVehicle: Vehicle,
        zonePaid: String,
        phoneNumber: String
    ) {
        val currentTime: Long = Calendar.getInstance().timeInMillis
        val paidTicket = Ticket(0, zonePaid, currentTime, currentVehicle.vehicleId)
        payTicketDialog(requireContext(), phoneNumber, currentVehicle)
        viewModel.addTicket(paidTicket)
    }

    private fun payTicketDialog(context: Context, phoneNumber: String, currentVehicle: Vehicle) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Pay ticket for registration plate: ${currentVehicle.vehicleRegistrationNumber}?")
            setMessage("Do you want to pay ticket for ${currentVehicle.vehicleManufacturer} ${currentVehicle.vehicleModel} with registration plate ${currentVehicle.vehicleRegistrationNumber}?")
            setPositiveButton("Pay ticket") { _, _ ->
                sendSMS(phoneNumber, currentVehicle)
                Toast.makeText(
                    context,
                    "Message sent",
                    Toast.LENGTH_LONG
                ).show()
            }
            setNegativeButton("Cancel") { _, _ ->

            }
            create().show()
        }
    }

    private fun sendSMS(
        phoneNumber: String,
        currentVehicle: Vehicle
    ) {
        val smsManager: SmsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(
            phoneNumber,
            null,
            currentVehicle.vehicleRegistrationNumber,
            null,
            null
        )
    }

}