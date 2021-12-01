package com.zvonimirplivelic.parkingzg.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.telephony.SmsManager
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.Vehicle
import com.zvonimirplivelic.parkingzg.ui.fragment.VehicleListFragmentDirections
import com.zvonimirplivelic.parkingzg.util.Constants

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
//            payTicket(holder.itemView.context, Constants.ZONE_ONE, currentVehicle)
            payTicket(
                holder.itemView.context,
                Constants.ZONE_TEST,
                currentVehicle
            )
        }
        btnZoneTwo.setOnClickListener {
//            payTicket(holder.itemView.context, Constants.ZONE_TWO, currentVehicle)
            payTicket(
                holder.itemView.context,
                Constants.ZONE_TEST,
                currentVehicle
            )
        }
        btnZoneThree.setOnClickListener {
//            payTicket(holder.itemView.context, Constants.ZONE_THREE, currentVehicle)
        }
        payTicket(
            holder.itemView.context,
            Constants.ZONE_TEST,
            currentVehicle
        )
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

    private fun payTicket(context: Context, phoneNumber: String, currentVehicle: Vehicle) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Pay ticket for registration plate: ${currentVehicle.vehicleRegistrationNumber}?")
            setMessage("Do you want to pay ticket for ${currentVehicle.vehicleManufacturer} ${currentVehicle.vehicleModel} with registration plate ${currentVehicle.vehicleRegistrationNumber}?")
            setPositiveButton("Pay ticket") { _, _ ->

                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(phoneNumber, null, currentVehicle.vehicleRegistrationNumber, null, null)
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
}
