package com.example.branchandatmlocator.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.branchandatmlocator.data.getDatabase
import com.example.branchandatmlocator.repository.LocationsRepository

class LocationsMapViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val locationsRepository = LocationsRepository(getDatabase(application))
    val locationsList = locationsRepository.locations

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