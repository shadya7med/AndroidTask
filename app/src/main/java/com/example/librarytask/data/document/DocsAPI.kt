package com.example.librarytask.data.document

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class DocumentsSearchResponse(
    @Expose @SerializedName("start") val start: Int? = null,
    @Expose @SerializedName("docs") val docs: List<Docs>? = null,
    @Expose @SerializedName("num_found") val num_found: Long? = null
) : Serializable

@Parcelize
data class Docs(
    @Expose @SerializedName("cover_i") val cover_i: Long? = null,
    @Expose @SerializedName("has_fulltext") val has_fulltext: Boolean? = null,
    @Expose @SerializedName("key") val key: String? = null,
    @Expose @SerializedName("title") val title: String? = null,
    @Expose @SerializedName("edition_count") val edition_count: Long? = null,
    @Expose @SerializedName("first_publish_year") val first_publish_year: Long? = null,
    @Expose @SerializedName("author_key") val author_author_key: List<String>? = null,
    @Expose @SerializedName("author_name") val author_name: List<String>? = null,
    @Expose @SerializedName("isbn") val isbn: List<String>? = null
) : Parcelable