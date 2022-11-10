package com.example.branchandatmlocator.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

@SuppressLint("MissingPermission")
class LocationsMapFragment : Fragment(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    companion object {
        private val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private val viewModel: LocationsMapViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            LocationsMapViewModel.LocationsMapFactory(activity.application)
        )[LocationsMapViewModel::class.java]
    }

    private lateinit var callback: OnMapReadyCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestPermissions(getPermissionsRequest())
        return inflater.inflate(R.layout.fragment_map_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callback = OnMapReadyCallback { gMap ->
            gMap.uiSettings.isZoomControlsEnabled = true

            gMap.isMyLocationEnabled = true

            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                if (loc != null) {
                    gMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                loc.latitude, loc.longitude
                            ), 16f
                        )
                    )
                }
            }

            viewModel.locationsList.observe(viewLifecycleOwner) {
                it.forEachIndexed { _, locations ->
                    val coordinates = LatLng(
                        locations.xCoordinate.toDouble(),
                        locations.yCoordinate.toDouble()
                    )
                    gMap.addMarker(MarkerOptions().position(coordinates).title(locations.name))
                }
            }
        }
    }

    private fun getPermissionsRequest() =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (isAllPermissionsGranted(PERMISSIONS)) {             //extension function
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync(callback)
            } else {
                Toast.makeText(context, "Please allow location access", Toast.LENGTH_SHORT).show()
            }
        }

    private fun requestPermissions(
        request: ActivityResultLauncher<Array<String>>
    ) = request.launch(PERMISSIONS)

    private fun Fragment.isAllPermissionsGranted(permissions: Array<String>) = permissions.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
}