package com.cosmetics.lush.ui.homemodule.checkout

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.cart.Product
import com.cosmetics.domain.model.product.cart.Total
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentCheckOutBinding
import com.cosmetics.lush.ui.dialogs.PaymentOptionDialog
import com.cosmetics.lush.ui.homemodule.cart.payment.PaymentWebViewViewModel
import com.cosmetics.lush.utils.navigate
import kotlinx.android.synthetic.main.fragment_check_out.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckOutFragment : BaseBindingFragment<FragmentCheckOutBinding>(),
    PaymentOptionDialog.OnPaymentSelectedListener {
    private val cod = "cod"
    private val paymentViewModel: PaymentWebViewViewModel by sharedViewModel()

    private val viewModel: CheckOutViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.fragment_check_out

    override fun getViewModel(): BaseViewModel = viewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.paymentMethodResponse.observe(viewLifecycleOwner, Observer {
            initCheckOutRV(it.products)
            initPriceSplit(it.totals)
        })

        paymentViewModel.onlinePaymentStatus.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Results.Success -> {
                    viewModel.confirmOrder()
                }
                is Results.Error -> {
                    showSnackBarError(it.message)
                }
            }
        })
        paymentOptionTV.setOnClickListener {
            try {
                findNavController().popBackStack()
            } catch (e: Exception) {
            }
        }
        proceedTV.setOnClickListener {
            try {
                if (viewModel.paymentMethodResponse.value?.paymentCode == cod ||
                    paymentViewModel.onlinePaymentStatus.value is Results.Success
                ) {
                    viewModel.confirmOrder()
                } else {
                    navigateToOnlinePaymentPage(viewModel.paymentMethodResponse.value?.payment)
                }
            } catch (e: Exception) {
            }
        }

        viewModel.confirmOrderResponse.observe(viewLifecycleOwner, Observer {
            navigateToOrderSuccess()
        })
    }

    private fun navigateToOnlinePaymentPage(url: String?) {
        url?.let {
            navigate(
                CheckOutFragmentDirections
                    .actionCheckOutFragmentToPaymentWebViewFragment(it)
            )
        }
    }

    private fun navigateToOrderSuccess() {
        paymentViewModel.onlinePaymentStatus.value = null
        navigate(
            CheckOutFragmentDirections
                .actionCheckOutFragmentToOrderConfirmedFragment()
        )
    }

    private fun initPriceSplit(total: List<Total>) {
        recyclerViewPriceSplitUp.adapter = CheckoutPriceSplitUpAdapter(total)
    }

    override fun onPaymentSelected(paymentMethod: String?) {
        paymentOptionTV.text = paymentMethod
    }

    private fun initCheckOutRV(products: List<Product>) {
        checkOutRV.adapter = CheckOutAdapter(products)
    }

}
