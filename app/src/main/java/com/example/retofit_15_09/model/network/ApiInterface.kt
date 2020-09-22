package com.example.retofit_15_09.model.network

import com.example.retofit_15_09.model.Terrain
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("realestate")
    fun getDataFromApi() : Call<List<Terrain>>


    @GET("realestate")
    suspend fun getDataFromApiCorutines() : Response<List<Terrain>>

}