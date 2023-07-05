package dev.mridx.common.common_presentation.fragment.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.mridx.common.common_data.data.local.model.ui_error.UIErrorModel
import dev.mridx.common.common_presentation.databinding.ListingFragmentBinding
import dev.mridx.common.common_presentation.fragment.base.BaseFragment
import dev.mridx.common.common_presentation.vm.listing.ListingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class ListingFragment : BaseFragment<ListingFragmentBinding>() {


    val getParentView get() = binding.root

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = ListingFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ListingFragment.getViewModel()
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.itemHolder.apply {
            adapter = this@ListingFragment.getAdapter()
            layoutManager = this@ListingFragment.getLayoutManager()
        }

        binding.swipeRefreshLayout.setOnRefreshListener { onRefreshing() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    getViewModel<ListingViewModel<Any, Any>>()?.errorState?.collectLatest {
                        handleErrorState(it)
                    }
                }
            }
        }


    }

    private fun handleErrorState(error: UIErrorModel) {
        binding.itemHolder.isVisible = !error.showError
        binding.errorView.root.isVisible = error.showError
        binding.errorView.textView.text = error.errorMessage
    }


    abstract fun <T> getViewModel(): T?

    abstract fun <T> getAdapter(): T?

    open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(requireContext())
    }

    abstract fun onRefreshing()

    open fun setRefreshing(isRefreshing: Boolean = false) {
        binding.swipeRefreshLayout.isRefreshing = isRefreshing
    }


}