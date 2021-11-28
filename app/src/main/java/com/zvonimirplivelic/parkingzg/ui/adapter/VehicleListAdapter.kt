package com.zvonimirplivelic.parkingzg.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.Vehicle

class VehicleListAdapter: RecyclerView.Adapter<VehicleListAdapter.VehicleViewHolder>() {

    private var vehicleList = emptyList<Vehicle>()

    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        return VehicleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.vehicle_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val currentVehicle = vehicleList[position]
        var tvVehicleModel = holder.itemView.findViewById<TextView>(R.id.tv_vehicle_model)
        var tvVehicleManufacturer = holder.itemView.findViewById<TextView>(R.id.tv_vehicle_manufacturer)
        var tvVehicleRegistrationNumber = holder.itemView.findViewById<TextView>(R.id.tv_vehicle_registration_number)

        tvVehicleModel.text = currentVehicle.vehicleModel
        tvVehicleManufacturer.text = currentVehicle.vehicleManufacturer
        tvVehicleRegistrationNumber.text = currentVehicle.vehicleRegistrationNumber

    }

    override fun getItemCount(): Int = vehicleList.size

    fun setData(vehicles: List<Vehicle>) {
        this.vehicleList = vehicles
        notifyDataSetChanged()
    }
}