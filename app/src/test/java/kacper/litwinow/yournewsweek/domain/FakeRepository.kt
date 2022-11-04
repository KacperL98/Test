package kacper.litwinow.yournewsweek.domain

import kacper.litwinow.yournewsweek.api.data.remote.Comments
import kacper.litwinow.yournewsweek.api.data.remote.Posts
import kacper.litwinow.yournewsweek.domain.repository.Repository

class FakeRepository(
) : Repository {

    override suspend fun getAllPosts(): List<Posts> {
        return listOf()
    }

    override suspend fun getAllComments(): List<Comments> {
        return listOf()
    }
}