package com.cosmetics.lush.ui.homemodule.cart.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cosmetics.core.base.BaseFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.Loading
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.lush.R
import com.cosmetics.lush.utils.navigate
import kotlinx.android.synthetic.main.fragment_payment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PaymentWebViewFragment : BaseFragment() {
    private val viewModel: PaymentWebViewViewModel by sharedViewModel()
    private val navArgs: PaymentWebViewFragmentArgs by navArgs<PaymentWebViewFragmentArgs>()
    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = PaymentWebViewClient(buttonPaymentDone)
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        viewModel.viewState.value = Loading
        viewModel.confirmOrderResponse.observe(viewLifecycleOwner, Observer {
            navigateToOrderSuccess()
        })
        webView.loadUrl(navArgs.paymentUrl)
        initListener()
    }

    private fun navigateToOrderSuccess() {
        viewModel.onlinePaymentStatus.value = null
        navigate(
            PaymentWebViewFragmentDirections
                .actionPaymentWebViewFragmentToOrderConfirmedFragment()
        )
    }

    private fun initListener() {
        buttonPaymentDone.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    inner class PaymentWebViewClient(val btnView: View) : WebViewClient() {

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            viewModel.viewState.value = Completed
            Log.e("PageFinish Url", url)
            url.let {
                if (it.contains("/success")) {
                    viewModel.onlinePaymentStatus.value = Results.Success(BaseResponse())
                    viewModel.confirmOrder()
                    //btnView.visibility = View.VISIBLE
                } else if (it.contains("/cart") || it.contains("/paymentcancel")) {
                    viewModel.onlinePaymentStatus.value =
                        Results.Error(getString(R.string.unable_to_process_your_order))
                    findNavController().popBackStack()
                }
            }
        }
    }
}