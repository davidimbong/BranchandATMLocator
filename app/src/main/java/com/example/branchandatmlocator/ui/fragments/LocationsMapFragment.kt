package com.example.branchandatmlocator.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.branchandatmlocator.R
import com.example.branchandatmlocator.ui.viewmodel.LocationsDetailedViewModel
import com.example.branchandatmlocator.ui.viewmodel.LocationsMapViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("MissingPermission")
@AndroidEntryPoint
class LocationsMapFragment : Fragment(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    companion object {
        private val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private val viewModel: LocationsMapViewModel by viewModels()
    private val detailedViewModel: LocationsDetailedViewModel by activityViewModels()
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
            gMap.clear()
            gMap.uiSettings.isZoomControlsEnabled = true

            gMap.isMyLocationEnabled = true

            //set mapcamera to current location
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

            //populate map with markers of locations from api call
            viewModel.locationsList.observe(viewLifecycleOwner) { it ->
                it.forEachIndexed { _, locations ->
                    val coordinates = LatLng(
                        locations.xCoordinate.toDouble(),
                        locations.yCoordinate.toDouble()
                    )
                    gMap.addMarker(MarkerOptions().position(coordinates).title(locations.name))
                }

                //set listener for markers to go to detailed screen
                gMap.setOnMarkerClickListener { marker ->
                    viewModel.getList(marker.title!!)
                    viewModel.location.observe(viewLifecycleOwner) { locations ->
                        detailedViewModel.locationsDetailed = locations
                        findNavController().navigate(R.id.action_mapLocationsFragment_to_locationsDetailedFragment)
                    }
                    true
                }

                viewModel.location.observe(viewLifecycleOwner) { locations ->

                }
            } // observe ends


        } // callback ends
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