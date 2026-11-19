package com.example.proyectolibrevictor

import com.google.gson.annotations.SerializedName

data class JuegoOferta(
    @SerializedName("title") val title: String,
    @SerializedName("salePrice") val salePrice: String,
    @SerializedName("normalPrice") val normalPrice: String,
    @SerializedName("metacriticScore") val metacriticScore: String?,
    @SerializedName("thumb") val thumb: String?
)