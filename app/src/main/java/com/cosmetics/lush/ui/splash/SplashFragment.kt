package com.cosmetics.lush.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.HomeNavigationActivity
import com.cosmetics.lush.ui.login.LoginNavigationActivity

class SplashFragment : Fragment() {

    companion object {
        const val SPLASH_DURATION = 2000L
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private val handler = Handler()
    private val runnable = Runnable {
        if (!PreferenceDelegate.isLanguageSelectionScreenShown) {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLanguageSelectionFragment())
        } else {
            if (!PreferenceDelegate.customerId.isNullOrBlank() ||
                !PreferenceDelegate.token.isNullOrBlank()
            ) {
                startActivity(Intent(requireActivity(), HomeNavigationActivity::class.java))
            } else {
                startActivity(Intent(requireActivity(), LoginNavigationActivity::class.java))
            }
            requireActivity().finish()
        }
    }

    override fun onResume() {
        super.onResume()
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(runnable, SPLASH_DURATION)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}
