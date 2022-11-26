package com.actia.myapplication.data.domain.model

import com.google.gson.annotations.SerializedName


data class DetailItem (
    val title: String,
    val poster: String,
    val director: String?,
    val releaseYear: String,
    val duration: String?,
    val description: String?,
    val score:String?
)