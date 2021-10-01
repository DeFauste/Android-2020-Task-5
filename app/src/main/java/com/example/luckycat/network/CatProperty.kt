package com.example.luckycat.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatProperty(
    @Json(name = "id") val id: String?,
    @Json(name = "url") val imgSrcUrl: String?,
    @Json(name = "width") val width: String?,
    @Json(name = "height") val height: String?
)