package com.cosmetics.lush.ui.homemodule.cart.address

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.home.profile.Address
import com.cosmetics.domain.model.home.profile.AddressList
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ItemAddressSelectionBinding

class AddressSelectionAdapter(
    private var addressList: AddressList,
    private val viewModel: CartAddressViewModel
) :
    RecyclerView.Adapter<AddressSelectionAdapter.AddressSelectionVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressSelectionVH {
        val view: ItemAddressSelectionBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_address_selection,
                parent,
                false
            )
        return AddressSelectionVH(view)
    }

    override fun getItemCount(): Int = addressList.addresses.size

    override fun onBindViewHolder(holder: AddressSelectionVH, position: Int) {
        holder.bind(addressList.addresses[position], position)
    }

    fun updateList(addressList: AddressList) {
        this.addressList = addressList
        notifyDataSetChanged()
    }

    inner class AddressSelectionVH(private val binder: ItemAddressSelectionBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(address: Address, position: Int) {
            binder.address = address
            binder.viewModel = viewModel
            if (position + 1 == addressList.addresses.size) {
                binder.viewSplitter.visibility = View.GONE
            }
        }
    }
}