package com.project.response

import com.google.gson.annotations.SerializedName

data class CitiesDto(
    @SerializedName("LocalizedName")
    val localizedName: String? = null,
    @SerializedName("EnglishName")
    val englishName: String? = null,
    @SerializedName("Key")
    val key: String = "",
    @SerializedName("Region")
    val region: Region? = null,
    @SerializedName("Country")
    val country: Country? = null,
    @SerializedName("GeoPosition")
    val geoPosition: GeoPosition? = null
)


data class Region(
    @SerializedName("EnglishName")
    val englishName: String? = null
)

data class Country(
    @SerializedName("EnglishName")
    val englishName: String? = null
)

data class GeoPosition(
    @SerializedName("Latitude")
    val latitude: String? = null,
    @SerializedName("Longitude")
    val longitude: String? = null
)