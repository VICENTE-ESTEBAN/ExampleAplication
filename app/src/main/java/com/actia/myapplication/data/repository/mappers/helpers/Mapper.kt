package com.actia.myapplication.data.repository.mappers.helpers

interface Mapper<I, O> {
    fun map(input: I): O
}