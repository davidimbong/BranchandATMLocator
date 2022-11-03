package com.example.branchandatmlocator.network

import com.example.branchandatmlocator.model.GetLocationByKeyword
import com.example.branchandatmlocator.model.RequestBody
import com.google.gson.JsonObject
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


var okHttpClient: OkHttpClient = UnsafeOkHttpClient().getUnsafeOkHttpClient()

private const val BASE_URL =
    "https://103.53.154.82/MobileAppV7SIT/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface LocatorApiService {
    @POST("BranchAndAtmLocator/GetLocationByKeyword")
    suspend fun getLocations(@Body requestBody: RequestBody): GetLocationByKeyword

    @POST("BranchAndAtmLocator/GetLocationByKeyword")
    suspend fun getLocations1(@Body requestBody: RequestBody): JsonObject
}

object LocatorApi {
    val retrofitService: LocatorApiService by lazy { retrofit.create(LocatorApiService::class.java) }

}