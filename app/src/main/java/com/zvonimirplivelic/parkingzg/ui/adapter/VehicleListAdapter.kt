package com.zvonimirplivelic.parkingzg.ui.adapter

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.Vehicle
import com.zvonimirplivelic.parkingzg.ui.fragment.VehicleListFragmentDirections

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
        var vehicleInfoCardExpandable = holder.itemView.findViewById<LinearLayout>(R.id.expandable_buttons_layout)

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
}