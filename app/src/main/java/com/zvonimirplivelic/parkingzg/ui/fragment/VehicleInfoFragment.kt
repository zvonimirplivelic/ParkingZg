package com.zvonimirplivelic.parkingzg.ui.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.model.Vehicle
import com.zvonimirplivelic.parkingzg.ui.adapter.PaidTicketListAdapter
import com.zvonimirplivelic.parkingzg.viewmodel.ParkingZgViewModel

const val TAG = "VehInfoFrag"

private const val CAMERA_UPDATE_INTENT_REQUEST_CODE = 0

class VehicleInfoFragment : Fragment() {

    private val args by navArgs<VehicleInfoFragmentArgs>()

    private lateinit var viewModel: ParkingZgViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var ticketListAdapter: PaidTicketListAdapter

    private lateinit var ivUpdateVehiclePhoto: ImageView
    private lateinit var ibUpdateCamera: ImageButton
    private lateinit var etUpdateVehicleModel: EditText
    private lateinit var etUpdateVehicleManufacturer: EditText
    private lateinit var etUpdateVehicleRegistrationNumber: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vehicle_info, container, false)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this)[ParkingZgViewModel::class.java]
        viewModel.getVehicleWithTickets(args.currentVehicle.vehicleId)

        ticketListAdapter = PaidTicketListAdapter()
        recyclerView = view.findViewById(R.id.rv_paid_tickets_list)
        recyclerView.apply {
            adapter = ticketListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }


        ivUpdateVehiclePhoto = view.findViewById(R.id.iv_update_vehicle_image)
        ibUpdateCamera = view.findViewById(R.id.ib_update_camera_intent)
        etUpdateVehicleModel = view.findViewById(R.id.et_vehicle_info_model)
        etUpdateVehicleManufacturer = view.findViewById(R.id.et_vehicle_info_manufacturer)
        etUpdateVehicleRegistrationNumber =
            view.findViewById(R.id.et_vehicle_info_registration_number)

        ivUpdateVehiclePhoto.setImageBitmap(args.currentVehicle.vehiclePhoto)
        etUpdateVehicleModel.setText(args.currentVehicle.vehicleModel)
        etUpdateVehicleManufacturer.setText(args.currentVehicle.vehicleManufacturer)
        etUpdateVehicleRegistrationNumber.setText(args.currentVehicle.vehicleRegistrationNumber)

        ibUpdateCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivityForResult(cameraIntent, CAMERA_UPDATE_INTENT_REQUEST_CODE)
            } else {
                Toast.makeText(requireContext(), "Unable to launch camera", Toast.LENGTH_SHORT)
                    .show()
            }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTicketList.observe(viewLifecycleOwner, { ticketList ->
            Log.d(TAG, "onViewCreated: $ticketList")
            ticketListAdapter.setData(
                ticketList[0].tickets
            )
        })
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
        val vehicleModel = etUpdateVehicleModel.text.toString()
        val vehicleManufacturer = etUpdateVehicleManufacturer.text.toString()
        val vehicleRegistrationNumber = etUpdateVehicleRegistrationNumber.text.toString()
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