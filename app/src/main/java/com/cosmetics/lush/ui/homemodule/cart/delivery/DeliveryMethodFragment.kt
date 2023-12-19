package com.cosmetics.lush.ui.homemodule.cart.delivery

import android.os.Bundle
import androidx.lifecycle.Observer
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.DeliveryMethodFragmentBinding
import com.cosmetics.lush.utils.navigate
import kotlinx.android.synthetic.main.delivery_method_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeliveryMethodFragment : BaseBindingFragment<DeliveryMethodFragmentBinding>() {
    private val viewModel: DeliveryMethodViewModel by viewModel()
    private var adapter: DeliveryMethodMainAdapter? = null

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getLayoutId(): Int = R.layout.delivery_method_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.deliveryMethodResponse.observe(viewLifecycleOwner, Observer {
            if (adapter == null) {
                adapter = DeliveryMethodMainAdapter(it.shippingMethods, viewModel)
                recyclerViewDeliveryMethod.adapter = adapter
                viewModel.quoteSelected = it.shippingMethods[0].quote[0]
                viewModel.onContinueButtonClick()
            } else {
                adapter?.updateList(it.shippingMethods)
            }
        })
        viewModel.submitDeliveryMethod.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                navigate(
                    DeliveryMethodFragmentDirections.actionDeliveryMethodFragmentToPaymentMethodFragment()
                )
                viewModel.submitDeliveryMethod.value = null
            }
        })
    }

}
