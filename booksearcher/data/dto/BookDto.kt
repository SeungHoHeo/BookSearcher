package com.hsh.booksearcher.data.dto
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BookDto(
    @SerializedName("documents")
    var documents: List<BookResponse>?
)

@Keep
data class BookResponse(
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("datetime")
    val datetime: String? ,
    @SerializedName("contents")
    val contents: String? ,
    @SerializedName("publisher")
    val publisher: String?
)
