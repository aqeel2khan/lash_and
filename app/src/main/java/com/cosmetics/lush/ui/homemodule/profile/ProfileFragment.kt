package com.cosmetics.lush.ui.homemodule.profile

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.model.home.profile.EDIT_ADDRESS
import com.cosmetics.domain.model.home.profile.NORMAL_ADDRESS
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentProfileBinding
import com.cosmetics.lush.ui.HeaderViewUserProfile
import com.cosmetics.lush.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_address.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseBindingFragment<FragmentProfileBinding>() {
    private val viewModel: ProfileViewModel by viewModel()
    private val sharedViewModel: MainViewModel by sharedViewModel()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getAddressList().observe(viewLifecycleOwner, Observer {
            //initAddressRV(it)
        })
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            sharedViewModel.userProfile.value = HeaderViewUserProfile()//Check the default value
        })
        textViewViewMore.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToAddressListFragment(
                    viewModel.addressResponse
                )
            )
        }
        imageViewEdit.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToEditAddressFragment(
                    viewModel.address.value,
                    EDIT_ADDRESS
                )
            )
        }
        textViewAddNewAddress.setOnClickListener {
            viewModel.viewState.value = Completed
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToEditAddressFragment(
                    null,
                    NORMAL_ADDRESS
                )
            )
        }
        buttonAddNewAddress.setOnClickListener {
            viewModel.viewState.value = Completed
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToEditAddressFragment(
                    null,
                    NORMAL_ADDRESS
                )
            )
        }
    }
}
