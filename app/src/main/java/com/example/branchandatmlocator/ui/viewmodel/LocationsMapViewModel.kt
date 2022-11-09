package com.example.branchandatmlocator.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.branchandatmlocator.data.getDatabase
import com.example.branchandatmlocator.repository.LocationsRepository
import kotlinx.coroutines.launch

class LocationsMapViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val locationsRepository = LocationsRepository(getDatabase(application))
    val locationsList = locationsRepository.locations

//    fun getCoordinates(): List<LatLng> {
//        val latlngList: MutableList<LatLng> = mutableListOf<LatLng>()
//        locationsRepository.locations.value?.forEachIndexed { index, locations ->
//            latlngList.add(
//                LatLng(
//                    locations.xCoordinate.toDouble(),
//                    locations.yCoordinate.toDouble()
//                )
//            )
//        }
//        return latlngList
//    }

//    fun getCoordinates(): List<Locations> {
//        //locationsList.observe(viewLifeCycleOwner)
//        Log.d("ASDASDASD", locationsList.value!!.toString())
//        return locationsList.value!!
//    }

    class LocationsMapFactory(
        val app: Application,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LocationsMapViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LocationsMapViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}