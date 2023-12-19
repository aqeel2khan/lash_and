package com.cosmetics.lush.ui.homemodule.orderconfirmed

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.cosmetics.lush.R
import com.cosmetics.lush.utils.CART_RESULT_CODE
import kotlinx.android.synthetic.main.fragment_order_confirmed.*


class OrderConfirmedFragment : Fragment(R.layout.fragment_order_confirmed) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This callback will only be called when Fragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    closePage()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        submitBT.setOnClickListener {
            closePage()
        }
        Handler().postDelayed({
            closePage()
        }, 7000)
    }

    private fun closePage() {
        try {
            activity?.setResult(CART_RESULT_CODE)
            activity?.finish()
        } catch (e: Exception) {
        }
    }


}
