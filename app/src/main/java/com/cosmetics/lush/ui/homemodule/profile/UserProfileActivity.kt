package com.cosmetics.lush.ui.homemodule.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.cosmetics.core.base.BaseActivity
import com.cosmetics.core.utils.setUpBackToolbar
import com.cosmetics.lush.R
import kotlinx.android.synthetic.main.activity_home_navigation.*
import kotlinx.android.synthetic.main.home_tab.view.*


class UserProfileActivity : BaseActivity() {
    private var appBarConfiguration: AppBarConfiguration? = null
    private val navController by lazy { findNavController(R.id.nav_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        setUpBackToolbar(lushToolbar, getString(R.string.user_profile))

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.profileFragment -> {
                    setHomeTitle(getString(R.string.user_profile))
                }
                R.id.addressListFragment -> {
                    setHomeTitle(getString(R.string.my_address))
                }
                else -> {
                    setHomeTitle("")
                }
            }
        }
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_fragment).navigateUpOrFinish(this)
    /*  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
          setUpMenuItemListener(item)
          return super.onOptionsItemSelected(item)
      }*/
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_search_menu, menu)
        setUpChatCount(2, menu?.findItem(R.id.actionCart))
        return super.onCreateOptionsMenu(menu)
    }*/

    private fun setHomeTitle(title: String) {
        if (!title.isNullOrEmpty()) {
            lushToolbar.titleTV.text = title
        }
    }
}

fun NavController.navigateUpOrFinish(activity: AppCompatActivity): Boolean =
    if (navigateUp()) {
        true
    } else {
        activity.finish()
        true
    }