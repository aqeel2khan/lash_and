package com.cosmetics.domain.di

import com.cosmetics.domain.interaction.account.forgotpassword.GetForgotPasswordUseCase
import com.cosmetics.domain.interaction.account.forgotpassword.GetForgotPasswordUseCaseImpl
import com.cosmetics.domain.interaction.account.logout.GetLogoutUseCase
import com.cosmetics.domain.interaction.account.logout.GetLogoutUseCaseImpl
import com.cosmetics.domain.interaction.account.register.GetRegisterUseCase
import com.cosmetics.domain.interaction.account.register.GetRegisterUseCaseImpl
import com.cosmetics.domain.interaction.account.token.GetTokenUseCase
import com.cosmetics.domain.interaction.account.token.GetTokenUseCaseImpl
import com.cosmetics.domain.interaction.account.userlogin.GetLoginUseCase
import com.cosmetics.domain.interaction.account.userlogin.GetLoginUseCaseImpl
import com.cosmetics.domain.interaction.appInfo.GetAppInformationDetailsUseCase
import com.cosmetics.domain.interaction.appInfo.GetAppInformationDetailsUseCaseImpl
import com.cosmetics.domain.interaction.appInfo.GetAppInformationUseCase
import com.cosmetics.domain.interaction.appInfo.GetAppInformationUseCaseImpl
import com.cosmetics.domain.interaction.banner.HomeBannerUseCase
import com.cosmetics.domain.interaction.banner.HomeBannerUseCaseImpl
import com.cosmetics.domain.interaction.categories.GetCategoryUseCase
import com.cosmetics.domain.interaction.categories.GetCategoryUseCaseImpl
import com.cosmetics.domain.interaction.contactus.SubmitContactUsUseCase
import com.cosmetics.domain.interaction.contactus.SubmitContactUsUseCaseImpl
import com.cosmetics.domain.interaction.order.*
import com.cosmetics.domain.interaction.product.cart.*
import com.cosmetics.domain.interaction.product.review.PostProductReviewUseCase
import com.cosmetics.domain.interaction.product.review.PostProductReviewUseCaseImpl
import com.cosmetics.domain.interaction.profile.*
import com.cosmetics.domain.interaction.profile.address.*
import com.cosmetics.domain.interaction.store.GetLushStoreUseCase
import com.cosmetics.domain.interaction.store.GetLushStoreUseCaseImpl
import org.koin.dsl.module

val interactionModule = module {
    factory<GetTokenUseCase> { GetTokenUseCaseImpl(get()) }
    factory<GetLoginUseCase> { GetLoginUseCaseImpl(get(), get()) }
    factory<GetLogoutUseCase> { GetLogoutUseCaseImpl(get()) }
    factory<GetRegisterUseCase> { GetRegisterUseCaseImpl(get(), get()) }
    factory<GetCategoryUseCase> { GetCategoryUseCaseImpl(get()) }
    factory<HomeBannerUseCase> { HomeBannerUseCaseImpl(get()) }
    factory<GetProductListUseCase> { GetProductListUseCaseImpl(get()) }
    factory<PostProductReviewUseCase> { PostProductReviewUseCaseImpl(get()) }
    factory<GetProductSortedListUseCase> { GetProductSortedListUseCaseImpl(get()) }
    factory<GetProductWithCategoryUseCase> { GetProductWithCategoryUseCaseImpl(get()) }
    factory<GetRelatedProductListUseCase> { GetRelatedProductListUseCaseImpl(get()) }
    factory<AddToWishListUseCase> { AddToWishListImpl(get()) }
    factory<GetWishListUseCase> { GetWishListUseCaseImpl(get()) }
    factory<RemoveFromWishListUseCase> { RemoveFromWishListUseCaseImpl(get()) }
    factory<SubmitContactUsUseCase> { SubmitContactUsUseCaseImpl(get()) }
    factory<AddProductToCartUseCase> { AddProductToCartUseCaseImpl(get()) }
    factory<GetProductFromCartUseCase> { GetProductFromCartUseCaseImpl(get()) }
    factory<GetCartCountUseCase> { GetCartCountUseCaseImpl(get()) }
    factory<GetUserProfileUseCase> { GetUserProfileUseCaseImpl(get()) }
    factory<UpdateCartUseCase> { UpdateCartUseCaseImpl(get()) }
    factory<UpdateUserProfileUseCase> { UpdateUserProfileUseCaseImpl(get()) }
    factory<GetAddressUseCase> { GetAddressUseCaseImpl(get()) }
    factory<GetDeliveryAddressUseCase> { GetDeliveryAddressUseCaseImpl(get()) }
    factory<SetDeliveryAddressUseCase> { SetDeliveryAddressUseCaseImpl(get()) }
    factory<EditAddressUseCase> { EditAddressUseCaseImpl(get()) }
    factory<GetCountryUseCase> { GetCountryUseCaseImpl(get()) }
    factory<GetZoneIdUseCase> { GetZoneIdUseCaseImpl(get()) }
    factory<SearchProductUseCase> { SearchProductUseCaseImpl(get()) }
    factory<GetDeliveryMethodUseCase> { GetDeliveryMethodUseCaseImpl(get()) }
    factory<SetDeliveryMethodUseCase> { SetDeliveryMethodUseCaseImpl(get()) }
    factory<GetLushStoreUseCase> { GetLushStoreUseCaseImpl(get()) }
    factory<GetPaymentMethodUseCase> { GetPaymentMethodUseCaseImpl(get()) }
    factory<SetPaymentMethodUseCase> { SetPaymentMethodUseCaseImpl(get()) }
    factory<GetConfirmedProductUseCase> { GetConfirmedProductUseCaseImpl(get()) }
    factory<GetOrderHistoryUseCase> { GetOrderHistoryUseCaseImpl(get()) }
    factory<ConfirmedOrderUseCase> { ConfirmedOrderUseCaseImpl(get()) }
    factory<GetMyOrderDetailUseCase> { GetMyOrderDetailUseCaseImpl(get()) }
    factory<GetOrderStatusUseCase> { GetOrderStatusUseCaseImpl(get()) }
    factory<RemoveCouponFromOrderUseCase> { RemoveCouponFromOrderUseCaseImpl(get()) }
    factory<AddCouponToOrderUseCase> { AddCouponToOrderUseCaseImpl(get()) }
    factory<GetAppInformationUseCase> { GetAppInformationUseCaseImpl(get()) }
    factory<GetAppInformationDetailsUseCase> { GetAppInformationDetailsUseCaseImpl(get()) }
    factory<CreateGuestUserUseCase> { CreateGuestUserUseCaseImpl(get()) }
    factory<GetKnetPayDetailsUseCase> { GetKnetPayDetailsUseCaseImpl(get()) }
    factory<GetForgotPasswordUseCase> { GetForgotPasswordUseCaseImpl(get()) }
}