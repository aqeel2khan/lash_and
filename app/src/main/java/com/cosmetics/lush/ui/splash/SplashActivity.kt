package com.cosmetics.lush.ui.splash

import android.os.Bundle
import android.view.WindowManager
import com.cosmetics.core.base.BaseActivity
import com.cosmetics.lush.R

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_navigation)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

}
