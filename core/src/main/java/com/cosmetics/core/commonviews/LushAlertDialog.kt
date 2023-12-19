package com.cosmetics.core.commonviews

import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.cosmetics.core.R

class LushAlertDialog(
    context: Context, title: String = "", message: String?,
    positiveButtonText: String = context.getString(R.string.ok),
    negativeButtonText: String = "",
    positiveButtonClickListener: DialogInterface.OnClickListener =
        DialogInterface.OnClickListener { dialogInterface, _ -> dialogInterface.cancel() },
    negativeButtonClickListener: DialogInterface.OnClickListener =
        DialogInterface.OnClickListener { dialogInterface, _ -> dialogInterface.cancel() }
) : LifecycleObserver {
    private val dialog by lazy {
        AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle).setMessage(message)
            .setPositiveButton(positiveButtonText, positiveButtonClickListener)
            .setNegativeButton(negativeButtonText, negativeButtonClickListener).apply {
                if (!TextUtils.isEmpty(title)) {
                    setTitle(title)
                }

            }.create().apply {
                this.findViewById<TextView>(android.R.id.message)?.textSize = 16f
                this.setOnShowListener {
                    this.getButton(AlertDialog.BUTTON_POSITIVE).textSize = 16f
                    this.getButton(AlertDialog.BUTTON_NEGATIVE).textSize = 16f
                }
            }
    }

    fun setDismissEvent(event: () -> Unit): LushAlertDialog {
        dialog.setOnDismissListener { event() }
        return this
    }


    fun show() {
        dialog.show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun dismiss() {
        dialog.dismiss()
    }
}