package com.example.project

import com.example.project.Model22.CityDetailsResponse
import com.example.project.Models.CityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {
 @GET("fayazara/Indian-Cities-API/master/db.json")
 suspend fun getMarketData() : Response<CityResponse>


 @GET
 suspend fun getCityData(@Url url : String) : Response<CityDetailsResponse>
}