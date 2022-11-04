package kacper.litwinow.yournewsweek.ui.viewmodel

import kacper.litwinow.yournewsweek.domain.FakeRepository
import kacper.litwinow.yournewsweek.domain.repository.Repository
import kacper.litwinow.yournewsweek.helpers.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostViewModelTest {
    lateinit var postViewModel: PostViewModel
    lateinit var repository: Repository

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setUp() {
        repository = FakeRepository()

        postViewModel = PostViewModel(repository)
    }

    @Test
    fun `loading is initialization to false`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        val loading = postViewModel.loading.value
        Assert.assertFalse(loading)
    }

    @Test
    fun `checking that the fetched list of posts from network is not empty`() {
        postViewModel.postAndComments()
        val posts = postViewModel.state.value
        Assert.assertFalse(posts.isNotEmpty())
    }

    @Test
    fun `set error to null after reset`() {
        postViewModel.resetError()
        val error = postViewModel.networkError.value
        Assert.assertNull(error)
    }
}