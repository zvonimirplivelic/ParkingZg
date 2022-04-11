package com.zvonimirplivelic.parkingzg.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.model.Vehicle
import com.zvonimirplivelic.parkingzg.viewmodel.ParkingZgViewModel

private const val CAMERA_INTENT_REQUEST_CODE = 0

class AddVehicleFragment : Fragment() {
    private lateinit var viewModel: ParkingZgViewModel

    private lateinit var ivVehicleImage: ImageView
    private lateinit var ibCamera: ImageButton
    private lateinit var etVehicleModel: TextInputLayout
    private lateinit var etVehicleManufacturer: TextInputLayout
    private lateinit var etVehicleRegistrationNumber: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_vehicle, container, false)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this)[ParkingZgViewModel::class.java]

        ivVehicleImage = view.findViewById(R.id.iv_add_vehicle_image)
        ibCamera = view.findViewById(R.id.ib_add_camera_intent)
        etVehicleModel = view.findViewById(R.id.et_vehicle_model)
        etVehicleManufacturer = view.findViewById(R.id.et_vehicle_manufacturer)
        etVehicleRegistrationNumber = view.findViewById(R.id.et_vehicle_registration_number)

        ibCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivityForResult(cameraIntent, CAMERA_INTENT_REQUEST_CODE)
            } else {
                Toast.makeText(requireContext(), "Unable to launch camera", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.vehicle_add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> addVehicleToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_INTENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val takenImage = data?.extras?.get("data") as Bitmap
            ivVehicleImage.setImageBitmap(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun addVehicleToDatabase() {
        val vehicleModel = etVehicleModel.editText?.text.toString()
        val vehicleManufacturer = etVehicleManufacturer.editText?.text.toString()
        val vehicleRegistrationNumber = etVehicleRegistrationNumber.editText?.text.toString()
        val vehicleImage: Bitmap = ivVehicleImage.drawable.toBitmap()

        if (validateUserInput(vehicleModel, vehicleManufacturer, vehicleRegistrationNumber)) {
            val vehicle = Vehicle(
                0,
                vehicleModel,
                vehicleManufacturer,
                vehicleRegistrationNumber,
                vehicleImage
            )
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