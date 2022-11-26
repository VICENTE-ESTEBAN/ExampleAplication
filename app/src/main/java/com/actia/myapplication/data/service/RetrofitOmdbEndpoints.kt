package com.actia.myapplication.data.service

import com.actia.myapplication.data.repository.response.ItemDTO
import com.actia.myapplication.data.repository.response.SearchResultDTO
import retrofit2.Call
import retrofit2.http.*


interface RetrofitOmdbEndpoints {

    @Headers(
        "Content-Type:application/json",
        "Accept:application/json",
        "Content-Type: application/json",
        "Cache-Control: no-cache",
        "Content-Language: en-US"
    )
    @GET("/?r=json")
    fun getItemsByTitle(

        @Query("apikey") apikey:String,
        @Query("s") title: String
    ): Call<SearchResultDTO>

    @Headers(
        "Content-Type:application/json",
        "Accept:application/json",
        "Content-Type: application/json",
        "Cache-Control: no-cache",
        "Content-Language: en-US"
    )
    @GET("/?r=json")
    fun getItemByImdb(
        @Query("apikey") apikey:String,
        @Query("i") imdb: String
    ): Call<ItemDTO>


    @Headers(
        "Content-Type:application/json",
        "Accept:application/json",
        "Content-Type: application/json",
        "Cache-Control: no-cache",
        "Content-Language: en-US"
    )
    @GET("/?r=json")
    fun getItemByTitle(

        @Query("apikey") apikey:String,
        @Query("t") title: String
    ): Call<ItemDTO>
}
