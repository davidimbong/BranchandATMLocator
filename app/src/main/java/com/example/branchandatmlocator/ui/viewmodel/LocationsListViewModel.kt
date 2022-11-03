package com.example.branchandatmlocator.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchandatmlocator.model.Locations
import com.example.branchandatmlocator.network.LocatorApi
import kotlinx.coroutines.launch

enum class LocationsApiStatus { LOADING, ERROR, DONE }

class LocationsListViewModel : ViewModel() {
    private val _status = MutableLiveData<LocationsApiStatus>()
    private val _locations = MutableLiveData<List<Locations>>()

    val locations: LiveData<List<Locations>> = _locations
    val status: LiveData<LocationsApiStatus> = _status

    init {
        //getLocations()
    }

//    private fun getLocations() {
//        viewModelScope.launch {
//            _status.value = LocationsApiStatus.LOADING
//            try {
//                _locations.value = LocatorApi.retrofitService.getLocations()
//                _status.value = LocationsApiStatus.DONE
//            } catch (e: Exception) {
//                _status.value = LocationsApiStatus.ERROR
//                _locations.value = listOf()
//            }
//        }
//    }
}