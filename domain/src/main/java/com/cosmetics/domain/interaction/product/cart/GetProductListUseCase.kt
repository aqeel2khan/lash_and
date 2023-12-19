package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.cart.AddCouponRequest
import com.cosmetics.domain.model.home.profile.CartCountResponse
import com.cosmetics.domain.model.product.ProductItem
import com.cosmetics.domain.model.product.ProductListRequest
import com.cosmetics.domain.model.product.ProductListResponse
import com.cosmetics.domain.model.product.cart.AddProductToCartRequest
import com.cosmetics.domain.model.product.cart.ProductInCartResponse
import com.cosmetics.domain.model.product.cart.UpdateCartRequest
import com.cosmetics.domain.model.product.wishlist.ProductWishListResponse

interface GetProductListUseCase : BaseUseCase<Int, ProductListResponse> {
    override suspend operator fun invoke(categoryId: Int): Results<ProductListResponse>
}

interface GetProductSortedListUseCase : BaseUseCase<ProductListRequest, ProductListResponse> {
    override suspend operator fun invoke(productListRequest: ProductListRequest): Results<ProductListResponse>
}

interface GetProductWithCategoryUseCase : BaseUseCase<Int, ProductItem> {
    override suspend operator fun invoke(categoryId: Int): Results<ProductItem>
}

interface GetRelatedProductListUseCase : BaseUseCase<Int, ProductListResponse> {
    override suspend operator fun invoke(categoryId: Int): Results<ProductListResponse>
}

interface SearchProductUseCase : BaseUseCase<String, ProductListResponse> {
    override suspend operator fun invoke(productName: String): Results<ProductListResponse>
}

interface AddProductToCartUseCase : BaseUseCase<AddProductToCartRequest, AddToCartResponse> {
    override suspend operator fun invoke(addProductToCartRequest: AddProductToCartRequest): Results<AddToCartResponse>
}

interface GetCartCountUseCase : BaseUseCase<String, CartCountResponse> {
    override suspend operator fun invoke(emptyString: String): Results<CartCountResponse>
}

interface UpdateCartUseCase : BaseUseCase<UpdateCartRequest, BaseResponse> {
    override suspend operator fun invoke(updateCartRequest: UpdateCartRequest): Results<BaseResponse>
}

interface AddCouponToOrderUseCase : BaseUseCase<AddCouponRequest, BaseResponse> {
    override suspend operator fun invoke(addCouponRequest: AddCouponRequest): Results<BaseResponse>
}

interface RemoveCouponFromOrderUseCase : BaseUseCase<String, BaseResponse> {
    override suspend operator fun invoke(emptyString: String): Results<BaseResponse>
}

interface GetProductFromCartUseCase : BaseUseCase<String, ProductInCartResponse> {
    override suspend operator fun invoke(emptyString: String): Results<ProductInCartResponse>
}

interface AddToWishListUseCase : BaseUseCase<Int, ProductListResponse> {
    override suspend operator fun invoke(categoryId: Int): Results<ProductListResponse>
}

interface GetWishListUseCase : BaseUseCase<String, ProductWishListResponse> {
    override suspend operator fun invoke(emptyString: String): Results<ProductWishListResponse>
}

interface RemoveFromWishListUseCase : BaseUseCase<Int, BaseResponse> {
    override suspend operator fun invoke(categoryId: Int): Results<BaseResponse>
}