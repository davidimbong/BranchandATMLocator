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

private const val LOCATION_PERMISSION_REQUEST_CODE = 1

@SuppressLint("MissingPermission")
class LocationsMapFragment : Fragment(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, //OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

    companion object {
        private val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>

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

    private lateinit var gMap: GoogleMap
    private lateinit var callback: OnMapReadyCallback

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestPermissions(getPermissionsRequest(), PERMISSIONS)
        return inflater.inflate(R.layout.fragment_map_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callback = OnMapReadyCallback { googleMap ->
            gMap = googleMap
            Log.d("ASD", "gMap initialized")
            gMap.setOnMyLocationButtonClickListener(this)
            gMap.setOnMyLocationClickListener(this)
            gMap.uiSettings.isZoomControlsEnabled = true

            gMap.isMyLocationEnabled = true
            Log.d("ASD", "Location Enabled")

            fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.lastLocation.addOnSuccessListener { lastLocation ->
                if (lastLocation != null) {
                    gMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                lastLocation.latitude, lastLocation.longitude
                            ), 16f
                        )
                    )
                    Log.d("ASD", "Camera zoom to location")
                }
            }
            Log.d("ASD", "fusedLocationClient finished calling")

            viewModel.locationsList.observe(viewLifecycleOwner) {
                Log.d("ASD", "list observed")
                //enableMyLocation()
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
//                gMap.moveCamera(
//                    CameraUpdateFactory.newLatLngZoom(
//                        LatLng(
//                            it[0].xCoordinate.toDouble(), it[0].yCoordinate.toDouble()
//                        ), 20f
//                    )
//                )

//                getCurrentLocation()
//
//                googleMap.animateCamera(
//                    CameraUpdateFactory.newLatLng(
//                        LatLng(
//                            currentLocation.latitude, currentLocation.longitude
//                        )
//                    )
//                )

//    override fun onMapReady(googleMap: GoogleMap) {
//        gMap = googleMap
//        Log.d("ASD", "gMap initialized")
//        //enableMyLocation()
//        gMap.setOnMyLocationButtonClickListener(this)
//        gMap.setOnMyLocationClickListener(this)
//        gMap.uiSettings.isZoomControlsEnabled = true
//        gMap.isMyLocationEnabled = true
//        Log.d("ASD", "Location Enabled")
//
//        fusedLocationClient =
//            LocationServices.getFusedLocationProviderClient(requireContext())
//        fusedLocationClient.lastLocation.addOnSuccessListener { lastLocation ->
//            if(lastLocation != null) {
//                gMap.animateCamera(
//                    CameraUpdateFactory.newLatLngZoom(
//                        LatLng(
//                            lastLocation.latitude, lastLocation.longitude
//                        ), 16f
//                    )
//                )
//                Log.d("ASD", "Camera zoom to location")
//            }
//        }
//    }

    private fun getPermissionsRequest() =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (isAllPermissionsGranted(PERMISSIONS)) {             //extension function
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync(callback)
                Log.d("ASD", "Map initialized")
            } else {
                Toast.makeText(context, "Please allow location access", Toast.LENGTH_SHORT).show()
            }
        }

//    private fun enableMyLocation() {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            gMap.isMyLocationEnabled = true
//            Log.d("ASD", "Location Enabled")
//            fusedLocationClient =
//                LocationServices.getFusedLocationProviderClient(requireContext())
//            fusedLocationClient.lastLocation.addOnSuccessListener { lastLocation ->
//                gMap.animateCamera(
//                    CameraUpdateFactory.newLatLngZoom(
//                        LatLng(
//                            lastLocation.latitude, lastLocation.longitude
//                        ), 16f
//                    )
//                )
//                Log.d("ASD", "Move camera")
//            }
//            return
//        }
//        Log.d("ASD", "Request Permission")
//        val LOCATION_PERMISSIONS = arrayOf(
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        )
//
//        val requestPermissionLauncher =
//            registerForActivityResult(
//                ActivityResultContracts.RequestPermission()
//            ) { isGranted: Boolean ->
//                if (isGranted) {
//                    Log.d("ASD", "Permission granted")
//                    gMap.isMyLocationEnabled = true
//                    Log.d("ASDASD", "Location Enabled")
//                    fusedLocationClient =
//                        LocationServices.getFusedLocationProviderClient(requireContext())
//                    fusedLocationClient.lastLocation.addOnSuccessListener { lastLocation ->
//                        gMap.animateCamera(
//                            CameraUpdateFactory.newLatLngZoom(
//                                LatLng(
//                                    lastLocation.latitude, lastLocation.longitude
//                                ), 16f
//                            )
//                        )
//                        Log.d("ASDASD", "Move camera")
//                    }
//                } else {
//                    Toast.makeText(
//                        context,
//                        "Please allow location access to utilize the app properly",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//    }
//        ActivityCompat.requestPermissions(
//            requireActivity(),
//            arrayOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ),
//            LOCATION_PERMISSION_REQUEST_CODE
//        )
//}

    override fun onMyLocationButtonClick(): Boolean {
        fusedLocationClient.lastLocation.addOnSuccessListener { lastLocation ->
            if (lastLocation != null) {
                gMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            lastLocation.latitude, lastLocation.longitude
                        ), 16f
                    )
                )
            }
        }
        Toast.makeText(context, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(context, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }

//@Suppress("DEPRECATION")
//override fun onRequestPermissionsResult(
//    requestCode: Int,
//    permissions: Array<String>,
//    grantResults: IntArray
//) {
//    if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
//        super.onRequestPermissionsResult(
//            requestCode,
//            permissions,
//            grantResults
//        )
//        Log.d("ASD", "not equal to permission request code")
//        return
//    }
//
//    if (isPermissionGranted(
//            permissions,
//            grantResults,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) || isPermissionGranted(
//            permissions,
//            grantResults,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        )
//    ) {
//        // Enable the my location layer if the permission has been granted.
//        Log.d("ASD", "Permission granted")
//        enableMyLocation()
//        gMap.isMyLocationEnabled = true
//        Log.d("ASDASD", "Location Enabled")
//        fusedLocationClient =
//            LocationServices.getFusedLocationProviderClient(requireContext())
//        fusedLocationClient.lastLocation.addOnSuccessListener { lastLocation ->
//            gMap.animateCamera(
//                CameraUpdateFactory.newLatLngZoom(
//                    LatLng(
//                        lastLocation.latitude, lastLocation.longitude
//                    ), 16f
//                )
//            )
//            Log.d("ASDASD", "Move camera")
//        }
//    } else {
//        Log.d("ASD", "di ko na alam bat nageerror ")
//        Toast.makeText(context, "Please allow location access to utilize the app properly", Toast.LENGTH_SHORT).show()
//    }
//}
//
//private fun isPermissionGranted(
//    permissions: Array<String>,
//    grantResults: IntArray,
//    permission: String
//): Boolean {
//    if ((permissions.isNotEmpty() && grantResults.isNotEmpty()) &&
//        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
//        permissions[0] == permission
//    ) {
//        Log.d("ASD", "$permissions +++++ $grantResults +++++ ${PackageManager.PERMISSION_GRANTED} +++++ $permission ")
//        Log.d("ASD", "TRUE")
//        return true
//    }
//    Log.d("ASD", "$permissions +++++ $grantResults +++++ ${PackageManager.PERMISSION_GRANTED} +++++ $permission ")
//    Log.d("ASD", "FALSE")
//    return false
//}

    fun Fragment.requestPermissions(
        request: ActivityResultLauncher<Array<String>>,
        permissions: Array<String>
    ) = request.launch(permissions)

    fun Fragment.isAllPermissionsGranted(permissions: Array<String>) = permissions.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
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
//                locationManager?.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER, 0, 0f,
//                    locationListener!!
//                )
//            }
//            getCurrentLocation()
//
//            val mapFragment =
//                childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//            mapFragment?.getMapAsync(callback)
//        }
}

