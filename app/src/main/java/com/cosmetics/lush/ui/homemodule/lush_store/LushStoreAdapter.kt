package com.cosmetics.lush.ui.homemodule.lush_store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.home.store.Store
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.LushStoreItemBinding

class LushStoreAdapter(
    private val storeList: List<Store>?,
    private val telephoneClick: (number: String) -> Unit,
    private val openMap: (address: String) -> Unit
) :
    RecyclerView.Adapter<LushStoreAdapter.LushViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LushViewHolder {
        val lushStoreItemBinding: LushStoreItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.lush_store_item,
                parent,
                false
            )
        return LushViewHolder(lushStoreItemBinding)
    }

    override fun getItemCount(): Int = storeList?.size ?: 0


    override fun onBindViewHolder(holder: LushViewHolder, position: Int) {
        storeList?.get(position)?.let { holder.bind(it) }

    }

    inner class LushViewHolder(private val binder: LushStoreItemBinding) :
        RecyclerView.ViewHolder(binder.root) {

        fun bind(data: Store) {
            binder.store = data
            binder.mallTelephoneTV.setOnClickListener {
                data.telephone.let { number -> telephoneClick(number) }
            }
            binder.mallLocationTV.setOnClickListener {
                data.getAddressInText().let { storeAddress -> openMap(storeAddress) }
            }
        }
    }
}