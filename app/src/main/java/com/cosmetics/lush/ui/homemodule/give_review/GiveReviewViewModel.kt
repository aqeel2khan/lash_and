package com.cosmetics.lush.ui.homemodule.give_review

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.result.Result
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.interaction.product.review.PostProductReviewUseCase
import com.cosmetics.domain.model.product.review.PostReviewRequest
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.getUserName
import com.cosmetics.lush.utils.ObservableViewModel

class GiveReviewViewModel(
    private val productId: Int,
    private val postProductReviewUseCase: PostProductReviewUseCase,
    private val context: Context
) : ObservableViewModel() {
    var ratingCount: Int = 0
    var postRatingResponse: MutableLiveData<BaseResponse> = MutableLiveData()
    var rating: MutableLiveData<String> = MutableLiveData()
    var reviewerName: String? = getUserName().trim()
    var reviewDescription: String? = null
    var isRatingActive: MutableLiveData<Boolean> = MutableLiveData(false)
    override fun getBindingId(): Int = BR.viewModel

    private fun postReview() {
        executeUseCase {
            postProductReviewUseCase(
                PostReviewRequest(
                    reviewerName!!, rating.value ?: "", reviewDescription!!, productId
                )
            )
                .onSuccess {
                    viewState.value = Completed
                    val response = Result.Success(it).data
                    postRatingResponse.value = response
                }
                .onFailure {
                    handleError(it)
                }
        }
    }

    private fun isDataValid(): Boolean =
        when {
            ratingCount < 1 -> {
                handleError(context.getString(R.string.please_select_a_rating))
                false
            }
            reviewerName.isNullOrEmpty() -> {
                handleError(context.getString(R.string.please_enter_ur_name))
                false
            }
            reviewDescription.isNullOrEmpty() -> {
                handleError(context.getString(R.string.please_enter_ur_description))
                false
            }
            else -> {
                true
            }
        }

    fun submitReview() {
        if (isDataValid()) {
            postReview()
        }
    }

}
