package com.cosmetics.data.di

import com.cosmetics.data.repository.DeliveryMethodRepositoryImpl
import com.cosmetics.data.repository.StoreRepositoryImpl
import com.cosmetics.data.repository.home.AppInfoRepositoryImpl
import com.cosmetics.data.repository.home.ContactUsRepositoryImpl
import com.cosmetics.data.repository.home.HomeBannerRepositoryImpl
import com.cosmetics.data.repository.home.UserProfileRepositoryImpl
import com.cosmetics.data.repository.home.cart.CartRepositoryImpl
import com.cosmetics.data.repository.home.categories.CategoriesRepositoryImpl
import com.cosmetics.data.repository.home.product.ProductsRepositoryImpl
import com.cosmetics.data.repository.home.product.WishListRepositoryImpl
import com.cosmetics.data.repository.login.LoginRepositoryImpl
import com.cosmetics.data.repository.login.LogoutRepositoryImpl
import com.cosmetics.data.repository.login.forgotpassword.ForgotPasswordRepositoryImpl
import com.cosmetics.data.repository.login.gettoken.GetTokenRepositoryImpl
import com.cosmetics.data.repository.login.register.RegisterRepositoryImpl
import com.cosmetics.domain.repository.*
import com.cosmetics.domain.repository.forgotpassword.ForgotPasswordRepository
import com.cosmetics.domain.repository.home.DeliveryMethodRepository
import com.cosmetics.domain.repository.home.StoreRepository
import com.cosmetics.domain.repository.home.UserProfileRepository
import com.cosmetics.domain.repository.home.banner.HomeBannerRepository
import com.cosmetics.domain.repository.login.GetTokenRepository
import com.cosmetics.domain.repository.login.LoginRepository
import com.cosmetics.domain.repository.login.LogoutRepository
import com.cosmetics.domain.repository.login.RegisterRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<LoginRepository> { LoginRepositoryImpl(get()) }
    factory<LogoutRepository> { LogoutRepositoryImpl(get()) }
    factory<RegisterRepository> { RegisterRepositoryImpl(get()) }
    factory<GetTokenRepository> { GetTokenRepositoryImpl(get()) }
    factory<HomeBannerRepository> { HomeBannerRepositoryImpl(get()) }
    factory<CategoriesRepository> { CategoriesRepositoryImpl(get()) }
    factory<ProductRepository> { ProductsRepositoryImpl(get()) }
    factory<WishListRepository> { WishListRepositoryImpl(get()) }
    factory<ContactUsRepository> { ContactUsRepositoryImpl(get()) }
    factory<UserProfileRepository> { UserProfileRepositoryImpl(get()) }
    factory<DeliveryMethodRepository> { DeliveryMethodRepositoryImpl(get()) }
    factory<StoreRepository> { StoreRepositoryImpl(get()) }
    factory<CartRepository> { CartRepositoryImpl(get()) }
    factory<AppInfoRepository> { AppInfoRepositoryImpl(get()) }
    factory<ForgotPasswordRepository> { ForgotPasswordRepositoryImpl(get()) }
}