package com.example.branchandatmlocator.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "branch_atm_info_database")
data class Locations (
    @ColumnInfo(name = "Name")
    val name: String,

    @ColumnInfo(name = "Type")
    val type: String,

    @ColumnInfo(name = "Address")
    val address: String,

    @ColumnInfo(name = "PhoneNumber")
    val phoneNumber: String,

    @ColumnInfo(name = "FaxNumber")
    val faxNumber: String,

    @ColumnInfo(name = "xCoordinate")
    val xCoordinate: String,

    @ColumnInfo(name = "yCoordinate")
    val yCoordinate: String,

    @PrimaryKey
    @ColumnInfo(name = "BankId")
    val bankId: String,

    @ColumnInfo(name = "QRTag")
    val qrTag: String
        )