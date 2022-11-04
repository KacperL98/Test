package kacper.litwinow.yournewsweek.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kacper.litwinow.yournewsweek.databinding.FragmentDetailsCommentsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsCommentsBinding? = null
    private val binding by lazy { _binding!! }
    private val posts: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.com.text = posts.posts.comments.firstOrNull()?.email
        binding.title.text = posts.posts.post.title
    }
}