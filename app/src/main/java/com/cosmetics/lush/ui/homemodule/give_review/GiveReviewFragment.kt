package com.cosmetics.lush.ui.homemodule.give_review

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentGiveReviewBinding
import kotlinx.android.synthetic.main.fragment_give_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class GiveReviewFragment : BaseBindingFragment<FragmentGiveReviewBinding>() {
    private val giveReviewFragmentArgs: GiveReviewFragmentArgs by navArgs()
    private val viewModel: GiveReviewViewModel by viewModel {
        parametersOf(
            giveReviewFragmentArgs.productId
        )
    }

    override fun getLayoutId(): Int = R.layout.fragment_give_review

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.postRatingResponse.observe(viewLifecycleOwner, Observer {
            showSnackBarError(getString(R.string.thank_you_for_your_review))
            findNavController().popBackStack()
        })
        /* ratingRB.setOnRatingBarChangeListener { _, rating, _ ->
             ratingRB.rating = ceil(rating.toDouble()).toFloat();

         }*/
        ratingRB.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            if (fromUser) {
                viewModel.isRatingActive.value = true
                viewModel.rating.value = rating.toString()
                viewModel.ratingCount = rating.toInt()
            }
        }
    }


}
