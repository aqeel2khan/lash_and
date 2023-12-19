package com.cosmetics.lush.ui.homemodule.appinfo

import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.model.home.store.valueFromHtml
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentOrderDetailsBinding
import com.cosmetics.lush.ui.HomeNavigationActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class AppInformationDetailsFragment : BaseBindingFragment<FragmentOrderDetailsBinding>() {
    private val args: AppInformationDetailsFragmentArgs by navArgs()
    private val viewModel: AppInformationDetailsViewModel by viewModel {
        parametersOf(
            args.appInformation.id
        )
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getLayoutId(): Int = R.layout.fragment_app_info_details

    override fun onResume() {
        super.onResume()
        setHomeTittle()
        viewModel.appInformation.observe(this, Observer {

        })
        /*viewModel.appInformation.value = activity?.assets?.open(args.appInformation.fileName)
            ?.bufferedReader().use {
                it?.readText()
            }*/
    }

    private fun setHomeTittle() {
        if (activity is HomeNavigationActivity)
            (activity as HomeNavigationActivity).setHomeTitle(
                args.appInformation.title.valueFromHtml()
            )
    }
}