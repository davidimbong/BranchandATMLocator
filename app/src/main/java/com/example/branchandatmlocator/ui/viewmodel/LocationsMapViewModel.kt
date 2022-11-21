package com.example.branchandatmlocator.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchandatmlocator.repository.LocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsMapViewModel @Inject constructor(
    private val locationsRepository: LocationsRepository,
    application: Application
) : AndroidViewModel(application) {

    val locationsList = locationsRepository.locations
    val location = locationsRepository.location

    fun getList(name: String){
        viewModelScope.launch {
            locationsRepository.getLocation(name)
        }
    }
}