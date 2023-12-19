package com.cosmetics.lush.ui.homemodule.cart

import android.os.Bundle
import android.widget.TextView
import androidx.navigation.findNavController
import com.cosmetics.core.base.BaseActivity
import com.cosmetics.core.utils.setUpBackToolbar
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.homemodule.profile.navigateUpOrFinish
import kotlinx.android.synthetic.main.activity_my_cart.*


class MyCartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)
        setUpBackToolbar(appBar, getString(R.string.my_cart))
        findNavController(R.id.nav_fragment).addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.show()
            when (destination.id) {
                R.id.cartAddressFragment -> {
                    setUpToolbarTittle(getString(R.string.select_address))
                }
                R.id.deliveryMethodFragment -> {
                    setUpToolbarTittle(getString(R.string.delivery_methods))
                }
                R.id.paymentMethodFragment -> {
                    setUpToolbarTittle(getString(R.string.payment_method))
                }
                R.id.checkOutFragment -> {
                    setUpToolbarTittle(getString(R.string.checkout))
                }
                R.id.paymentWebViewFragment -> {
                    supportActionBar?.hide()
                }
                R.id.orderConfirmedFragment -> {
                    supportActionBar?.hide()
                }
                R.id.editAddressFragment -> {
                    setUpToolbarTittle(getString(R.string.delivery_details))
                }
                R.id.guestCreateUserFragment -> {
                    setUpToolbarTittle(getString(R.string.billing_details))
                }
                else -> {
                    setUpToolbarTittle(getString(R.string.my_cart))
                }
            }
        }
    }

    private fun setUpToolbarTittle(title: String = "") {
        val textView = findViewById<TextView>(R.id.titleTV)
        textView.text = title
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_fragment).navigateUpOrFinish(this)

}
