package kacper.litwinow.yournewsweek.api.data.remote


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comments(
    val id: Int,
    @Json(name = "body")
    val body: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "postId")
    val postId: Int
) : Parcelable