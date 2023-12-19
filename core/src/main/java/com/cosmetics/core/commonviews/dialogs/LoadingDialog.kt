package com.cosmetics.core.commonviews.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.cosmetics.core.R

class LoadingDialog(context: Context) : Dialog(context),
    LifecycleObserver {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(true)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.loading_dialog)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = lp
    }

    fun toggle(status: Boolean = true) {
        if (status) {
            if (!isShowing) {
                show()
            }
        } else {
            cancel()
        }
    }

    fun disableBack(): LoadingDialog {
        setCancelable(false)
        return this
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun release() {
        cancel()
    }

}
