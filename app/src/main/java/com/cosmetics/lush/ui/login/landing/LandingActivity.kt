package com.cosmetics.lush.ui.login.landing

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.HomeNavigationActivity
import com.cosmetics.lush.ui.login.LoginNavigationActivity
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        loginBT.setOnClickListener {
            startActivity(Intent(this, LoginNavigationActivity::class.java))
            finish()
        }

        guestTV.setOnClickListener {
            startActivity(Intent(this, HomeNavigationActivity::class.java))
            finish()
        }

    }
}
