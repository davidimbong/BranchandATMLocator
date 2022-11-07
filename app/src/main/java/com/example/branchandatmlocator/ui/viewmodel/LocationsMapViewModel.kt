package com.example.branchandatmlocator.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.branchandatmlocator.data.getDatabase
import com.example.branchandatmlocator.model.Locations
import com.example.branchandatmlocator.repository.LocationsRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class LocationsMapViewModel(
    application: Application,
    private val keyword: String,
    private val type: String
) : AndroidViewModel(application) {

    private val locationsRepository = LocationsRepository(getDatabase(application))
    val locationsList = locationsRepository.locations

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            locationsRepository.refreshRepository(keyword, type)
        }
    }

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

    fun getCoordinates(): List<Locations> {
        return locationsRepository.locations.value!!
    }

    class Factory(
        val app: Application,
        val keyword: String,
        val type: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LocationsMapViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LocationsMapViewModel(app, keyword, type) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}