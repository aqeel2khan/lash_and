package com.cosmetics.lush.ui.homemodule.cart.address

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.model.home.profile.DELIVERY_ADDRESS
import com.cosmetics.domain.model.home.profile.NORMAL_ADDRESS
import com.cosmetics.domain.model.home.profile.PAYMENT_ADDRESS
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.CartAdressFragmentBinding
import com.cosmetics.lush.utils.navigate
import kotlinx.android.synthetic.main.cart_adress_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class CartAddressFragment : BaseBindingFragment<CartAdressFragmentBinding>() {
    private var adapter: AddressSelectionAdapter? = null
    private var paymentAdapter: AddressSelectionAdapter? = null
    private val cartAddressFragmentArgs: CartAddressFragmentArgs by navArgs()
    private val viewModel: CartAddressViewModel by viewModel {
        parametersOf(
            cartAddressFragmentArgs.isShippingAddressNeeded
        )
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getLayoutId(): Int = R.layout.cart_adress_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.callAPI()
        initClickListener()
        viewModel.deliveryAddressResponse.observe(viewLifecycleOwner, Observer {
            if (adapter == null) {
                adapter = AddressSelectionAdapter(it, viewModel)
                recyclerViewDeliveryAddress.adapter = adapter
            } else {
                adapter?.updateList(it)
            }
        })

        viewModel.isDeliveryAddressRegistered.observe(viewLifecycleOwner, Observer {
            if (it) {
                navigate(
                    CartAddressFragmentDirections.actionCartAddressFragmentToDeliveryMethodFragment()
                )
                viewModel.isDeliveryAddressRegistered.value = false/*
                viewModel.isPaymentAddressRegistered.value = false
                viewModel.isPaymentAddressSelectionActive.value = true*/
            }
        })

        viewModel.navigateToPaymentSelection.observe(viewLifecycleOwner, Observer {
            if (it) {
                navigate(
                    CartAddressFragmentDirections.actionCartAddressFragmentToPaymentMethodFragment()
                )
                viewModel.navigateToPaymentSelection.value = false
                viewModel.isPaymentAddressRegistered.value = false
                viewModel.isPaymentAddressSelectionActive.value = true/*
                viewModel.isPaymentAddressSelectionActive.value = true*/
            }
        })
/*

        viewModel.deliveryAddressResponse.observe(viewLifecycleOwner, Observer {
            if (adapter == null) {
                adapter = AddressSelectionAdapter(it, viewModel)
                recyclerView_PaymentAddress.adapter = adapter
            } else {
                adapter?.updateList(it)
            }
        })
*/

        viewModel.paymentAddressResponse.observe(viewLifecycleOwner, Observer {
            if (paymentAdapter == null) {
                paymentAdapter = AddressSelectionAdapter(it, viewModel)
                recyclerView_PaymentAddress.adapter = paymentAdapter
            } else {
                paymentAdapter?.updateList(it)
            }
        })
    }

    private fun initClickListener() {
        textViewAddNewAddress.setOnClickListener {
            findNavController().navigate(
                CartAddressFragmentDirections.actionCartAddressFragmentToEditAddressFragment(
                    null,
                    NORMAL_ADDRESS
                )
            )
        }
        textViewAdd_PaymentAddress.setOnClickListener {
            findNavController().navigate(
                CartAddressFragmentDirections.actionCartAddressFragmentToEditAddressFragment(
                    null,
                    PAYMENT_ADDRESS
                )
            )
        }
        textViewAddDeliveryAddress.setOnClickListener {
            findNavController().navigate(
                CartAddressFragmentDirections.actionCartAddressFragmentToEditAddressFragment(
                    null,
                    DELIVERY_ADDRESS
                )
            )
        }
    }
}
