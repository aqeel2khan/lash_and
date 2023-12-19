package com.cosmetics.lush.ui.homemodule.cart.paymentmethod

import android.os.Bundle
import androidx.lifecycle.Observer
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.PaymentMethodFragmentBinding
import com.cosmetics.lush.utils.navigate
import kotlinx.android.synthetic.main.payment_method_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentMethodFragment : BaseBindingFragment<PaymentMethodFragmentBinding>() {
    private val viewModel: PaymentMethodViewModel by viewModel()
    private var adapter: PaymentMethodAdapter? = null

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getLayoutId(): Int = R.layout.payment_method_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.paymentMethodResponse.observe(viewLifecycleOwner, Observer {
            if (adapter == null) {
                adapter = PaymentMethodAdapter(it, viewModel)
                recyclerViewPaymentMethod.adapter = adapter
            } else {
                adapter?.updateList(it)
            }
        })
        viewModel.submitPaymentMethod.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                navigate(
                    PaymentMethodFragmentDirections.actionPaymentMethodFragmentToCheckOutFragment(
                        it
                    )
                )
                viewModel.submitPaymentMethod.value = null
            }
        })
    }

}
