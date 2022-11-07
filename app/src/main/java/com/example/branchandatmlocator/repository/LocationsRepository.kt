package com.example.branchandatmlocator.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.branchandatmlocator.data.LocationsDatabase
import com.example.branchandatmlocator.model.Locations
import com.example.branchandatmlocator.model.RequestBody
import com.example.branchandatmlocator.model.asDatabaseModel
import com.example.branchandatmlocator.model.asDomainModel
import com.example.branchandatmlocator.network.LocatorApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//interface LocationsRepository{
//
//    suspend fun search(keyword: String, type: String):List<Locations>
//
//    suspend fun get(bankID: String): Locations
//}
class LocationsRepository(private val database: LocationsDatabase) {

    val locations: LiveData<List<Locations>> =
        Transformations.map(database.locatorDao.getLocations()) {
            it.asDomainModel()
        }

    suspend fun refreshRepository(keyword: String, type: String) {
        withContext(Dispatchers.IO) {
            val locations = LocatorApi.retrofitService.getLocations(
                RequestBody
                    (
                    Keyword = keyword,
                    Type = type
                )
            )
            database.locatorDao.insertAll(locations.LocationByKeyword.asDatabaseModel())
        }
    }
}