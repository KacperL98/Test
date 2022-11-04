package kacper.litwinow.yournewsweek.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import kacper.litwinow.yournewsweek.api.data.remote.PostWithComments
import kacper.litwinow.yournewsweek.databinding.ItemPostsBinding

class PostsViewHolder(private val itemPostsBinding: ItemPostsBinding) :
    RecyclerView.ViewHolder(itemPostsBinding.root) {

    fun bindPosts(posts: PostWithComments) {
        with(itemPostsBinding) {
            expandTextView.text = posts.post.body
        }
    }
}