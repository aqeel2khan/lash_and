package com.cosmetics.lush.ui.login.login

/*import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult*/
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.EventObserver
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentLoginBinding
import com.cosmetics.lush.ui.HomeNavigationActivity
import com.cosmetics.lush.utils.launchActivity
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseBindingFragment<FragmentLoginBinding>() {
    // private var callbackManager: CallbackManager? = null
    private val viewModel by viewModel<LoginViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun getViewModel(): BaseViewModel = viewModel

    /*   override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)*//*
        PreferenceDelegate.token = ""
        viewModel.getToken(false) {}*//*
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClickListener()
    }
    private fun handleClickListener() {
        backIV.setOnClickListener { activity?.finish() }
        registerBT.setOnClickListener {
                navigateToRegisterPage()
        }
        recoverPasswordTV.setOnClickListener {
            viewModel.tokenCheck {
                findNavController().navigate(R.id.action_loginFragment_to_recoverPasswordFragment)
            }
        }

        viewModel.successLiveData.observe(viewLifecycleOwner, EventObserver { success ->
            if (success) {
                PreferenceDelegate.isLanguageSelectionScreenShown = true
                findNavController().navigate(R.id.action_loginFragment_to_successFragment)
            }
        })

        skipTV.setOnClickListener {
            viewModel.tokenCheck {
                PreferenceDelegate.isLanguageSelectionScreenShown = true
                activity?.launchActivity<HomeNavigationActivity>()
                activity?.finish()
            }
        }
        /* callbackManager = CallbackManager.Factory.create()
         facebookBT.setOnClickListener {
             handleFacebookLoginClick()
         }*/
    }

    private fun navigateToRegisterPage() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    /* private fun handleFacebookLoginClick() {
         LoginManager.getInstance().logInWithReadPermissions(
             this,
             listOf("user_photos", "email", "public_profile", "user_posts")
         )
         LoginManager.getInstance().registerCallback(callbackManager,
             object : FacebookCallback<LoginResult?> {
                 override fun onSuccess(loginResult: LoginResult?) {
                     val request = GraphRequest.newMeRequest(
                         loginResult?.accessToken
                     ) { _, response ->
                         Toast.makeText(activity, "Success", Toast.LENGTH_SHORT)
                             .show()// App code
                     }
                     val parameters = Bundle()
                     parameters.putString("fields", "id,name,email,mobile_no")
                     request.parameters = parameters
                     request.executeAsync()

                 }

                 override fun onCancel() { // App code
                     Toast.makeText(activity, "onCancel", Toast.LENGTH_SHORT).show()// App code
                 }

                 override fun onError(exception: FacebookException) { // App code
                     Toast.makeText(activity, "onError", Toast.LENGTH_SHORT).show()// App code
                 }
             })
     }*/
/*
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }*/
}
