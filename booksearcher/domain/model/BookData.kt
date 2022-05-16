package com.hsh.booksearcher.domain.model

data class BookData(
    val thumbnail           : String,
    val title               : String,
    val price               : String,
    val datetime            : String,
    val contents            : String,
    val publisher           : String,
    var like                : Int,
)