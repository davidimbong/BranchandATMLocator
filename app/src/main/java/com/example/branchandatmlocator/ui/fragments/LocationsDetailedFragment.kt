package com.example.branchandatmlocator.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.branchandatmlocator.databinding.FragmentLocationsDetailedBinding
import com.example.branchandatmlocator.ui.viewmodel.LocationsDetailedViewModel


class LocationsDetailedFragment : Fragment() {

    private lateinit var binding: FragmentLocationsDetailedBinding

    private val viewModel: LocationsDetailedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLocationsDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val list = viewModel.locationsDetailed
            name.text = list.name
            type.text = list.type
            address.text = list.address

            val qrTag = "QR Tag Available: ${list.qrTag.toBoolean()}"
            val phone = "Phone Number: ${list.phoneNumber}"
            val fax = "Fax Number: ${list.faxNumber}"
            qrtag.text = qrTag
            phoneNumber.text = phone
            faxNumber.text = fax

            button.setOnClickListener {
                val gmmIntentUri =
                    Uri.parse("google.navigation:q=${list.xCoordinate}, ${list.yCoordinate}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
    }
}