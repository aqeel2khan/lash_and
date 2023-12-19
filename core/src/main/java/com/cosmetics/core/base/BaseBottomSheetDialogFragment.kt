package com.cosmetics.core.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.cosmetics.core.R
import com.cosmetics.core.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var baseActivity: BaseActivity

    abstract fun getViewModel(): BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity = activity as BaseActivity

        getViewModel().viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Loading -> baseActivity.showProgress()
                is Completed -> baseActivity.dismissProgress()
                is Failure -> {
                    baseActivity.dismissProgress()
                    showSnackBarError(it.pair.first, it.pair.second)
                }
            }
        })
    }

    fun showSnackBarError(message: String, retryCallback: (() -> Unit)?) {
        view?.let { showCommonAPISnackBar(message, getString(R.string.retry), it, retryCallback) }
    }

    fun setDialogPeakHeight() {
        /* dialog?.setOnShowListener { dialog ->
             val bottomSheet: FrameLayout? =
                 (dialog as BottomSheetDialog).findViewById(R.id.design_bottom_sheet)
             val coordinatorLayout: CoordinatorLayout = bottomSheet?.parent as CoordinatorLayout
             val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
             bottomSheetBehavior.peekHeight = bottomSheet.height
             coordinatorLayout.parent.requestLayout()
         }*/
    }

}