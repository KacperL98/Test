package kacper.litwinow.yournewsweek.ui.fragments

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kacper.litwinow.yournewsweek.R
import kacper.litwinow.yournewsweek.adapter.PostCommentAdapter
import kacper.litwinow.yournewsweek.databinding.FragmentPostsBinding
import kacper.litwinow.yournewsweek.ui.viewmodel.PostViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding by lazy { _binding!! }

    private val adapter by lazy {
        PostCommentAdapter(PostCommentAdapter.OnClickListener {
            this.findNavController().navigate(
                ListFragmentDirections.actionPostsFragmentToDetailsCommentsFragment(it)
            )
        })
    }
    private val viewModel: PostViewModel by viewModels()
    private var animatedHide = false
    private var animatedShow = false
    private val findLastVisibleItemPositionValue = 16

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        customArrow()
        scrollListener()
        animationArrow()
        binding.productsRv.adapter = adapter

    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    viewModel.postAndComments()
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun customArrow() {
        binding.scrollToTopArrow.borderColor = Color.WHITE
        binding.scrollToTopArrow.circleBackgroundColor =
            ContextCompat.getColor(
                requireContext(),
                R.color.black
            )
        binding.scrollToTopArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
    }

    private fun scrollListener() {

        val linearLayoutManager = LinearLayoutManager(binding.productsRv.context)
        binding.productsRv.layoutManager = linearLayoutManager
        binding.productsRv.setHasFixedSize(true)

        val whenVisibleMargin = convertDpToPixel(15f, binding.productsRv.context)
        val whenHideMargin = convertDpToPixel(-85f, binding.productsRv.context)
        binding.productsRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (linearLayoutManager.findLastVisibleItemPosition() >= findLastVisibleItemPositionValue) {
                    if (!animatedShow) {
                        binding.scrollToTopArrow.visibility = View.VISIBLE
                        val params =
                            binding.scrollToTopArrow.layoutParams as RelativeLayout.LayoutParams
                        val animator =
                            ValueAnimator.ofInt(params.rightMargin, whenVisibleMargin.toInt())
                        animator.addUpdateListener { valueAnimator ->
                            params.rightMargin = valueAnimator.animatedValue as Int
                            binding.scrollToTopArrow.requestLayout()
                        }
                        animator.duration = 800
                        animator.start()
                        animatedShow = true
                        animatedHide = false
                    }
                } else {
                    if (!animatedHide) {
                        binding.scrollToTopArrow.visibility = View.VISIBLE
                        val params =
                            binding.scrollToTopArrow.layoutParams as RelativeLayout.LayoutParams
                        val animator =
                            ValueAnimator.ofInt(params.rightMargin, whenHideMargin.toInt())
                        animator.addUpdateListener { valueAnimator ->
                            params.rightMargin = valueAnimator.animatedValue as Int
                            binding.scrollToTopArrow.requestLayout()
                        }
                        animator.duration = 800
                        animator.start()
                        animatedHide = true
                        animatedShow = false
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun animationArrow() {
        binding.scrollToTopArrow.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(
                requireContext(), R.anim.rotate
            )
            binding.productsRv.post {
                binding.productsRv.smoothScrollToPosition(0)
                binding.scrollToTopArrow.startAnimation(anim)
            }
        }
    }

    private fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
