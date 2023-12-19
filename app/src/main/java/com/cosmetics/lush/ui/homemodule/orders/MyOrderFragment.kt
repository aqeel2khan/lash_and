package com.cosmetics.lush.ui.homemodule.orders

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cosmetics.core.base.BaseActivity
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentMyOrdersBinding
import com.cosmetics.lush.ui.isUserLoggedIn
import com.cosmetics.lush.ui.login.LoginNavigationActivity
import com.cosmetics.lush.ui.settings.handlePreferenceAfterUserSession
import com.cosmetics.lush.utils.HOME_RESULT_CODE
import com.cosmetics.lush.utils.LAUNCH_PATH
import com.cosmetics.lush.utils.navigate
import kotlinx.android.synthetic.main.fragment_orders_history.*
import kotlinx.android.synthetic.main.user_login_prompt.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyOrderFragment : BaseBindingFragment<FragmentMyOrdersBinding>(), View.OnClickListener {
    private var orderHistoryPageAdapter: OrderHistoryPageAdapter? = null
    private val viewModel: MyOrdersViewModel by viewModel()
    override fun getLayoutId(): Int = R.layout.fragment_orders_history
    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initPagingList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileIV.setOnClickListener(this)
        imageViewBlackEdit.setOnClickListener(this)
        buttonLogin.setOnClickListener(this)
        initRecyclerView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.postLiveData?.observe(viewLifecycleOwner, Observer {
            orderHistoryPageAdapter?.submitList(it)
        })
        viewModel.getNetworkState()?.observe(viewLifecycleOwner, Observer {
            orderHistoryPageAdapter?.setNetworkState(it)
        })
    }

    private fun initRecyclerView() {
        orderHistoryPageAdapter = OrderHistoryPageAdapter({ viewModel.retry() }) {
            navigate(
                MyOrderFragmentDirections.actionTabMyOrdersToOrderDetailsFragment(
                    it
                )
            )
        }
        orderHistoryRV.adapter = orderHistoryPageAdapter
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.profileIV, R.id.imageViewBlackEdit -> {
                if (isUserLoggedIn()) {
                    findNavController().navigate(
                        MyOrderFragmentDirections.actionTabMyOrdersToProfileFragment()
                    )
                }
            }
            R.id.buttonLogin -> {
              //  changeToDefaultLanguage()
                handlePreferenceAfterUserSession()
                var intent = Intent(activity, LoginNavigationActivity::class.java)
                intent.putExtra(LAUNCH_PATH, HOME_RESULT_CODE)
                startActivityForResult(intent, HOME_RESULT_CODE)
            }
        }
    }

    private fun changeToDefaultLanguage() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).resetToDefaultLanguage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.refreshPage()
    }

}
