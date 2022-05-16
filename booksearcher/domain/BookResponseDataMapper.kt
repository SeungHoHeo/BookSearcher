package com.hsh.booksearcher.domain

import com.hsh.booksearcher.R
import com.hsh.booksearcher.data.dto.BookResponse
import com.hsh.booksearcher.domain.model.BookData

fun BookResponse.toRepoInfo() = BookData(
    thumbnail = thumbnail,
    title = title,
    price = price +"원",
    datetime = datetime?.split("T")?.get(0) ?: "",
    contents = contents ?: "",
    publisher= publisher?: "",
    like     = R.mipmap.icon_empty_like
)

