package com.cosmetics.lush.ui.products.product_details.productoption

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.cosmetics.core.base.BaseBottomSheetDialogFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.CartCountViewModel
import com.cosmetics.lush.ui.HomeNavigationActivity
import kotlinx.android.synthetic.main.fragment_product_option_dialog.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ProductOptionDialogFragment : BaseBottomSheetDialogFragment() {
    private val productOptionDialogFragmentArgs: ProductOptionDialogFragmentArgs by navArgs()
    private val cartCountViewModel: CartCountViewModel by sharedViewModel()
    private val viewModel: ProductOptionsViewModel by viewModel {
        parametersOf(
            productOptionDialogFragmentArgs.product,
            productOptionDialogFragmentArgs.quantity
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setDialogPeakHeight()
        return inflater.inflate(R.layout.fragment_product_option_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserver()
        addToCartButton.setOnClickListener {
            viewModel.addToCart()
        }
    }

    private fun initObserver() {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            /*  showSnackBarError(
                  getString(
                      R.string.has_been_added_to_your_cart,
                      it.data.product.name
                  )
              )*/
            if (activity is HomeNavigationActivity) {
                (activity as HomeNavigationActivity).showViewCartSnackBar(
                    getString(R.string.has_been_added_to_your_cart, it.data.product.name)
                )
            }
            cartCountViewModel.getCartCount()
            findNavController().popBackStack()
            findNavController().popBackStack()
        })
    }

    private fun initRecyclerView() {
        recyclerViewProductOptions.adapter = ProductOptionMainAdapter(viewModel)
        activity?.let {
            val dividerItemDecoration =
                DividerItemDecoration(it, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(it, R.drawable.divider_horizontal)?.let { drawable ->
                dividerItemDecoration.setDrawable(drawable)
            }
            recyclerViewProductOptions.addItemDecoration(dividerItemDecoration)
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
