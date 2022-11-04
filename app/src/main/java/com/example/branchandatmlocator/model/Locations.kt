package com.example.branchandatmlocator.model

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.branchandatmlocator.data.LocatorDao
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
//@Entity(tableName = "locations_database")
data class Locations constructor (
    @Json(name = "Name")
    val name: String,

    @Json(name = "Type")
    val type: String,

    @Json(name = "Address")
    val address: String,

    @Json(name = "PhoneNumber")
    //@ColumnInfo(name = "Phone_Number")
    val phoneNumber: String,

    @Json(name = "FaxNumber")
    //@ColumnInfo(name = "Fax_Number")
    val faxNumber: String?,

    @Json(name = "xCoordinate")
    val xCoordinate: String,

    @Json(name = "yCoordinate")
    val yCoordinate: String,

    @Json(name = "BankId")
    //@ColumnInfo(name = "Bank_ID")
    val bankId: String,

    @Json(name = "QRTag")
    //@ColumnInfo(name = "QR_Tag")
    val qrTag: String
        )

@JsonClass(generateAdapter = true)
data class GetLocationByKeyword constructor(
    @Json(name = "LocationByKeyword")
    val LocationByKeyword: List<Locations>
)