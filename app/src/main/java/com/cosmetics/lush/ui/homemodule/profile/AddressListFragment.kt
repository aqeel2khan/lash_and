package com.cosmetics.lush.ui.homemodule.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cosmetics.core.base.BaseFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.model.home.profile.Address
import com.cosmetics.domain.model.home.profile.AddressList
import com.cosmetics.domain.model.home.profile.EDIT_ADDRESS
import com.cosmetics.domain.model.home.profile.NORMAL_ADDRESS
import com.cosmetics.lush.R
import kotlinx.android.synthetic.main.address_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
class AddressListFragment : BaseFragment() {

    companion object {
        fun newInstance() = AddressListFragment()
    }

    private lateinit var addressListResponseObserver: Observer<AddressList>
    private var adapter: AddressAdapter? = null
    private val viewModel: AddressListViewModel by viewModel()
    private val addressListArgs by navArgs<AddressListFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addressListResponseObserver = Observer<AddressList> {
            initAddressRV(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.address_list_fragment, container, false)
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (addressListArgs.addressList?.addresses != null && addressListArgs.addressList?.addresses?.isNotEmpty()!!) {
            initAddressRV(addressListArgs.addressList!!)
        }
        cardAddNewAddress.setOnClickListener {
            navigateToEditAddress(null, NORMAL_ADDRESS)
        }
        viewModel.getAddressList().observe(viewLifecycleOwner, addressListResponseObserver)
    }

    private fun initAddressRV(addressList: AddressList) {
        if (addressRV.adapter == null) {
            adapter = AddressAdapter(addressList) {
                navigateToEditAddress(it, EDIT_ADDRESS)
            }
            addressRV.adapter = adapter
        } else {
            adapter?.updateList(addressList)
        }
    }

    private fun navigateToEditAddress(
        it: Address?,
        type: Int
    ) {
        findNavController().navigate(
            AddressListFragmentDirections.actionAddressListFragmentToEditAddressFragment(it, type)
        )
    }

    override fun onStop() {
        super.onStop()
        adapter = null
    }
}
