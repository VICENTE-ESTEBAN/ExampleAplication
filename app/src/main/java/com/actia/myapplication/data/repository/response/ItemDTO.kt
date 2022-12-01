package com.actia.myapplication.data.repository.response

import com.google.gson.annotations.SerializedName


data class ItemDTO (
    @SerializedName("Title")
        val title: String,

    @SerializedName("Year")
        val year: String,

    @SerializedName("Rated")
        val rated: String?,

    @SerializedName("Released")
        val released: String?,

    @SerializedName("Runtime")
        val runtime: String?,

    @SerializedName("Genre")
        val genre: String?,

    @SerializedName("Director")
        val director: String?,

    @SerializedName("Writer")
        val writer: String?,

    @SerializedName("Actors")
        val actors: String?,

    @SerializedName("Plot")
        val plot: String?,

    @SerializedName("Language")
        val language: String?,

    @SerializedName("Country")
        val country: String?,

    @SerializedName("Awards")
        val awards: String?,

    @SerializedName("Poster")
        val poster: String,

    @SerializedName("Ratings")
        val ratings: Array<RatingsDTO>?,

    @SerializedName("Metascore")
        val metascore: String?,

    @SerializedName("imdbRating")
        val imdbRating: String?,

    @SerializedName("imdbVotes")
        val imdbVotes: String?,

    @SerializedName("imdbID")
        val imdbID: String,

    @SerializedName("Type")
        val type: String?,

    @SerializedName("DVD")
        val dVD: String?,

    @SerializedName("BoxOffice")
        val boxOffice: String?,

    @SerializedName("Production")
        val production: String?,

    @SerializedName("Website")
        val website: String?,

    @SerializedName("Response")
        val response: String?
)
{

        data class RatingsDTO(
            @SerializedName("Source")
            val source:String?,
            @SerializedName("Value")
            val value:String?
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemDTO

        if (title != other.title) return false
        if (year != other.year) return false
        if (rated != other.rated) return false
        if (released != other.released) return false
        if (runtime != other.runtime) return false
        if (genre != other.genre) return false
        if (director != other.director) return false
        if (writer != other.writer) return false
        if (actors != other.actors) return false
        if (plot != other.plot) return false
        if (language != other.language) return false
        if (country != other.country) return false
        if (awards != other.awards) return false
        if (poster != other.poster) return false
        if (!ratings.contentEquals(other.ratings)) return false
        if (metascore != other.metascore) return false
        if (imdbRating != other.imdbRating) return false
        if (imdbVotes != other.imdbVotes) return false
        if (imdbID != other.imdbID) return false
        if (type != other.type) return false
        if (dVD != other.dVD) return false
        if (boxOffice != other.boxOffice) return false
        if (production != other.production) return false
        if (website != other.website) return false
        if (response != other.response) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + year.hashCode()
        result = 31 * result + (rated?.hashCode() ?: 0)
        result = 31 * result + (released?.hashCode() ?: 0)
        result = 31 * result + (runtime?.hashCode() ?: 0)
        result = 31 * result + (genre?.hashCode() ?: 0)
        result = 31 * result + (director?.hashCode() ?: 0)
        result = 31 * result + (writer?.hashCode() ?: 0)
        result = 31 * result + (actors?.hashCode() ?: 0)
        result = 31 * result + (plot?.hashCode() ?: 0)
        result = 31 * result + (language?.hashCode() ?: 0)
        result = 31 * result + (country?.hashCode() ?: 0)
        result = 31 * result + (awards?.hashCode() ?: 0)
        result = 31 * result + poster.hashCode()
        result = 31 * result + ratings.contentHashCode()
        result = 31 * result + (metascore?.hashCode() ?: 0)
        result = 31 * result + (imdbRating?.hashCode() ?: 0)
        result = 31 * result + (imdbVotes?.hashCode() ?: 0)
        result = 31 * result + imdbID.hashCode()
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (dVD?.hashCode() ?: 0)
        result = 31 * result + (boxOffice?.hashCode() ?: 0)
        result = 31 * result + (production?.hashCode() ?: 0)
        result = 31 * result + (website?.hashCode() ?: 0)
        result = 31 * result + (response?.hashCode() ?: 0)
        return result
    }

}