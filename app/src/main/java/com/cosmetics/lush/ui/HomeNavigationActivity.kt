package com.cosmetics.lush.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.cosmetics.core.base.BaseActivity
import com.cosmetics.core.utils.LKEYS
import com.cosmetics.core.utils.setOnAllItemClickListener
import com.cosmetics.core.utils.setUpChatCount
import com.cosmetics.core.utils.showCancelableSnackBar
import com.cosmetics.data.utils.session
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.model.home.appinfo.AppInfo
import com.cosmetics.domain.model.home.request.Category
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.homemodule.appinfo.AppInformationDetailsFragmentArgs
import com.cosmetics.lush.ui.homemodule.cart.MyCartActivity
import com.cosmetics.lush.ui.homemodule.cart.payment.PaymentWebViewViewModel
import com.cosmetics.lush.ui.homemodule.drawer.DrawerAppInfoAdapter
import com.cosmetics.lush.ui.homemodule.drawer.DrawerCategoryAdapter
import com.cosmetics.lush.ui.settings.clearUserSession
import com.cosmetics.lush.utils.*
import com.infideap.drawerbehavior.AdvanceDrawerLayout
import kotlinx.android.synthetic.main.activity_home_navigation.*
import kotlinx.android.synthetic.main.drawer_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

fun AppCompatActivity.openCart() {
    Intent(this, MyCartActivity::class.java).apply {
        this.putExtra(LAUNCH_PATH, CART_RESULT_CODE)
        startActivityForResult(this, CART_RESULT_CODE)
    }
}

class HomeNavigationActivity : BaseActivity() {
    //https://github.com/shiburagi/Drawer-Behavior
    private var toggle: ActionBarDrawerToggle? = null
    private var menu: Menu? = null
    private var drawerToggle: AdvanceDrawerLayout? = null
    private val mainViewModel: MainViewModel by viewModel()
    private val paymentWebViewViewModel: PaymentWebViewViewModel by viewModel()
    private val cartCountViewModel: CartCountViewModel by viewModel()
    private var drawerCategoryAdapter: DrawerCategoryAdapter? = null
    private val navController by lazy { findNavController(R.id.nav_fragment) }
    private val homeBarTitleTV by lazy {
        lushToolbar.findViewById<TextView>(R.id.homeBarTitleTV)
    }
    private val homeBarIV by lazy {
        lushToolbar.findViewById<ImageView>(R.id.homeBarIV)
    }
    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.tab_home,
                R.id.tab_category,
                R.id.tab_lush_store,
                R.id.tab_my_orders
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_navigation)
        addAuthListener()
        initProfileInfoUI()
        setUpSlidingToolbar()
        setUpSlidingDrawer()
        //mainViewModel.callAPIs()
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        initObserver()
        handleDrawerClickListener()
        cartCountViewModel.getCartCount()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.cart_search_menu, menu)
        setUpChatCount(PreferenceDelegate.cartCountProducts, menu?.findItem(R.id.actionCart))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.actionSearch ->
                navController.navigate(
                    R.id.searchFragment, null, getNavOption()
                )
            R.id.actionCart ->
                openCart()
            else -> {

            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private fun getNavOption() = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()


    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    private fun initProfileInfoUI() {
        if (PreferenceDelegate.customerId.isNullOrEmpty()) {
            // userEmailTV.visibility = View.GONE
            loginButton.visibility = View.VISIBLE
            loginButton.setOnClickListener {
                //resetToDefaultLanguage()
                clearUserSession()
            }
        } else {
            loginButton.visibility = View.GONE
            userNameTV.text = getUserName()
            userEmailTV.text = PreferenceDelegate.email
        }
    }

    private fun initObserver() {
        mainViewModel.userProfile.observe(this, Observer {
            initProfileInfoUI()
        })
        cartCountViewModel.cartCount.observe(this, Observer {
            setUpChatCount(it, menu?.findItem(R.id.actionCart))
        })
        mainViewModel.appInformation.observe(this, Observer {
            drawerAppInfoRV.adapter = DrawerAppInfoAdapter(it) { appInfo ->
                drawerLayout.closeDrawers()
                withLessDelay {
                    navigateWithClearTask(
                        R.id.appInformationDetailsFragment,
                        bundle = AppInformationDetailsFragmentArgs(appInfo).toBundle()
                    )
                }
            }
        })
        mainViewModel.categoryData.observe(this, Observer {
            setDrawerItems(it.data)
        })
    }

    private fun setUpSlidingDrawer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewCompat.setLayoutDirection(
                drawerLayout,
                ViewCompat.LAYOUT_DIRECTION_LOCALE
            )
        }
        toggle =
            ActionBarDrawerToggle(
                this,
                drawerLayout,
                lushToolbar as Toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
        drawerLayout.addDrawerListener(toggle!!)
        toggle?.syncState()
        drawerLayout.setViewScale(GravityCompat.START, 0.8f)
        drawerLayout.setRadius(GravityCompat.START, 35f)
        drawerLayout.setViewElevation(GravityCompat.START, 20f)
        toggle?.setToolbarNavigationClickListener {
            onSupportNavigateUp()
        }
    }

    private fun setUpSlidingToolbar() {
        setSupportActionBar(lushToolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // this sets the button to the    back icon
        supportActionBar?.setHomeButtonEnabled(true) // makes it clickable
        setupDestinationChangeListener()
    }

    private fun setupDestinationChangeListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            toggle?.isDrawerIndicatorEnabled = false
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            supportActionBar?.show()
            bottomNavigationView.visibility = View.GONE
            when (destination.id) {
                R.id.wishListFragment -> {
                    setHomeTitle(getString(R.string.wishlist))
                }
                R.id.profileFragment -> {
                    setHomeTitle(getString(R.string.user_profile))
                }
                R.id.productDetailsFragment -> {
                    setHomeTitle(getString(R.string.product_details))
                }
                R.id.subCategoryFragment -> {
                    setHomeTitle(getString(R.string.categories))
                }
                R.id.searchFragment -> {
                    supportActionBar?.hide()
                }
                R.id.contactUsFragment -> {
                    setHomeTitle(getString(R.string.contact_us))
                }
                R.id.relatedProductsFragment -> {
                    setHomeTitle(getString(R.string.related_product))
                }
                R.id.giveReviewFragment -> {
                    setHomeTitle(getString(R.string.give_a_review))
                }
                R.id.addressListFragment -> {
                    setHomeTitle(getString(R.string.my_address))
                }
                R.id.editAddressFragment -> {
                    setHomeTitle(getString(R.string.add_new_address))
                }
                R.id.giveReviewFragment -> {
                    setHomeTitle("")
                }
                R.id.orderDetailsFragment -> {
                    setHomeTitle(getString(R.string.order_status))
                }
                R.id.settingsFragment -> {
                    setHomeTitle(getString(R.string.settings))
                }
                R.id.tab_home, R.id.tab_category, R.id.tab_lush_store, R.id.tab_my_orders -> {
                    toggle?.isDrawerIndicatorEnabled = true
                    bottomNavigationView.visibility = View.VISIBLE
                    setHomeTitle("")
                }
            }
        }
    }

    private fun addAuthListener() {
        session.observe(this, Observer {
            if (it) {
                session.value = false
                clearUserSession()
            }
        })
    }

    private fun launchURL(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
        startActivity(intent)
    }

    private fun handleDrawerClickListener() {
        contactUsTV.setOnClickListener {
            drawerLayout.closeDrawers()
            withDrawerDelay {
                navigateWithClearTask(R.id.contactUsFragment)
            }
        }
        newsletterTV.setOnClickListener {
            drawerLayout.closeDrawers()
            withDrawerDelay {
                launchURL("https://lushkw.com/index.php?route=account/newsletter")
            }
        }
        /*refundNdReturnTV.setOnClickListener {
            drawerLayout.closeDrawers()
            openAppInfoPage(
                AppInfo(
                    getString(R.string.return_refund_us),
                    getString(R.string.return_refund_us_description)
                )
            )
        }
        aboutUsTV.setOnClickListener {
            drawerLayout.closeDrawers()
            openAppInfoPage(
                AppInfo(
                    getString(R.string.about_us),
                    getString(R.string.about_us_description)
                )
            )
        }
        termsConditionTV.setOnClickListener {
            drawerLayout.closeDrawers()
            openAppInfoPage(
                AppInfo(
                    getString(R.string.terms_conditions),
                    getString(R.string.terms_conditions_description)
                )
            )
        }
        privacyPolicyTV.setOnClickListener {
            drawerLayout.closeDrawers()
            openAppInfoPage(
                AppInfo(
                    getString(R.string.privacy_policy),
                    getString(R.string.privacy_policy_description)
                )
            )
        }*/
        homeTV.setOnClickListener {
            drawerLayout.closeDrawers()
            withDrawerDelay {
                navigateWithClearTask(R.id.tab_home, true)
            }
        }

        settingsItemTV.setOnClickListener {
            drawerLayout.closeDrawers()
            withDrawerDelay {
                navigateWithClearTask(R.id.settingsFragment)
            }
        }

        wishListTV.setOnClickListener {
            drawerLayout.closeDrawers()
            withDrawerDelay {
                navigateWithClearTask(R.id.wishListFragment)
            }
        }

        profileGrp.setOnAllItemClickListener {
            drawerLayout.closeDrawers()
            if (isUserLoggedIn()) {
                withDrawerDelay { navigateWithClearTask(R.id.profileFragment) }
            }
        }

        myOrdersTV.setOnClickListener {
            drawerLayout.closeDrawers()
            withDrawerDelay { navController.navigate(R.id.tab_my_orders) }
        }
        findLushStoresTV.setOnClickListener {
            drawerLayout.closeDrawers()
            withDrawerDelay { navController.navigate(R.id.tab_lush_store) }
        }
    }

    private fun openAppInfoPage(appInformation: AppInfo) {
        //withLessDelay {
        /*  navigateWithClearTask(
              R.id.appInformationDetailsFragment,
              bundle = AppInformationDetailsFragmentArgs(appInformation).toBundle()
          )*/
        //}
    }

    private fun navigateWithClearTask(id: Int, include: Boolean = false, bundle: Bundle? = null) {
        navController.popBackStack(R.id.tab_home, include)
        navController.navigate(id, bundle)
    }

    fun setHomeTitle(title: String) {
        if (TextUtils.isEmpty(title)) {
            homeBarIV.visibility = View.VISIBLE
            homeBarTitleTV.visibility = View.GONE
        } else {
            homeBarIV.visibility = View.GONE
            homeBarTitleTV.visibility = View.VISIBLE
            homeBarTitleTV.text = title
        }
    }

    fun setDrawerItems(data: List<Category>) {
        drawerCategoryAdapter = DrawerCategoryAdapter(data) {
            drawerLayout.closeDrawers()
            /* navController.navigate(
                 HomeContainerFragmentDirections.actionHomeFragmentToSubCategoryFragment(it)
             )*/
            navController.popBackStack(R.id.subCategoryFragment, true)
            navController.navigateWithBundle(
                LKEYS.CATEGORY_ITEM, R.id.subCategoryFragment, it
            )
        }
        drawerCategoryRV.adapter = drawerCategoryAdapter
    }

    fun getCurrentFragment(): Fragment? {
        try {
            val navHostFragment: NavHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment
            return navHostFragment.childFragmentManager.fragments[0]
        } catch (e: Exception) {
            return null
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (resultCode == HOME_RESULT_CODE) {
            mainViewModel.setUserProfileInfo()
            super.onActivityResult(requestCode, resultCode, data)
            /* getCurrentFragment()?.let {
                 if (it is MyOrderFragment) {
                     it.onActivityResult(requestCode, resultCode, data)
                 }
             }*/
        } else if (resultCode == CART_RESULT_CODE) {
            cartCountViewModel.cartCount.value = 0
        }
    }

    fun showViewCartSnackBar(message: String) {
        showCancelableSnackBar(message, getString(R.string.view_cart)) { openCart() }
    }
}


fun isUserLoggedIn() = !PreferenceDelegate.customerId.isNullOrEmpty()

