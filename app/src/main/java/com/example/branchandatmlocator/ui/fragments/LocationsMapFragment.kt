package com.example.branchandatmlocator.ui.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.branchandatmlocator.R
import com.example.branchandatmlocator.ui.viewmodel.LocationsMapViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationsMapFragment : Fragment() {

    private val args: LocationsMapFragmentArgs by navArgs()
    private var keyword = ""
    private var locType = ""

    private val viewModel: LocationsMapViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            LocationsMapViewModel.Factory(activity.application, keyword, locType)
        )
            .get(LocationsMapViewModel::class.java)
    }

    private lateinit var callback: OnMapReadyCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        keyword = args.keyword
        locType = args.locType
        return inflater.inflate(R.layout.fragment_map_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.locationsList.observe(viewLifecycleOwner) {
            callback = OnMapReadyCallback { googleMap ->
                it.forEachIndexed { index, it ->
                    val coordinates = LatLng(
                        it.xCoordinate.toDouble(),
                        it.yCoordinate.toDouble()
                    )
                    Log.d("ASD", "$keyword $locType")
                    googleMap.addMarker(MarkerOptions().position(coordinates).title(it.name))
                }
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLng(
                        LatLng(
                            it[0].xCoordinate.toDouble(),
                            it[0].yCoordinate.toDouble()
                        )
                    )
                )
            }
//            viewModel.getCoordinates().forEachIndexed { index, it ->
//                val coordinates = LatLng(
//                    it.xCoordinate.toDouble(),
//                    it.yCoordinate.toDouble()
//                )
//                Log.d("ASD", "$keyword $locType")
//                googleMap.addMarker(MarkerOptions().position(coordinates).title(it.name))
//            }
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
        }
    }
}