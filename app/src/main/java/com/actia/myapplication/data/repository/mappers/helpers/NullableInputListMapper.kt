package com.actia.myapplication.data.repository.mappers.helpers

// Nullable to Non-nullable
interface NullableInputListMapper<I, O>: Mapper<List<I>?, List<O>>