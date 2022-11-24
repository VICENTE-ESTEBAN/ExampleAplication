package com.actia.myapplication.data.repository.mappers.helpers

// Non-nullable to Nullable
interface NullableOutputListMapper<I, O>: Mapper<List<I>, List<O>?>