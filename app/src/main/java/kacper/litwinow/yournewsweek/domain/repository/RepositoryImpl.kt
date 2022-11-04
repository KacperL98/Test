package kacper.litwinow.yournewsweek.domain.repository

import kacper.litwinow.yournewsweek.api.data.remote.Comments
import kacper.litwinow.yournewsweek.api.data.remote.Posts
import kacper.litwinow.yournewsweek.api.service.ApiService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : Repository {

    override suspend fun getAllPosts(): List<Posts> {
        return apiService.getAllPosts()
    }

    override suspend fun getAllComments(): List<Comments> {
        return apiService.getAllComments()
    }
}