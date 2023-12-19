package com.cosmetics.lush.ui.homemodule.edit_address

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
import com.cosmetics.lush.databinding.FragmentEditAddressBinding
import kotlinx.android.synthetic.main.fragment_edit_address.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressInputFragment : BaseBindingFragment<FragmentEditAddressBinding>(),
    View.OnClickListener {
    private var isZoneSpinnerClickedWithListEmpty = false//Becz zonelist is retrieved from API.
    override fun getViewModel(): BaseViewModel = inputViewModel
    override fun getLayoutId(): Int = R.layout.fragment_edit_address
    private val inputViewModel: AddressInputViewModel by viewModel()
    private val addressInputFragmentArgs: AddressInputFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonHomeContent = context
            ?.assets
            ?.open(getString(R.string.file_areas))
            ?.bufferedReader().use { it?.readText() }
        jsonHomeContent?.let { inputViewModel.setGovernateAndArea(it) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        spinnerZoneClicker.setOnClickListener(this)
        addressInputFragmentArgs.address?.let { it -> inputViewModel.initUserData(it) }
        addressInputFragmentArgs.type.let { it -> inputViewModel.addressType = it }
        inputViewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                if (addressInputFragmentArgs.type == GUEST_SHIPPING) {
                    findNavController().navigate(
                        AddressInputFragmentDirections
                            .actionEditAddressFragmentToDeliveryMethodFragment()
                    )
                } else {
                    findNavController().popBackStack()
                }
            }
        })
        inputViewModel.zones.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty() && isZoneSpinnerClickedWithListEmpty) {
                spinnerZone.performClick()
                isZoneSpinnerClickedWithListEmpty = false
            }
        })
        inputViewModel._areas.observe(viewLifecycleOwner, Observer {
            spinnerArea.adapter=null
            inputViewModel.areas.value=it
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.spinnerZoneClicker -> {
                if (inputViewModel.zones.value.isNullOrEmpty()) {
                    isZoneSpinnerClickedWithListEmpty = true
                    inputViewModel.getZoneId()
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
