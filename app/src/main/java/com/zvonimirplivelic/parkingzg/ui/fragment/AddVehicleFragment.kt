package com.zvonimirplivelic.parkingzg.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.Vehicle
import com.zvonimirplivelic.parkingzg.viewmodel.ParkingZgViewModel
import timber.log.Timber

class AddVehicleFragment : Fragment() {
    private lateinit var viewModel: ParkingZgViewModel

    private lateinit var etVehicleModel: EditText
    private lateinit var etVehicleManufacturer: EditText
    private lateinit var etVehicleRegistrationNumber: EditText
    private lateinit var btnAddVehicle: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_vehicle, container, false)

        viewModel = ViewModelProvider(this)[ParkingZgViewModel::class.java]

        etVehicleModel = view.findViewById(R.id.et_vehicle_model)
        etVehicleManufacturer = view.findViewById(R.id.et_vehicle_manufacturer)
        etVehicleRegistrationNumber = view.findViewById(R.id.et_vehicle_registration_number)
        btnAddVehicle = view.findViewById(R.id.btn_add_vehicle)

        btnAddVehicle.setOnClickListener {
            addVehicleToDatabase()
        }
        return view
    }

    private fun addVehicleToDatabase() {
        val vehicleModel = etVehicleModel.text.toString()
        val vehicleManufacturer = etVehicleManufacturer.text.toString()
        val vehicleRegistrationNumber = etVehicleRegistrationNumber.text.toString()

        if (validateUserInput(vehicleModel, vehicleManufacturer, vehicleRegistrationNumber)) {
            val vehicle = Vehicle(0, vehicleModel, vehicleManufacturer, vehicleRegistrationNumber)
            viewModel.addVehicle(vehicle)
            Toast.makeText(requireContext(), "Vehicle successfully added!", Toast.LENGTH_LONG)
                .show()

            findNavController().navigate(R.id.action_addVehicleFragment_to_vehicleListFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out the fields!", Toast.LENGTH_LONG)
                .show()
        }
    }


    private fun validateUserInput(
        vehicleModel: String,
        vehicleManufacturer: String,
        vehicleRegistrationNumber: String
    ): Boolean {
        return !(TextUtils.isEmpty(vehicleModel) ||
                TextUtils.isEmpty(vehicleManufacturer) ||
                TextUtils.isEmpty(vehicleRegistrationNumber))
    }
}