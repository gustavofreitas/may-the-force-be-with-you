package br.com.example.data.remote.model

import com.google.gson.annotations.SerializedName

data class PeoplePayload(
    @SerializedName("name") val name: String,
    @SerializedName("height") val height: String,
    @SerializedName("mass") val mass: String,
    @SerializedName("hair_color") val hairColor: String,
    @SerializedName("skin_color") val skinColor: String,
    @SerializedName("eye_color") val eyeColor: String,
    @SerializedName("birth_year") val birthYear: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("homeworld") val homeworld: String,
    @SerializedName("species") val species: Array<String>,
    @SerializedName("starships") val starships: Array<String>,
    @SerializedName("vehicles") val vehicles: Array<String>,
    @SerializedName("created") val created: String,
    @SerializedName("edited") val edited: String,
    @SerializedName("url") val url: String

)