package com.actia.myapplication.data.domain.model

import android.os.Parcel
import android.os.Parcelable


data class Item(
    val poster: String?,
    val title: String?,
    val releaseYear: String?,
    val imdb: String?

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(poster)
        parcel.writeString(title)
        parcel.writeString(releaseYear)
        parcel.writeString(imdb)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}


