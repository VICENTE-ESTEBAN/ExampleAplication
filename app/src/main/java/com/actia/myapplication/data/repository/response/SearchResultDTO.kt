package com.actia.myapplication.data.repository.response

import com.google.gson.annotations.SerializedName

data class SearchResultDTO (

    @SerializedName("Response")
    val response:Boolean,

    @SerializedName("Search")
    val search: List<ItemDTO>,
)