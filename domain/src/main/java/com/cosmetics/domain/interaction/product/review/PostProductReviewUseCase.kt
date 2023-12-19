package com.cosmetics.domain.interaction.product.review

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.review.PostReviewRequest

interface PostProductReviewUseCase : BaseUseCase<PostReviewRequest, BaseResponse> {
    override suspend operator fun invoke(postReviewRequest: PostReviewRequest)
            : Results<BaseResponse>
}