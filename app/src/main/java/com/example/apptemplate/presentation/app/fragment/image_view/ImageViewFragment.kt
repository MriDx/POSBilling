package com.example.apptemplate.presentation.app.fragment.image_view

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.apptemplate.data.local.model.media.ImageViewModel
import com.igreenwood.loupe.Loupe
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_presentation.fragment.base.BaseFragment
import dev.mridx.common.common_presentation.fragment.builder.FragmentBuilder
import dev.mridx.dynamic_form.databinding.ImageViewFragmentBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageViewFragment : BaseFragment<ImageViewFragmentBinding>() {


    class Builder : FragmentBuilder<ImageViewFragment>() {

        private val args = Bundle()

        fun setImageViewModel(imageViewModel: ImageViewModel): Builder {
            args.putParcelable("data", imageViewModel)
            return this
        }

        override fun build(): ImageViewFragment {
            val fragment = ImageViewFragment()
            fragment.arguments = args
            return fragment
        }

    }



    private lateinit var imageViewModel: ImageViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = ImageViewFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageViewModel = if (arguments?.containsKey("data") == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable("data", ImageViewModel::class.java) ?: throw Exception("")
            } else {
                arguments?.getParcelable<ImageViewModel>("data") ?: throw Exception("")
            }
        } else {
            navArgs<ImageViewFragmentArgs>().value.imageViewModel
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    loadImage()
                }
            }
        }


    }


    private fun loadImage() {
        Glide.with(requireContext())
            .asBitmap()
            .load(imageViewModel.imageModel?.img_meta?.img_large ?: imageViewModel.imageModel?.image)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (!isAdded) return false
                    Loupe.create(
                        binding.imageView,
                        binding.container
                    ) { // imageView is your ImageView
                        onViewTranslateListener = object : Loupe.OnViewTranslateListener {

                            override fun onStart(view: ImageView) {
                                // called when the view starts moving
                            }

                            override fun onViewTranslate(view: ImageView, amount: Float) {
                                // called whenever the view position changed
                            }

                            override fun onRestore(view: ImageView) {
                                // called when the view drag gesture ended
                            }

                            override fun onDismiss(view: ImageView) {
                                // called when the view drag gesture ended
                                findNavController().popBackStack()
                            }
                        }
                    }
                    return false
                }

            })
            .into(binding.imageView)
    }



}