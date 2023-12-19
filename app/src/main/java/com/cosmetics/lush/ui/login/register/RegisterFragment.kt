package com.cosmetics.lush.ui.login.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.EventObserver
import com.cosmetics.core.utils.hideKeyboard
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentRegisterBinding
import com.cosmetics.lush.ui.HomeNavigationActivity
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseBindingFragment<FragmentRegisterBinding>() {

    private val viewModel by viewModel<RegisterViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_register

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("MyOrder", "onActivityCreated")
        backIV.setOnClickListener { findNavController().popBackStack() }
        viewModel.successLiveData.observe(viewLifecycleOwner, EventObserver { success ->
            if (success) {
                showSnackBarError(getString(R.string.registration_successful))
                findNavController().navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                )
            }
        })

        skipTV.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireActivity(),
                    HomeNavigationActivity::class.java
                )
            )
            requireActivity().finish()
        }
    }

    override fun onStop() {
        super.onStop()
        view?.hideKeyboard()
    }

}
