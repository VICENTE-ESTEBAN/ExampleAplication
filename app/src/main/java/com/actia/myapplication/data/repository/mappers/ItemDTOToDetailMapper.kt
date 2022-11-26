package com.actia.myapplication.data.repository.mappers

import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.data.repository.mappers.helpers.Mapper
import com.actia.myapplication.data.repository.response.ItemDTO
import com.actia.myapplication.data.repository.response.SearchResultDTO
import com.actia.myapplication.util.Constants

class ItemDTOToItemMapper: Mapper<ItemDTO, Item?> {
    override fun map(input: ItemDTO): Item? {
        return if(!input.response.isNullOrEmpty())
            return Item(
                poster = input.poster,
                title = input.title,
                releaseYear = input.year,
                imdb = input.imdbID
            )
        else
            return null
    }
}