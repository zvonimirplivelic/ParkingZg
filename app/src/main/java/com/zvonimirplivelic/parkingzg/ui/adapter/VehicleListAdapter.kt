package com.zvonimirplivelic.parkingzg.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.telephony.SmsManager
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.model.Vehicle
import com.zvonimirplivelic.parkingzg.ui.fragment.VehicleListFragmentDirections
import com.zvonimirplivelic.parkingzg.util.Constants
import timber.log.Timber
import java.util.*
const val TAG = "adapter"
class VehicleListAdapter : RecyclerView.Adapter<VehicleListAdapter.VehicleViewHolder>() {

    private var vehicleList = emptyList<Vehicle>()

    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        return VehicleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.vehicle_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val currentVehicle = vehicleList[position]

        var clickableInfoCard = holder.itemView.findViewById<CardView>(R.id.vehicle_list_item)
        var vehicleInfoCardExpandable =
            holder.itemView.findViewById<LinearLayout>(R.id.expandable_buttons_layout)

        var tvVehicleModel = holder.itemView.findViewById<TextView>(R.id.tv_vehicle_model)
        var tvVehicleManufacturer =
            holder.itemView.findViewById<TextView>(R.id.tv_vehicle_manufacturer)
        var tvVehicleRegistrationNumber =
            holder.itemView.findViewById<TextView>(R.id.tv_vehicle_registration_number)

        var ivVehicleInfo = holder.itemView.findViewById<ImageView>(R.id.iv_vehicle_info)
        var btnZoneOne = holder.itemView.findViewById<Button>(R.id.btn_zone_1)
        var btnZoneTwo = holder.itemView.findViewById<Button>(R.id.btn_zone_2)
        var btnZoneThree = holder.itemView.findViewById<Button>(R.id.btn_zone_3)

        tvVehicleModel.text = currentVehicle.vehicleModel
        tvVehicleManufacturer.text = currentVehicle.vehicleManufacturer
        tvVehicleRegistrationNumber.text = currentVehicle.vehicleRegistrationNumber

        ivVehicleInfo.setOnClickListener {
            val action =
                VehicleListFragmentDirections
                    .actionVehicleListFragmentToVehicleInfoFragment(currentVehicle)
            it.findNavController().navigate(action)
        }

        clickableInfoCard.setOnClickListener {
            expandCardView(vehicleInfoCardExpandable, clickableInfoCard)
        }

        btnZoneOne.setOnClickListener {
            val currentTime: Long = Calendar.getInstance().timeInMillis

            Log.d(TAG, "onBindViewHolder: $currentTime")
            Timber.d("Time:$currentTime")
            //            payTicket(holder.itemView.context, Constants.ZONE_ONE, currentVehicle)
//            payTicketDialog(
//                holder.itemView.context,
//                Constants.ZONE_TEST,
//                currentVehicle
//            )
        }
        btnZoneTwo.setOnClickListener {
//            payTicket(holder.itemView.context, Constants.ZONE_TWO, currentVehicle)
//            payTicketDialog(
//                holder.itemView.context,
//                Constants.ZONE_TEST,
//                currentVehicle
//            )
        }
        btnZoneThree.setOnClickListener {
//            payTicket(holder.itemView.context, Constants.ZONE_THREE, currentVehicle)
//            payTicketDialog(
//                holder.itemView.context,
//                Constants.ZONE_TEST,
//                currentVehicle
//            )
        }
    }

    override fun getItemCount(): Int = vehicleList.size

    fun setData(vehicles: List<Vehicle>) {
        this.vehicleList = vehicles
        notifyDataSetChanged()
    }

    private fun expandCardView(
        expandableLayout: LinearLayout,
        expandableClickable: CardView
    ) {
        if (expandableLayout.visibility == View.GONE) {
            TransitionManager.beginDelayedTransition(expandableClickable, AutoTransition())
            expandableLayout.visibility = View.VISIBLE
        } else {
            TransitionManager.beginDelayedTransition(expandableClickable, AutoTransition())
            expandableLayout.visibility = View.GONE
        }
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
