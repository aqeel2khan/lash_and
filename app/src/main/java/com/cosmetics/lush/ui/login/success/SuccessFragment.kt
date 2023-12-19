package com.cosmetics.lush.ui.login.success


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.cosmetics.core.utils.FragmentBinding
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentSuccessBinding
import com.cosmetics.lush.ui.HomeNavigationActivity
import com.cosmetics.lush.utils.HOME_RESULT_CODE
import com.cosmetics.lush.utils.LAUNCH_PATH


class SuccessFragment : Fragment() {

    private val fragmentSuccessBinding
            by FragmentBinding<FragmentSuccessBinding>(R.layout.fragment_success)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSuccessBinding.name = PreferenceDelegate.firstName
        return fragmentSuccessBinding.root
    }

    override fun onDestroy() {
        activity?.setResult(HOME_RESULT_CODE)
        super.onDestroy()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            if (activity?.intent?.extras != null) {
                activity?.intent?.extras?.let {
                    if (it.getInt(LAUNCH_PATH) == HOME_RESULT_CODE) {
                        activity?.setResult(HOME_RESULT_CODE)
                        activity?.finish()
                    } else {
                        startActivity(Intent(requireActivity(), HomeNavigationActivity::class.java))
                        requireActivity().finishAffinity()
                    }
                }
            } else {
                startActivity(Intent(requireActivity(), HomeNavigationActivity::class.java))
                requireActivity().finishAffinity()
            }
        }, 1500)

    }
}
