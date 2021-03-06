package com.zvonimirplivelic.parkingzg.ui.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.model.Vehicle
import com.zvonimirplivelic.parkingzg.viewmodel.ParkingZgViewModel

const val TAG = "VehInfoFrag"

private const val CAMERA_UPDATE_INTENT_REQUEST_CODE = 0

class VehicleInfoFragment : Fragment() {

    private val args by navArgs<VehicleInfoFragmentArgs>()

    private lateinit var viewModel: ParkingZgViewModel

    private lateinit var ivUpdateVehiclePhoto: ImageView
    private lateinit var ibUpdateCamera: ImageButton
    private lateinit var etUpdateVehicleModel: TextInputLayout
    private lateinit var etUpdateVehicleManufacturer: TextInputLayout
    private lateinit var etUpdateVehicleRegistrationNumber: TextInputLayout
    private lateinit var btnShowPaidTickets : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vehicle_info, container, false)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this)[ParkingZgViewModel::class.java]

        ivUpdateVehiclePhoto = view.findViewById(R.id.iv_update_vehicle_image)
        ibUpdateCamera = view.findViewById(R.id.ib_update_camera_intent)
        etUpdateVehicleModel = view.findViewById(R.id.et_vehicle_info_model)
        etUpdateVehicleManufacturer = view.findViewById(R.id.et_vehicle_info_manufacturer)
        etUpdateVehicleRegistrationNumber =
            view.findViewById(R.id.et_vehicle_info_registration_number)
        btnShowPaidTickets = view.findViewById(R.id.btn_paid_ticket_list)

        ivUpdateVehiclePhoto.setImageBitmap(args.currentVehicle.vehiclePhoto)
        etUpdateVehicleModel.editText?.setText(args.currentVehicle.vehicleModel)
        etUpdateVehicleManufacturer.editText?.setText(args.currentVehicle.vehicleManufacturer)
        etUpdateVehicleRegistrationNumber.editText?.setText(args.currentVehicle.vehicleRegistrationNumber)

        ibUpdateCamera.setOnClickListener {
            launchCamera()
        }

        btnShowPaidTickets.setOnClickListener {
            val action =
                VehicleInfoFragmentDirections
                    .actionVehicleInfoFragmentToPaidTicketListFragment(args.currentVehicle)
            it.findNavController().navigate(action)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_UPDATE_INTENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val takenImage = data?.extras?.get("data") as Bitmap
            ivUpdateVehiclePhoto.setImageBitmap(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.vehicle_info_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> deleteVehicle()
            R.id.action_save -> updateVehicle()
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
        val vehicleModel = etUpdateVehicleModel.editText?.text.toString()
        val vehicleManufacturer = etUpdateVehicleManufacturer.editText?.text.toString()
        val vehicleRegistrationNumber = etUpdateVehicleRegistrationNumber.editText?.text.toString()
        val vehiclePhoto = ivUpdateVehiclePhoto.drawable.toBitmap()

        if (validateUserInput(vehicleModel, vehicleManufacturer, vehicleRegistrationNumber)) {
            val updatedVehicle = Vehicle(
                args.currentVehicle.vehicleId,
                vehicleModel,
                vehicleManufacturer,
                vehicleRegistrationNumber,
                vehiclePhoto
            )
            viewModel.updateVehicle(updatedVehicle)
            Toast.makeText(requireContext(), "Successfully updated vehicle details", Toast.LENGTH_LONG).show()
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

    private fun launchCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(cameraIntent, CAMERA_UPDATE_INTENT_REQUEST_CODE)
        } else {
            Toast.makeText(requireContext(), "Unable to launch camera", Toast.LENGTH_SHORT)
                .show()
        }
    }
}