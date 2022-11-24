package com.example.branchandatmlocator.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.branchandatmlocator.repository.LocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationsListViewModel @Inject constructor(
    locationsRepository: LocationsRepository,
    application: Application
) : AndroidViewModel(application) {

    val locationsList = locationsRepository.locations

}