package com.cosmetics.lush.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cosmetics.core.base.BaseActivity
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.HomeNavigationActivity
import com.cosmetics.lush.ui.login.LoginNavigationActivity
import kotlinx.android.synthetic.main.fragment_language_selection.*


class LanguageSelectionFragment : Fragment() {


    lateinit var baseActivity: BaseActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseActivity = activity as BaseActivity
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_language_selection, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewArabic.setOnClickListener {
            baseActivity.changeToArabicLanguage()
            navigate()
        }
        textViewEnglish.setOnClickListener {
            baseActivity.changeToEnglishLanguage()
            navigate()
        }
    }

    fun navigate() {
        if (!PreferenceDelegate.customerId.isNullOrEmpty() ||
            !PreferenceDelegate.token.isNullOrEmpty()
        ) {
            startActivity(Intent(requireActivity(), HomeNavigationActivity::class.java))
        } else {
            startActivity(Intent(requireActivity(), LoginNavigationActivity::class.java))
        }
        requireActivity().finish()
    }

}