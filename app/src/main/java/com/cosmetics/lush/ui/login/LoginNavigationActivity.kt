package com.cosmetics.lush.ui.login

import android.os.Bundle
import android.view.WindowManager
import com.cosmetics.core.base.BaseActivity
import com.cosmetics.lush.R

class LoginNavigationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_navigation)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
