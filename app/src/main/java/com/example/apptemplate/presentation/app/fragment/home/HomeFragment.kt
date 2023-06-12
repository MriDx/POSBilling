package com.example.apptemplate.presentation.app.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apptemplate.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common_presentation.fragment.base.BaseFragment


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>()  {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = HomeFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }


}