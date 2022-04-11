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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.parkingzg.R
import com.zvonimirplivelic.parkingzg.db.model.Vehicle
import com.zvonimirplivelic.parkingzg.ui.fragment.VehicleListFragmentDirections
import com.zvonimirplivelic.parkingzg.ui.listener.ZonePaidClickedListener
import com.zvonimirplivelic.parkingzg.util.Constants
import com.zvonimirplivelic.parkingzg.util.DiffUtilExtension.autoNotify
import kotlin.properties.Delegates

const val TAG = "adapter"

class VehicleListAdapter(
    private val listener: ZonePaidClickedListener
) : RecyclerView.Adapter<VehicleListAdapter.VehicleViewHolder>() {

    private var vehicleList: List<Vehicle> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.vehicleId == n.vehicleId }
    }

    class VehicleViewHolder(itemView: View, private val listener: ZonePaidClickedListener) :
        RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        return VehicleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.vehicle_list_item, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val currentVehicle = vehicleList[position]

        val clickableInfoCard = holder.itemView.findViewById<CardView>(R.id.vehicle_list_item)
        val vehicleInfoCardExpandable =
            holder.itemView.findViewById<LinearLayout>(R.id.expandable_buttons_layout)

        val ivVehiclePhoto = holder.itemView.findViewById<ImageView>(R.id.iv_vehicle_image)
        val tvVehicleModel = holder.itemView.findViewById<TextView>(R.id.tv_vehicle_model)
        val tvVehicleManufacturer =
            holder.itemView.findViewById<TextView>(R.id.tv_vehicle_manufacturer)
        val tvVehicleRegistrationNumber =
            holder.itemView.findViewById<TextView>(R.id.tv_vehicle_registration_number)

        val ivVehicleInfo = holder.itemView.findViewById<ImageView>(R.id.iv_vehicle_info)
        val btnZoneOne = holder.itemView.findViewById<Button>(R.id.btn_zone_1)
        val btnZoneTwo = holder.itemView.findViewById<Button>(R.id.btn_zone_2)
        val btnZoneThree = holder.itemView.findViewById<Button>(R.id.btn_zone_3)

        ivVehiclePhoto.setImageBitmap(currentVehicle.vehiclePhoto)
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
            listener.onZonePaidClicked(
                currentVehicle,
                btnZoneOne.text.toString(),
                Constants.ZONE_TEST
            )
        }
        btnZoneTwo.setOnClickListener {
            listener.onZonePaidClicked(
                currentVehicle,
                btnZoneTwo.text.toString(),
                Constants.ZONE_TEST
            )
        }
        btnZoneThree.setOnClickListener {
            listener.onZonePaidClicked(
                currentVehicle,
                btnZoneThree.text.toString(),
                Constants.ZONE_TEST
            )
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
