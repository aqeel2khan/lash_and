package com.cosmetics.lush.ui.login.recover_password

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.hideKeyboard
import com.cosmetics.core.utils.showSnackBarLessTime
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentForgotPasswordBinding
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgottenPasswordFragment : BaseBindingFragment<FragmentForgotPasswordBinding>() {

    companion object {
        fun newInstance() = ForgottenPasswordFragment()
    }

    private val viewModel by viewModel<ForgottenPasswordViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_forgot_password

    override fun getViewModel(): BaseViewModel = viewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.successLiveData.observe(viewLifecycleOwner,
            Observer {
                activity?.showSnackBarLessTime(getString(R.string.forgot_password_email_sent_message))
                findNavController().popBackStack()
            })
        backIV.setOnClickListener { findNavController().popBackStack() }
        cancelTV.setOnClickListener { findNavController().popBackStack() }
    }

    override fun onStop() {
        super.onStop()
        view?.hideKeyboard()
    }
}
