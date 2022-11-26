package com.actia.myapplication.data.repository.mappers

import com.actia.myapplication.data.domain.model.DetailItem
import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.data.repository.mappers.helpers.Mapper
import com.actia.myapplication.data.repository.response.ItemDTO

class ItemDTOToDetailMapper: Mapper<ItemDTO, DetailItem> {
    override fun map(input: ItemDTO): DetailItem {
        return DetailItem(
                title = input.title,
                director = input.director,
                poster = input.poster,
                releaseYear = input.year,
                duration = input.runtime,
                description = input.plot,
                score = input.imdbRating,
            )
    }
}