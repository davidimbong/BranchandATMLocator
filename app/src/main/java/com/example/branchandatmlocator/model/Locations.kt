package com.example.branchandatmlocator.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Locations constructor (
    @Json(name = "Name")
    val name: String,

    @Json(name = "Type")
    val type: String,

    @Json(name = "Address")
    val address: String,

    @Json(name = "PhoneNumber")
    val phoneNumber: String,

    @Json(name = "FaxNumber")
    val faxNumber: String?,

    @Json(name = "xCoordinate")
    val xCoordinate: String,

    @Json(name = "yCoordinate")
    val yCoordinate: String,

    @Json(name = "BankId")
    val bankId: String,

    @Json(name = "QRTag")
    val qrTag: String
        )

@JsonClass(generateAdapter = true)
data class GetLocationByKeyword constructor(
    @Json(name = "LocationByKeyword")
    val LocationByKeyword: List<Locations>
)