package com.zvonimirplivelic.parkingzg.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.model.Vehicle
import com.zvonimirplivelic.parkingzg.viewmodel.ParkingZgViewModel

class VehicleInfoFragment : Fragment() {

    private val args by navArgs<VehicleInfoFragmentArgs>()

    private lateinit var viewModel: ParkingZgViewModel

    private lateinit var etUpdateVehicleModel: EditText
    private lateinit var etUpdateVehicleManufacturer: EditText
    private lateinit var etUpdateVehicleRegistrationNumber: EditText
    private lateinit var btnUpdateVehicle: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vehicle_info, container, false)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this)[ParkingZgViewModel::class.java]

        etUpdateVehicleModel = view.findViewById(R.id.et_vehicle_info_model)
        etUpdateVehicleManufacturer = view.findViewById(R.id.et_vehicle_info_manufacturer)
        etUpdateVehicleRegistrationNumber =
            view.findViewById(R.id.et_vehicle_info_registration_number)
        btnUpdateVehicle = view.findViewById(R.id.btn_update_info)


        etUpdateVehicleModel.setText(args.currentVehicle.vehicleModel)
        etUpdateVehicleManufacturer.setText(args.currentVehicle.vehicleManufacturer)
        etUpdateVehicleRegistrationNumber.setText(args.currentVehicle.vehicleRegistrationNumber)

        btnUpdateVehicle.setOnClickListener {
            updateVehicle()
        }

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.vehicle_info_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> deleteVehicle()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun deleteVehicle() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Delete ${args.currentVehicle.vehicleManufacturer} ${args.currentVehicle.vehicleModel}?")
            setMessage("Do you want to delete ${args.currentVehicle.vehicleManufacturer} ${args.currentVehicle.vehicleModel} from the list?")
            setPositiveButton("Delete") { _, _ ->

                viewModel.deleteVehicle(args.currentVehicle)

                Toast.makeText(
                    requireContext(),
                    "Successfully deleted ${args.currentVehicle.vehicleManufacturer} ${args.currentVehicle.vehicleModel}",
                    Toast.LENGTH_LONG
                ).show()

                findNavController().navigate(R.id.action_vehicleInfoFragment_to_vehicleListFragment)
            }
            setNegativeButton("No") { _, _ ->

            }
            create().show()
        }
    }


    private fun updateVehicle() {
        val vehicleModel = etUpdateVehicleModel.text.toString()
        val vehicleManufacturer = etUpdateVehicleManufacturer.text.toString()
        val vehicleRegistrationNumber = etUpdateVehicleRegistrationNumber.text.toString()

        if (validateUserInput(vehicleModel, vehicleManufacturer, vehicleRegistrationNumber)) {
            val updatedVehicle = Vehicle(
                args.currentVehicle.vehicleId,
                vehicleModel,
                vehicleManufacturer,
                vehicleRegistrationNumber
            )
            viewModel.updateVehicle(updatedVehicle)
            Toast.makeText(requireContext(), "Successfully updated task", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_vehicleInfoFragment_to_vehicleListFragment)
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