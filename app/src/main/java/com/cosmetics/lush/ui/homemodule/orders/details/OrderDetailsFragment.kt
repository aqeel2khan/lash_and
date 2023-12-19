package com.cosmetics.lush.ui.homemodule.orders.details

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.model.order.History
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentOrderDetailsBinding
import com.cosmetics.lush.ui.homemodule.order_status.OrderStatusAdapter
import com.cosmetics.lush.utils.navigate
import kotlinx.android.synthetic.main.fragment_order_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class OrderDetailsFragment : BaseBindingFragment<FragmentOrderDetailsBinding>() {
    private val viewModel: MyOrderDetailsViewModel by viewModel()
    private val orderDetailsFragmentArgs: OrderDetailsFragmentArgs by navArgs()
    override fun getViewModel(): BaseViewModel = viewModel

    override fun getLayoutId(): Int = R.layout.fragment_order_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.order = orderDetailsFragmentArgs.order
    }

    override fun onResume() {
        super.onResume()
        viewModel.initAPICall()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.orderStatuss.observe(viewLifecycleOwner, Observer {
            initOrderStatusRV(it)
        })
        contactUsTV.setOnClickListener {
            navigate(
                OrderDetailsFragmentDirections.actionOrderDetailsFragmentToContactUsFragment()
            )
        }
    }

    private fun initOrderStatusRV(orderStatusList: MutableList<History>) {
        orderStatusRV.adapter = activity?.let { OrderStatusAdapter(orderStatusList) }
    }

}
