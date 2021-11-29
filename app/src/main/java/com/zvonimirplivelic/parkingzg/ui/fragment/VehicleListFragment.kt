package com.zvonimirplivelic.parkingzg.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.ui.adapter.VehicleListAdapter
import com.zvonimirplivelic.parkingzg.viewmodel.ParkingZgViewModel

class VehicleListFragment : Fragment() {

    private lateinit var viewModel: ParkingZgViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var vehicleListAdapter: VehicleListAdapter
    private lateinit var fabAddVehicle: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vehicle_list, container, false)
        setHasOptionsMenu(true)

        vehicleListAdapter = VehicleListAdapter()
        recyclerView = view.findViewById(R.id.rv_vehicle_list)
        fabAddVehicle = view.findViewById(R.id.fab_add_vehicle)

        recyclerView.apply {
            adapter = vehicleListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel = ViewModelProvider(this)[ParkingZgViewModel::class.java]
        viewModel.getAllVehicles.observe(viewLifecycleOwner, { vehicleList ->
            vehicleListAdapter.setData(vehicleList)
        })



        fabAddVehicle.setOnClickListener {
            findNavController().navigate(R.id.action_vehicleListFragment_to_addVehicleFragment)
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.vehicle_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_all -> deleteAllVehicles()
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

}