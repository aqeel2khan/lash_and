package com.cosmetics.domain.interaction.product.cart

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.ProductItem
import com.cosmetics.domain.model.product.ProductListRequest
import com.cosmetics.domain.model.product.ProductListResponse
import com.cosmetics.domain.model.product.wishlist.ProductWishListResponse
import com.cosmetics.domain.repository.ProductRepository
import com.cosmetics.domain.repository.WishListRepository

class GetProductListUseCaseImpl(private val productRepository: ProductRepository) :
    GetProductListUseCase {
    override suspend fun invoke(categoryId: Int): Results<ProductListResponse> =
        productRepository.getProductList(categoryId)
}

class GetProductSortedListUseCaseImpl(private val productRepository: ProductRepository) :
    GetProductSortedListUseCase {
    override suspend fun invoke(productListRequest: ProductListRequest): Results<ProductListResponse> =
        productRepository.getProductSortedList(productListRequest)
}

class GetProductWithCategoryUseCaseImpl(private val productRepository: ProductRepository) :
    GetProductWithCategoryUseCase {
    override suspend fun invoke(id: Int): Results<ProductItem> =
        productRepository.getProduct(id)
}

class GetRelatedProductListUseCaseImpl(private val productRepository: ProductRepository) :
    GetRelatedProductListUseCase {
    override suspend fun invoke(id: Int): Results<ProductListResponse> =
        productRepository.getRelatedProduct(id)
}

class AddToWishListImpl(private val wishListRepository: WishListRepository) :
    AddToWishListUseCase {
    override suspend fun invoke(categoryId: Int): Results<ProductListResponse> =
        wishListRepository.addToWishList(categoryId)
}

class GetWishListUseCaseImpl(private val wishListRepository: WishListRepository) :
    GetWishListUseCase {
    override suspend fun invoke(emptyString: String): Results<ProductWishListResponse> =
        wishListRepository.getWishlistUseCase()
}

class RemoveFromWishListUseCaseImpl(private val wishListRepository: WishListRepository) :
    RemoveFromWishListUseCase {
    override suspend fun invoke(categoryId: Int): Results<BaseResponse> =
        wishListRepository.removeFromWishList(categoryId)
}