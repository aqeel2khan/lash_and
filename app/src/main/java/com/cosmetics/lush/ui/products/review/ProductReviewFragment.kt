package com.cosmetics.lush.ui.products.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.cosmetics.lush.R
import kotlinx.android.synthetic.main.product_review_fragment.*

class ProductReviewFragment : Fragment() {
    private val productReviewFragmentArgs = navArgs<ProductReviewFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_review_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewReviewList.adapter = productReviewFragmentArgs.value.review.reviewList?.let {
            ProductReviewAdapter(
                it
            )
        }
    }
}
