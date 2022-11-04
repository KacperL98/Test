package kacper.litwinow.yournewsweek.domain.repository

import kacper.litwinow.yournewsweek.api.data.remote.Comments
import kacper.litwinow.yournewsweek.api.data.remote.Posts

interface Repository {
    suspend fun getAllPosts(): List<Posts>
    suspend fun getAllComments(): List<Comments>
}