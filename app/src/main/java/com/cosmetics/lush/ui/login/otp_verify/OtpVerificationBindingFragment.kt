package com.cosmetics.lush.ui.login.otp_verify

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.EventObserver
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentOtpVerificationBinding
import kotlinx.android.synthetic.main.fragment_otp_verification.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class OtpVerificationBindingFragment : BaseBindingFragment<FragmentOtpVerificationBinding>() {

    companion object {
        fun newInstance() = OtpVerificationBindingFragment()
    }

    private val otpVerificationViewModel by viewModel<OtpVerificationBindingViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_otp_verification

    override fun getViewModel(): BaseViewModel = otpVerificationViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        backIV.setOnClickListener { findNavController().popBackStack() }

        otpVerificationViewModel.successLiveData.observe(
            viewLifecycleOwner,
            EventObserver { success ->
                if (success) findNavController().navigate(R.id.action_otpVerificationFragment_to_successFragment)
            })
    }

}
