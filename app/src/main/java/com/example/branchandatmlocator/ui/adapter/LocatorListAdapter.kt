package com.example.branchandatmlocator.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.branchandatmlocator.model.Locations

class LocatorListAdapter(
    private val clickListener: (Locations) -> Unit
) {//: ListAdapter<Locations, LocatorListAdapter.LocatorViewHolder>(DiffCallback) {

    /*class LocatorViewHolder(
        private var binding: ListItemLocationsBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(locations: Locations) {
            binding.location = locations
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocatorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LocatorViewHolder(
            ListItemLocationsBinding.inflate(layoutInflater, parent, false)
        )
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Locations>() {
        override fun areItemsTheSame(oldItem: Locations, newItem: Locations): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Locations, newItem: Locations): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locations = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener(locations)
        }
        holder.bind(locations)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentLocationsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    override fun onBindViewHolder(holder: LocatorViewHolder, position: Int) {
        TODO("Not yet implemented")
    }*/
}