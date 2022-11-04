package kacper.litwinow.yournewsweek.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kacper.litwinow.yournewsweek.R
import kacper.litwinow.yournewsweek.api.data.remote.PostWithComments
import kacper.litwinow.yournewsweek.databinding.ItemPostsBinding
import kacper.litwinow.yournewsweek.ui.viewholder.PostsViewHolder

class PostCommentAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<PostWithComments, PostsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = ItemPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {

        val context = holder.itemView.context
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
        }
        val posts = getItem(position)
        holder.bindPosts(posts)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(posts)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PostWithComments>() {

        override fun areItemsTheSame(
            oldItem: PostWithComments,
            newItem: PostWithComments
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: PostWithComments,
            newItem: PostWithComments
        ): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (posts: PostWithComments) -> Unit) {
        fun onClick(posts: PostWithComments) = clickListener(posts)
    }
}