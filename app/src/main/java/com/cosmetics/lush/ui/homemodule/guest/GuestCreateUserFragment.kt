package com.cosmetics.lush.ui.homemodule.guest

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.hideKeyboard
import com.cosmetics.domain.model.home.profile.GUEST_SHIPPING
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentGuestCreateUserBinding
import com.cosmetics.lush.utils.navigate
import kotlinx.android.synthetic.main.fragment_edit_address.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GuestCreateUserFragment : BaseBindingFragment<FragmentGuestCreateUserBinding>(),
    View.OnClickListener {
    private var isZoneSpinnerClickedWithListEmpty = false
    private val guestCreateUserFragmentArgs: GuestCreateUserFragmentArgs by navArgs()
    private val viewModel: GuestCreateUserViewModel by viewModel {
        parametersOf(
            guestCreateUserFragmentArgs.isShippingAddressNeeded
        )
    }

    override fun getViewModel(): BaseViewModel = viewModel
    override fun getLayoutId(): Int = R.layout.fragment_guest_create_user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonHomeContent = context
            ?.assets
            ?.open(getString(R.string.file_areas))
            ?.bufferedReader().use { it?.readText() }
        jsonHomeContent?.let { viewModel.setGovernateAndArea(it) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        spinnerZoneClicker.setOnClickListener(this)
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                if (viewModel.isSameDeliveryShippingAddress()) {
                    findNavController().navigate(
                        GuestCreateUserFragmentDirections
                            .actionGuestCreateUserFragmentToDeliveryMethodFragment()
                    )
                } else {
                    navigateToShippingAddress()
                }
            }
        })
        viewModel.zones.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty() && isZoneSpinnerClickedWithListEmpty) {
                spinnerZone.performClick()
                isZoneSpinnerClickedWithListEmpty = false
            }
        })
        viewModel._areas.observe(viewLifecycleOwner, Observer {
            spinnerArea.adapter = null
            viewModel.areas.value = it
        })
        viewModel.navigateToPaymentSelection.observe(viewLifecycleOwner, Observer {
            if (it) {
                navigate(
                    GuestCreateUserFragmentDirections.actionGuestCreateUserFragmentToPaymentMethodFragment()
                )
                viewModel.navigateToPaymentSelection.value = false/*
                viewModel.isPaymentAddressSelectionActive.value = true*/
            }
        })
    }

    private fun navigateToShippingAddress() {
        findNavController().navigate(
            GuestCreateUserFragmentDirections
                .actionGuestCreateUserFragmentToEditAddressFragment(
                    null,
                    GUEST_SHIPPING
                )
        )
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.spinnerZoneClicker -> {
                if (viewModel.zones.value.isNullOrEmpty()) {
                    isZoneSpinnerClickedWithListEmpty = true
                    viewModel.getZoneId()
                } else {
                    spinnerZone.performClick()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        view?.hideKeyboard()
    }
}
