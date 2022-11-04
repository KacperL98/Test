package kacper.litwinow.yournewsweek.api.data.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostWithComments(
    val post: Posts,
    val comments: List<Comments>
) : Parcelable
