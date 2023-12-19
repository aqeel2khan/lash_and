package com.cosmetics.lush.ui.homemodule.contactus

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.callTelephone
import com.cosmetics.core.utils.openEmail
import com.cosmetics.core.utils.showSnackBarLessTime
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentContactUsBinding
import kotlinx.android.synthetic.main.fragment_contact_us.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactUsFragment : BaseBindingFragment<FragmentContactUsBinding>() {
    private val viewModel by viewModel<ContactUsViewModel>()
    override fun getViewModel(): BaseViewModel = viewModel

    override fun getLayoutId(): Int = R.layout.fragment_contact_us
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.successLiveData.observe(viewLifecycleOwner,
            {
                activity?.showSnackBarLessTime(
                    getString(R.string.submitted)
                )
                findNavController().popBackStack()
            })
        initOnClickListener()
    }

    private fun initOnClickListener() {
        locationEmailAddressTextView.setOnClickListener {
            activity?.openEmail(locationEmailAddressTextView.text.toString())
        }
        locationPhoneValueTextView.setOnClickListener {
            activity?.callTelephone(locationPhoneValueTextView.text.toString())
        }
    }

}

