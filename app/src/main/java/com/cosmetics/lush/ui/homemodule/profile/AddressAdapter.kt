package com.cosmetics.lush.ui.homemodule.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.home.profile.Address
import com.cosmetics.domain.model.home.profile.AddressList
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ItemAddressBinding

class AddressAdapter(
    private var addressList: AddressList,
    private val onItemEdit: (address: Address) -> Unit
) :
    RecyclerView.Adapter<AddressAdapter.AddressVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressVH {
        val view: ItemAddressBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_address,
                parent,
                false
            )
        return AddressVH(view)
    }

    override fun getItemCount(): Int = addressList.addresses.size

    override fun onBindViewHolder(holder: AddressVH, position: Int) {
        holder.bind(addressList.addresses[position])
    }

    fun updateList(addressList: AddressList) {
        this.addressList = addressList
        notifyDataSetChanged()
    }

    inner class AddressVH(private val binder: ItemAddressBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(address: Address) {
            binder.address = address
            binder.imageViewEdit.setOnClickListener {
                onItemEdit(address)
            }
        }
    }
}