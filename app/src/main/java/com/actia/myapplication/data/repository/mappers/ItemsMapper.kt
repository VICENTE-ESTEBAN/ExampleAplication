package com.actia.myapplication.data.repository.mappers

import com.actia.myapplication.data.model.Item
import com.actia.myapplication.data.repository.mappers.helpers.Mapper
import com.actia.myapplication.data.repository.response.ItemDTO
import com.actia.myapplication.data.repository.response.SearchResultDTO

class ItemsMapper: Mapper<SearchResultDTO, List<Item>> {
    override fun map(input: SearchResultDTO): List<Item> {
        return if(input.response)
           input.search.map {
               mapSearchResultDTOToItem(it)
           }.sortedBy {
               //ordenar por año
               it.releaseYear
           }
        else
            return emptyList()
    }

    private fun mapSearchResultDTOToItem(itemDTO: ItemDTO):Item{
        return Item(
            poster = itemDTO.poster,
            title = itemDTO.title,
            releaseYear = itemDTO.year,
            imdb = itemDTO.imdbID
        )
    }
}