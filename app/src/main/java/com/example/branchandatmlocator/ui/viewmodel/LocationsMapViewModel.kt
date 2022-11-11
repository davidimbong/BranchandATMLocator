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
    val location = locationsRepository.location

    fun getList(name: String){
        viewModelScope.launch {
            locationsRepository.getLocation(name)
        }
    }

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