package com.cosmetics.domain.interaction.product.review

import com.cosmetics.domain.model.product.review.PostReviewRequest
import com.cosmetics.domain.repository.ProductRepository

class PostProductReviewUseCaseImpl
    (private val productRepository: ProductRepository) : PostProductReviewUseCase {
    override suspend fun invoke(postReviewRequest: PostReviewRequest) =
        productRepository.postReview(postReviewRequest)
}