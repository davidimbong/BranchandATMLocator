package com.example.branchandatmlocator.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.branchandatmlocator.model.Locations
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocatorDtoContainer(val locations: List<LocatorDto>)

/**
 * Videos represent a devbyte that can be played.
 */
@JsonClass(generateAdapter = true)
data class LocatorDto(
    @Json(name = "Name")
    val name: String,

    @Json(name = "Type")
    val type: String,

    @Json(name = "Address")
    val address: String,

    @Json(name = "PhoneNumber")
    @ColumnInfo(name = "Phone_Number")
    val phoneNumber: String,

    @Json(name = "FaxNumber")
    @ColumnInfo(name = "Fax_Number")
    val faxNumber: String?,

    @Json(name = "xCoordinate")
    val xCoordinate: String,

    @Json(name = "yCoordinate")
    val yCoordinate: String,

    @PrimaryKey
    @Json(name = "BankId")
    @ColumnInfo(name = "Bank_ID")
    val bankId: String,

    @Json(name = "QRTag")
    @ColumnInfo(name = "QR_Tag")
    val qrTag: String)


fun LocatorDtoContainer.asDomainModel(): List<Locations> {
    return locations.map {
        Locations(
            name = it.name,
            type = it.type,
            address = it.address,
            phoneNumber = it.phoneNumber,
            faxNumber = it.faxNumber,
            xCoordinate = it.xCoordinate,
            yCoordinate = it.yCoordinate,
            bankId = it.bankId,
            qrTag = it.qrTag
        )
    }
}

fun LocatorDtoContainer.asDatabaseModel(): List<Locations> {
    return locations.map {
        Locations(
            name = it.name,
            type = it.type,
            address = it.address,
            phoneNumber = it.phoneNumber,
            faxNumber = it.faxNumber,
            xCoordinate = it.xCoordinate,
            yCoordinate = it.yCoordinate,
            bankId = it.bankId,
            qrTag = it.qrTag
        )
    }
}
