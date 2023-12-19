package com.cosmetics.lush.ui.homemodule.cart

import android.os.Bundle
import androidx.lifecycle.Observer
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.model.product.cart.ItemList
import com.cosmetics.domain.model.product.cart.ProductInCart
import com.cosmetics.domain.model.product.cart.UpdateCartRequest
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentMyCartBinding
import com.cosmetics.lush.utils.navigate
import kotlinx.android.synthetic.main.fragment_my_cart.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyCartFragment : BaseBindingFragment<FragmentMyCartBinding>() {
    private var cartAdapter: MyCartAdapter? = null
    private val viewModel: MyCartViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getLayoutId(): Int = R.layout.fragment_my_cart

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        textViewCheckOut.setOnClickListener {
            if (viewModel.totalProductPrice < 10) {
                showSnackBarError(getString(R.string.minimum_purchase_will_be_10_kd_or_more))
                return@setOnClickListener
            }
            if (PreferenceDelegate.customerId.isNullOrEmpty()) {
                guestUserNavigation()
            } else {
                normalUserNavigation()
            }
        }
        viewModel.getProductFromCart().observe(viewLifecycleOwner,
            Observer { initUI(it) })
        viewModel.updateCart.observe(viewLifecycleOwner,
            Observer { cartAdapter?.updateList(it) }
        )
        viewModel.promoCodeMessage.observe(viewLifecycleOwner,
            {
                if (!it.isNullOrBlank()) {
                    showSnackBarError(it)
                    viewModel.promoCodeMessage.value = ""
                }
            }
        )
        /*viewModel.noProductMessage.observe(viewLifecycleOwner,
            Observer {
                viewModel.errorMessage.value=context?.getString(R.string.shopping_cart_empty_message)
            }
        )*/
    }

    private fun guestUserNavigation() {
        navigate(
            MyCartFragmentDirections.actionMyCartFragmentToGuestCreateUserFragment(viewModel.isShippingAddressNeeded)
        )
    }

    private fun normalUserNavigation() {
        navigate(
            MyCartFragmentDirections
                .actionMyCartFragmentToCartAddressFragment(viewModel.isShippingAddressNeeded)
        )
    }

    private fun initUI(it: ItemList) {
        initCartRV(it.products)

    }

    private fun initCartRV(products: MutableList<ProductInCart>) {
        cartAdapter = MyCartAdapter(
            activity,
            products
        ) { quantity, product ->
            viewModel.updateCart(UpdateCartRequest(product.key, quantity))
        }
        cartRV.adapter = cartAdapter
    }
}
