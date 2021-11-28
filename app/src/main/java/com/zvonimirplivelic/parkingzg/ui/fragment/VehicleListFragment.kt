package com.zvonimirplivelic.parkingzg.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
}