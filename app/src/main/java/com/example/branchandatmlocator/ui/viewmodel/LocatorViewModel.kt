package com.example.branchandatmlocator.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.branchandatmlocator.data.LocatorDao
import com.example.branchandatmlocator.model.Locations

class LocatorViewModel(
    private val locatorDao: LocatorDao
) :ViewModel() {

    fun retrieveLocations(name: String): LiveData<List<Locations>> {
        return locatorDao.getLocations(name).asLiveData()
    }

    class LocatorViewModelFactory(private val locatorDao: LocatorDao) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LocatorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LocatorViewModel(locatorDao) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}