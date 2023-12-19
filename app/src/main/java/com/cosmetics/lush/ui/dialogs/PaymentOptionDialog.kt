package com.cosmetics.lush.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import android.widget.RadioButton
import com.cosmetics.lush.R
import kotlinx.android.synthetic.main.payment_options_dialog.*

class PaymentOptionDialog(context: Context) : Dialog(context) {

    private val onPaymentSelectedListener by lazy {
        context as? OnPaymentSelectedListener
    }

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.payment_options_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        cancelTV.setOnClickListener { cancel() }
        doneTV.setOnClickListener {
            onPaymentSelectedListener?.onPaymentSelected(
                findViewById<RadioButton>
                    (paymentOptionsRB.checkedRadioButtonId)?.text?.toString()
            )
            dismiss()
        }
        closeIV.setOnClickListener { cancel() }
    }

    interface OnPaymentSelectedListener {

        fun onPaymentSelected(paymentMethod: String?)
    }
}