package kacper.litwinow.yournewsweek.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kacper.litwinow.yournewsweek.api.data.remote.PostWithComments
import kacper.litwinow.yournewsweek.api.data.remote.Posts
import kacper.litwinow.yournewsweek.common.NetworkError
import kacper.litwinow.yournewsweek.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(emptyList<PostWithComments>())
    val state = _state.asStateFlow()

    private val _networkError = MutableStateFlow<NetworkError?>(null)
    val networkError = _networkError.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun postAndComments() {
        viewModelScope.launch(Dispatchers.IO) {
            val getPosts = async { repository.getAllPosts() }
            val getComments = async { repository.getAllComments() }
            try {
                val posts = getPosts.await()
                val comments = getComments.await()

                val items = posts.map { post ->
                    PostWithComments(
                        post, comments.filter { post.id == it.postId }
                    )
                }
                _state.value = items
            } catch (e: Exception) {
                _networkError.value = when {
                    else -> NetworkError.UnKnown
                }
            }
            _loading.value = false
        }
    }

    fun resetError() = viewModelScope.launch {
        _networkError.value = null
    }
}