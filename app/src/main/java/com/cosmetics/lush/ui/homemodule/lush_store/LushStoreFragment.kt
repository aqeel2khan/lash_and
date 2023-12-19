package com.cosmetics.lush.ui.homemodule.lush_store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.cosmetics.core.base.BaseFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.callTelephone
import com.cosmetics.core.utils.openMap
import com.cosmetics.domain.model.home.store.Store
import com.cosmetics.lush.R
import kotlinx.android.synthetic.main.fragment_lush_store.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LushStoreFragment : BaseFragment() {

    private val viewModel: LushStoreViewModel by viewModel()

    companion object {
        fun newInstance() = LushStoreFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lush_store, container, false)
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.storeList.observe(viewLifecycleOwner, Observer {
            initLushStoreRV(it)
        })
    }

    private fun initLushStoreRV(it: List<Store>?) {
        lushStoreRV.adapter = LushStoreAdapter(
            it, { activity?.callTelephone(it) }, { activity?.openMap(it) })
    }

}
