package com.example.branchandatmlocator.data

import androidx.room.Dao
import androidx.room.Query
import com.example.branchandatmlocator.model.Locations
import kotlinx.coroutines.flow.Flow

@Dao
interface LocatorDao {

    @Query("SELECT * from branch_atm_info_database WHERE Name = :name")
    fun getLocations(name: String): Flow<List<Locations>>

    @Query("SELECT * from branch_atm_info_database WHERE BankId = :bankID")
    fun getLocation(bankID: String): Flow<Locations>
}