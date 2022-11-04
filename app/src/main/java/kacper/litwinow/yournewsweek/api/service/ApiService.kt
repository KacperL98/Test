package kacper.litwinow.yournewsweek.api.service

import kacper.litwinow.yournewsweek.api.data.remote.Comments
import kacper.litwinow.yournewsweek.api.data.remote.Posts
import retrofit2.http.GET

interface ApiService {

    @GET("/posts")
    suspend fun getAllPosts(): List<Posts>

    @GET("/comments")
    suspend fun getAllComments(): List<Comments>
}
