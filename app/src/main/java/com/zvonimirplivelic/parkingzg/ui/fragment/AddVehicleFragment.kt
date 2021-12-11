package com.zvonimirplivelic.parkingzg.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.model.Vehicle
import com.zvonimirplivelic.parkingzg.viewmodel.ParkingZgViewModel

private const val CAMERA_INTENT_REQUEST_CODE = 0

class AddVehicleFragment : Fragment() {
    private lateinit var viewModel: ParkingZgViewModel

    private lateinit var ivVehicleImage: ImageView
    private lateinit var ibCamera: ImageButton
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

        ivVehicleImage = view.findViewById(R.id.iv_add_vehicle_image)
        ibCamera = view.findViewById(R.id.ib_update_camera_intent)
        etVehicleModel = view.findViewById(R.id.et_vehicle_model)
        etVehicleManufacturer = view.findViewById(R.id.et_vehicle_manufacturer)
        etVehicleRegistrationNumber = view.findViewById(R.id.et_vehicle_registration_number)
        btnAddVehicle = view.findViewById(R.id.btn_add_vehicle)

        ibCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivityForResult(cameraIntent, CAMERA_INTENT_REQUEST_CODE)
            } else {
                Toast.makeText(requireContext(), "Unable to launch camera", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        btnAddVehicle.setOnClickListener {
            addVehicleToDatabase()
        }
        return view
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
        val vehicleModel = etVehicleModel.text.toString()
        val vehicleManufacturer = etVehicleManufacturer.text.toString()
        val vehicleRegistrationNumber = etVehicleRegistrationNumber.text.toString()
        val vehicleImage: Bitmap = ivVehicleImage.drawable.toBitmap()

        if (validateUserInput(vehicleModel, vehicleManufacturer, vehicleRegistrationNumber)) {
            val vehicle = Vehicle(0, vehicleModel, vehicleManufacturer, vehicleRegistrationNumber, vehicleImage)
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