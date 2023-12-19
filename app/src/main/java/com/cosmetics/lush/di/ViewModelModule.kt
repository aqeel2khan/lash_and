package com.cosmetics.lush.di

import com.cosmetics.domain.model.product.Product
import com.cosmetics.lush.ui.CartCountViewModel
import com.cosmetics.lush.ui.MainViewModel
import com.cosmetics.lush.ui.homemodule.appinfo.AppInformationDetailsViewModel
import com.cosmetics.lush.ui.homemodule.cart.MyCartViewModel
import com.cosmetics.lush.ui.homemodule.cart.address.CartAddressViewModel
import com.cosmetics.lush.ui.homemodule.cart.delivery.DeliveryMethodViewModel
import com.cosmetics.lush.ui.homemodule.cart.payment.PaymentWebViewViewModel
import com.cosmetics.lush.ui.homemodule.cart.paymentmethod.PaymentMethodViewModel
import com.cosmetics.lush.ui.homemodule.categories.CategoriesViewModel
import com.cosmetics.lush.ui.homemodule.checkout.CheckOutViewModel
import com.cosmetics.lush.ui.homemodule.contactus.ContactUsViewModel
import com.cosmetics.lush.ui.homemodule.edit_address.AddressInputViewModel
import com.cosmetics.lush.ui.homemodule.give_review.GiveReviewViewModel
import com.cosmetics.lush.ui.homemodule.guest.GuestCreateUserViewModel
import com.cosmetics.lush.ui.homemodule.home.HomeViewModel
import com.cosmetics.lush.ui.homemodule.lush_store.LushStoreViewModel
import com.cosmetics.lush.ui.homemodule.orders.MyOrdersViewModel
import com.cosmetics.lush.ui.homemodule.orders.details.MyOrderDetailsViewModel
import com.cosmetics.lush.ui.homemodule.profile.AddressListViewModel
import com.cosmetics.lush.ui.homemodule.profile.ProfileViewModel
import com.cosmetics.lush.ui.login.login.LoginViewModel
import com.cosmetics.lush.ui.login.otp_verify.OtpVerificationBindingViewModel
import com.cosmetics.lush.ui.login.recover_password.ForgottenPasswordViewModel
import com.cosmetics.lush.ui.login.register.RegisterViewModel
import com.cosmetics.lush.ui.products.ProductListViewModel
import com.cosmetics.lush.ui.products.product_details.ProductDetailsViewModel
import com.cosmetics.lush.ui.products.product_details.productoption.ProductOptionsViewModel
import com.cosmetics.lush.ui.products.relatedproduct.RelatedProductsViewModel
import com.cosmetics.lush.ui.products.search.SearchViewModel
import com.cosmetics.lush.ui.products.wishlist.WishListViewModel
import com.cosmetics.lush.ui.settings.SettingsViewModel
import com.cosmetics.lush.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { (id: Int) -> GiveReviewViewModel(id, get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ForgottenPasswordViewModel(get()) }
    viewModel { OtpVerificationBindingViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { ProductListViewModel() }
    viewModel { SettingsViewModel(get()) }
    viewModel { WishListViewModel(get(), get()) }
    viewModel { ProductDetailsViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { ContactUsViewModel(get(), get()) }
    viewModel { CartCountViewModel(get()) }
    viewModel { MyCartViewModel(get(), get(), get(), get(), get()) }
    viewModel { ProfileViewModel(androidContext(), get(), get()) }
    viewModel { AddressListViewModel(get()) }
    viewModel { AddressInputViewModel(get(), get(), get(), get()) }
    viewModel { (isShippingAddressNeeded: Boolean) ->
        GuestCreateUserViewModel(
            get(),
            get(),
            get(),
            get(),
            isShippingAddressNeeded
        )
    }
    viewModel { MainViewModel(get(), get(), get(), get()) }
    viewModel { MyOrdersViewModel(get()) }
    viewModel { MyOrderDetailsViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { DeliveryMethodViewModel(get(), get(), get()) }
    viewModel { PaymentMethodViewModel(get(), get(), get()) }
    viewModel { (isShippingAddressNeeded: Boolean) ->
        CartAddressViewModel(
            get(),
            get(),
            get(),
            isShippingAddressNeeded
        )
    }
    viewModel { CheckOutViewModel(get(), get()) }
    viewModel { LushStoreViewModel(get()) }
    viewModel { RelatedProductsViewModel() }
    viewModel { (product: Product, quantity: String) ->
        ProductOptionsViewModel(product, quantity, get())
    }
    // viewModel { (id: String) -> AppInformationDetailsViewModel(id, get()) }
    viewModel { (id: Int) -> AppInformationDetailsViewModel(id, get()) }
    viewModel { PaymentWebViewViewModel(get(), get()) }
}


