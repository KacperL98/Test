package kacper.litwinow.yournewsweek.api.data.remote


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Posts(
    @Json(name = "body")
    val body: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "userId")
    val userId: Int,
    var expandable: Boolean = false,
    var isNumberEven: Boolean,
) : Parcelable