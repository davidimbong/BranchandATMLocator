package com.example.branchandatmlocator.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.branchandatmlocator.data.LocatorDao
import com.example.branchandatmlocator.model.Locations
import com.example.branchandatmlocator.model.asDatabaseModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsRepository @Inject constructor(
    private val locatorDao: LocatorDao
){

    val locations: LiveData<List<Locations>> =
        Transformations.map(locatorDao.getLocations()) {
            it.asDatabaseModel()
        }

    val location = MutableLiveData<Locations>()

    suspend fun refreshRepository(list: List<Locations>) {
        withContext(Dispatchers.IO) {
            locatorDao.deleteAll()
            locatorDao.insertAll(list.asDatabaseModel())
        }
    }

    suspend fun getLocation(name: String) {
        withContext(Dispatchers.IO) {
            location.postValue(locatorDao.getLocation(name))
        }
    }
}