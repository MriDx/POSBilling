package com.example.apptemplate.presentation.app.fragment.web_view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.example.apptemplate.data.local.model.web_view.WebViewModel
import com.example.apptemplate.databinding.WebViewFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common_presentation.fragment.base.BaseFragment

@AndroidEntryPoint
class WebViewFragment : BaseFragment<WebViewFragmentBinding>() {


    private lateinit var webViewModel: WebViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = WebViewFragmentBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner { viewLifecycleOwner.lifecycle }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        webViewModel = navArgs<WebViewFragmentArgs>().value.webViewModel

        loadWebPage()


    }

    private fun loadWebPage() {
        binding.webView.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                cacheMode = WebSettings.LOAD_DEFAULT
                //userAgentString = "Mozilla/5.0 (Linux; Android 9.0.0; Google Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Mobile Safari/537.36"
                useWideViewPort = true


            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (isAttachedToWindow)
                        binding.progressbar.isVisible = false
                }
            }
        }.loadUrl(webViewModel.url)

    }


}