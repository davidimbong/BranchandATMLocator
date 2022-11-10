package com.example.branchandatmlocator.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.branchandatmlocator.databinding.ListItemLocationsBinding
import com.example.branchandatmlocator.model.Locations

class LocatorListAdapter(private val locationsList: List<Locations>,private val clickListener: (Locations) -> Unit) :
    RecyclerView.Adapter<LocatorListAdapter.LocatorListViewHolder>() {

    class LocatorListViewHolder(private var binding: ListItemLocationsBinding) :
        ViewHolder(binding.root) {

        fun bind(locations: Locations) {
            Log.d("ASD", "Location in bind: $locations")
            binding.txtName.text = locations.name
            binding.txtAddress.text = locations.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocatorListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LocatorListViewHolder(
            ListItemLocationsBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LocatorListViewHolder, position: Int) {
        val location = locationsList[position]
        Log.d("ASD", "position: $position")
        Log.d("ASD", "Location in onBind: $location")
        holder.itemView.setOnClickListener {
            clickListener(location)
        }
        holder.bind(location)
    }

    override fun getItemCount(): Int {
        Log.d("ASD", "list size: ${locationsList.size}")
        return locationsList.size
    }


}