package kacper.litwinow.yournewsweek.domain

import junit.framework.TestCase.assertTrue
import kacper.litwinow.yournewsweek.api.data.remote.Comments
import kacper.litwinow.yournewsweek.api.data.remote.Posts
import kacper.litwinow.yournewsweek.domain.repository.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FakeRepositoryTest {

    private lateinit var repository: Repository

    @Before
    fun setUp() {
        repository = FakeRepository()
    }

    @Test
    fun `initialization to return an empty posts list`() = runBlocking {
        val postsList = repository.getAllPosts()
        assertTrue(postsList.isEmpty())
    }

    @Test
    fun `Initialization to check if the posts list is not empty`() = runBlocking {
        val posts = buildPosts()
        assertPostsView(posts)
    }

    private fun buildPosts(): Posts {
        return Posts(
            body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
            id = 1,
            title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            userId = 1,
            expandable = false,
            isNumberEven = false

        )
    }

    private fun assertPostsView(posts: Posts) {
        Assert.assertEquals(
            "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
            posts.body
        )
        Assert.assertEquals(1, posts.id)
        Assert.assertEquals(
            "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            posts.title
        )
        Assert.assertEquals(1, posts.userId)
        Assert.assertEquals(false, posts.expandable)
        Assert.assertEquals(false, posts.isNumberEven)

    }

    @Test
    fun `initialization to return an empty comments list`() = runBlocking {
        val commentsList = repository.getAllComments()
        assertTrue(commentsList.isEmpty())
    }

    @Test
    fun `Initialization to check if the comments list is not empty`() = runBlocking {
        val comments = buildComments()
        assertCommentsView(comments)
    }

    private fun buildComments(): Comments {
        return Comments(
            body = "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium",
            id = 1,
            name = "id labore ex et quam laborum",
            postId = 1,
            email = "Eliseo@gardner.biz"
        )
    }

    private fun assertCommentsView(comments: Comments) {
        Assert.assertEquals(
            "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium",
            comments.body
        )
        Assert.assertEquals(1, comments.id)
        Assert.assertEquals(
            "id labore ex et quam laborum",
            comments.name
        )
        Assert.assertEquals(1, comments.postId)
        Assert.assertEquals("Eliseo@gardner.biz", comments.email)
    }

}