package com.portfolio.imagesearchapp.network.model

import java.io.Serializable

data class SearchImageObj(
    var meta: Meta,
    var documents: ArrayList<Document>
) {
    data class Meta(
        var total_count: Int,
        var pageable_count: Int,
        var is_end: Boolean
    )

    data class Document(
        var collection: String,
        var thumbnail_url: String,
        var image_url: String,
        var width: Int,
        var height: Int,
        var display_sitename: String,
        var doc_url: String,
        var datetime: String
    ) : Serializable
}