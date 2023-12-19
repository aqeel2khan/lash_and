package com.cosmetics.core.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.cosmetics.core.R
import com.cosmetics.core.utils.*
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    lateinit var baseActivity: BaseActivity

    abstract fun getViewModel(): BaseViewModel

    var snackbar: Snackbar? = null
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
        getViewModel().dismissSnackBar.observe(viewLifecycleOwner, Observer {
            if (it) {
                dismissSnackBar()
            }
        })
    }

    fun showSnackBarError(message: String, retryCallback: (() -> Unit)? = null) {
        dismissSnackBar()
        view?.let {
            snackbar = showCommonAPISnackBar(message, getString(R.string.retry), it, retryCallback)
        }
    }

    fun showNonDismissSnackBar(message: String, retryCallback: (() -> Unit)? = null) {
        dismissSnackBar()
        view?.let {
            showCommonAPISnackBar(message, getString(R.string.retry), it, retryCallback)
        }
    }

    fun dismissSnackBar() {
        try {
            snackbar?.let {
                if (it.isShown) {
                    it.dismiss()
                }
            }
        } catch (e: Exception) {
        }
    }

    override fun onDestroy() {
        dismissSnackBar()
        getViewModel().viewState.value = null
        //baseActivity.dismissProgress()
        super.onDestroy()
    }
}