package com.actia.myapplication.data.repository.mappers

import com.actia.myapplication.data.domain.model.DetailItem
import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.data.repository.mappers.helpers.Mapper
import com.actia.myapplication.data.repository.response.ItemDTO
import com.actia.myapplication.util.Constants

class ItemDTOToDetailMapper: Mapper<ItemDTO, DetailItem> {
    override fun map(input: ItemDTO): DetailItem {
        return DetailItem(
                title = input.title,
                director = input.director,
                poster = parseURL(input.poster),
                releaseYear = input.year,
                duration = input.runtime,
                description = input.plot,
                score = input.imdbRating,
            )
    }

    private fun parseURL(value:String?): String {
        return if (value.isNullOrEmpty() || value == Constants.EMPTY_FIELD)
            ""
        else
            value
    }
}