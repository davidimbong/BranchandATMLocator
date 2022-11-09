package com.example.branchandatmlocator.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.branchandatmlocator.R
import com.example.branchandatmlocator.ui.viewmodel.LocationsMapViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationsMapFragment : Fragment() {

    private val viewModel: LocationsMapViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            LocationsMapViewModel.LocationsMapFactory(activity.application)
        )
            .get(LocationsMapViewModel::class.java)
    }

    private lateinit var callback: OnMapReadyCallback
    private lateinit var gMap: GoogleMap
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val code = 101

    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.locationsList.observe(viewLifecycleOwner) {
            callback = OnMapReadyCallback { googleMap ->
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())

                it.forEachIndexed { _, it ->
                    val coordinates = LatLng(
                        it.xCoordinate.toDouble(),
                        it.yCoordinate.toDouble()
                    )
                    googleMap.addMarker(MarkerOptions().position(coordinates).title(it.name))
                }
                fusedLocationClient
                googleMap.uiSettings.isZoomControlsEnabled = true
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLng(
                        LatLng(
                            it[0].xCoordinate.toDouble(), it[0].yCoordinate.toDouble()
                        )
                    )
                )
            }
            val mapFragment =
                childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
            //getCurrentLocation()
        }
    }
}

//    @SuppressLint("MissingPermission")
//    private fun getCurrentLocation() {
//
//        ActivityCompat.checkSelfPermission(
//            requireContext(),
//            Manifest.permission.ACCESS_FINE_LOCATION
//        )
//
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                code
//            )
//            return
//        }
//
//        fusedLocationClient.lastLocation.addOnSuccessListener {
//
//                location ->
//
//            if (location != null) {
//
//                currentLocation = location
//            }
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//            if (ContextCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                //locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f,
//                 //   locationListener!!
//                //)
//            }
//            getCurrentLocation()
//
//            val mapFragment =
//                childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//            mapFragment?.getMapAsync(callback)
//        }
//    }
//}