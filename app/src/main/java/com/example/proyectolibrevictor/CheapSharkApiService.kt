package com.example.proyectolibrevictor

import retrofit2.http.GET
import retrofit2.http.Query

interface CheapSharkApiService {
    @GET("api/1.0/deals")
    suspend fun obtenerOfertas(
        @Query("storeID") storeID: String = "1"
    ): List<JuegoOferta>
}