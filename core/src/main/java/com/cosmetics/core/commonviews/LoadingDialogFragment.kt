package com.cosmetics.core.commonviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.cosmetics.core.R

class LoadingDialogFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        isCancelable = false

        return inflater.inflate(R.layout.loading_dialog_anim, container, false)
    }

    //over rid  this due to some issues that occur when trying to show a the dialog after onSaveInstanceState
    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (ignored: IllegalStateException) {

        }

    }

}