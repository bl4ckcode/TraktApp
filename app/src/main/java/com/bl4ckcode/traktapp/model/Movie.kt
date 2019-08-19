package com.bl4ckcode.traktapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("title") val title: String,
    @SerializedName("year") val year: Int,
    @SerializedName("ids") var ids: Map<String, String>,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("released") val released: String?
) : Parcelable