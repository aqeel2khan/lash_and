package com.cosmetics.lush.ui.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import com.cosmetics.core.base.BaseActivity
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.data.networking.interceptor.getLanguage
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentSettingsBinding
import com.cosmetics.lush.ui.MainViewModel
import com.cosmetics.lush.ui.isUserLoggedIn
import com.cosmetics.lush.ui.login.LoginNavigationActivity
import com.cosmetics.lush.utils.launchActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


fun Activity.clearUserSession() {
    //activity?.resetToDefaultLanguage()
    handlePreferenceAfterUserSession()
    finish()
    launchActivity<LoginNavigationActivity> {
        this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
}

fun handlePreferenceAfterUserSession() {
    val cachedIsLanguageSelectionScreenShown = PreferenceDelegate.isLanguageSelectionScreenShown
    val cachedLanguage = PreferenceDelegate.currentLanguage
    PreferenceDelegate.clearPreference()
    PreferenceDelegate.isLanguageSelectionScreenShown = cachedIsLanguageSelectionScreenShown
    PreferenceDelegate.currentLanguage = cachedLanguage
}

class SettingsFragment : BaseBindingFragment<FragmentSettingsBinding>(),
    CompoundButton.OnCheckedChangeListener {
    private val settingsViewModel: SettingsViewModel by viewModel()
    var activity: BaseActivity? = null
    private val viewModel: MainViewModel by sharedViewModel()

    override fun getViewModel(): BaseViewModel = settingsViewModel
    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) activity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arabicVersionSwitch.isChecked = getLanguage() == "ar"
        if (!isUserLoggedIn()) {
            logOutSwitch.text = getString(R.string.login)
        }
        settingsViewModel.logoutResponse.observe(viewLifecycleOwner, Observer {
            baseActivity.clearUserSession()
        })
        logOutSwitch.setOnClickListener {
            if (!isUserLoggedIn()) {
                baseActivity.clearUserSession()
            } else {
                settingsViewModel.logout()
            }
        }
        arabicVersionSwitch.setOnCheckedChangeListener(this)
    }


    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked)
            activity?.changeToArabicLanguage()
        else
            activity?.changeToEnglishLanguage()
        viewModel.refreshAPIonLocalChange()
    }

}