package com.cosmetics.lush.ui.products.product_details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.rotate
import com.cosmetics.core.utils.showSnackBarLessTime
import com.cosmetics.core.utils.strikeThrough
import com.cosmetics.domain.model.product.Discount
import com.cosmetics.domain.model.product.Product
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentProductDetailsBinding
import com.cosmetics.lush.ui.CartCountViewModel
import com.cosmetics.lush.ui.HomeNavigationActivity
import com.cosmetics.lush.ui.products.ProductsAdapter
import com.cosmetics.lush.ui.products.product_details.adapter.DiscountAdapter
import com.cosmetics.lush.ui.products.product_details.adapter.ProductAttributeAdapter
import com.cosmetics.lush.ui.products.product_details.model.QuantityModel
import kotlinx.android.synthetic.main.fragment_product_details.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductDetailsFragment : BaseBindingFragment<FragmentProductDetailsBinding>(),
    ProductsAdapter.OnProductsClickListener, View.OnClickListener {

    private val viewModel: ProductDetailsViewModel by viewModel()
    private val cartCountViewModel: CartCountViewModel by sharedViewModel()
    private val productDetailsFragmentArgs: ProductDetailsFragmentArgs by navArgs()

    override fun getViewModel(): BaseViewModel = viewModel
    override fun getLayoutId(): Int = R.layout.fragment_product_details
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.productId = productDetailsFragmentArgs.productId
        viewModel.categoryId = productDetailsFragmentArgs.categoryId
        viewModel.initProductDetails(productDetailsFragmentArgs.product)
        viewModel.productsMoreInCategory.value =
            productDetailsFragmentArgs.productsInSameCategory?.productInSameCategory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productCounter.getBinder()?.lifecycleOwner = this
        viewModel.initAPICall()
        productOriginalPriceTV.strikeThrough()
        handleClickListener()
        initMoreCategory()
        viewModel.discountList.observe(viewLifecycleOwner, Observer {
            it?.let { initDiscountList(it) }
        })
        viewModel.quantityModel.observe(viewLifecycleOwner, Observer {
            initMinimumCount(it)
        })
        viewModel.attributeListLiveData.observe(viewLifecycleOwner, Observer {
            recyclerViewAttributes.adapter = ProductAttributeAdapter(it)
        })
        viewModel.getRelatedProductList().observe(viewLifecycleOwner, Observer {
            initRelatedProductsRV(it)
        })
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                activity?.showSnackBarLessTime(getString(R.string.product_added_to_wishlist))
            }
        })
        viewModel.discountAppliedObsever.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                if (it) {
                    activity?.showSnackBarLessTime(getString(R.string.discount_applied))
                }
            }
        })
    }

    override fun onProductClick(product: Product) {
        findNavController().navigate(
            ProductDetailsFragmentDirections.actionToProductDetailsFragment(
                product,
                null,
                productId = product.productId,
                categoryId = product.category[0].id
            )
        )
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.seeAllRatingsTV, R.id.productRatingCV -> navigateToProductReviewList()
            R.id.viewClickEnablerProductDetails, R.id.productDetailsTV -> {
                handleExpandableViewClick(productDetailsInfoTV, productDetailsDropDownIV)
            }
            R.id.viewClickEnablerDiscountDetails, R.id.productDiscountTV -> {
                handleExpandableViewClick(recyclerViewDiscount, productDiscountDropDownIV)
            }
            R.id.addToCartTV -> {
                viewModel.product.value?.let {
                    if (viewModel.product.value?.options.isNullOrEmpty()) {
                        callAddToCart()
                    } else {
                        navigateToProductOptions(it)
                    }
                }
            }
            R.id.viewAllMoreCategoryTV -> {
                viewModel.getCategoryID()?.let {
                    findNavController().navigate(
                        ProductDetailsFragmentDirections.actionProductDetailsFragmentToProductListFragment(
                            it.name,
                            it.id
                        )
                    )
                }
            }

            R.id.viewAllrelatedProductsTV -> {
                viewModel.relatedProductRespone?.let {
                    findNavController().navigate(
                        ProductDetailsFragmentDirections
                            .actionProductDetailsFragmentToRelatedProductsFragment(
                                it
                            )
                    )
                }
            }
            R.id.textViewPostReview -> {
                viewModel.productId?.let { id ->
                    findNavController().navigate(
                        ProductDetailsFragmentDirections
                            .actionProductDetailsFragmentToGiveReviewFragment(id)
                    )
                }
            }
        }
    }

    private fun handleExpandableViewClick(contentTextView: View, dropDownIV: ImageView) {
        if (contentTextView.visibility == View.GONE) {
            contentTextView.visibility = View.VISIBLE
            dropDownIV.rotate(0f, 180f)
        } else {
            contentTextView.visibility = View.GONE
            dropDownIV.rotate(180f, 0f)
        }
    }

    private fun handleClickListener() {
        productRatingCV.setOnClickListener(this)
        seeAllRatingsTV.setOnClickListener(this)
        productDetailsTV.setOnClickListener(this)
        viewClickEnablerProductDetails.setOnClickListener(this)
        productDiscountTV.setOnClickListener(this)
        viewClickEnablerDiscountDetails.setOnClickListener(this)
        addToCartTV.setOnClickListener(this)
        viewAllMoreCategoryTV.setOnClickListener(this)
        viewAllrelatedProductsTV.setOnClickListener(this)
        textViewPostReview.setOnClickListener(this)
    }

    private fun initDiscountList(discountList: List<Discount>) {
        recyclerViewDiscount.adapter = DiscountAdapter(discountList)
    }

    private fun initMinimumCount(quantityModel: QuantityModel) {
        productCounter.getBinder()?.quantityModel = quantityModel
        quantityModel.countValue.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewModel.quantity = it
                viewModel.calculateTotalValue(it.toInt())
            }
        })
        quantityModel.errorMessage.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                viewModel.handleError(it)
                quantityModel.errorMessage.value = ""
            }
        })
    }

    private fun navigateToProductOptions(product: Product) {
        try {
            if (findNavController().currentDestination?.id == R.id.productDetailsFragment) {
                findNavController().navigate(
                    ProductDetailsFragmentDirections
                        .actionProductDetailsFragmentToProductOptionDialogFragment(
                            product.clone(),
                            viewModel.quantity
                        )
                )
            }
        } catch (e: Exception) {

        }
    }

    private fun callAddToCart() {
        viewModel.addToCart().observe(viewLifecycleOwner, Observer {
            if (activity is HomeNavigationActivity) {
                (activity as HomeNavigationActivity).showViewCartSnackBar(
                    getString(R.string.has_been_added_to_your_cart, it.data.product.name)
                )
            }
            cartCountViewModel.getCartCount()
            findNavController().popBackStack()
        })
    }

    private fun navigateToProductReviewList() {
        viewModel.getReviewData()?.let { review ->
            findNavController().navigate(
                ProductDetailsFragmentDirections
                    .actionProductDetailsFragmentToProductReviewFragment(review)
            )
        }
    }

    private fun initMoreCategory() {
        val moreInCategory = viewModel.productsMoreInCategory.value
        if (moreInCategory != null && !moreInCategory.isNullOrEmpty()) {
            viewModel.isMoreProductInCategoryVisible.value = true
            initRecyclerView(moreCategoryRV, moreInCategory)
        } else {
            viewModel.getProductListMoreInCategory().observe(viewLifecycleOwner, Observer {
                if (!it.isNullOrEmpty()) {
                    viewModel.isMoreProductInCategoryVisible.value = true
                    initRecyclerView(moreCategoryRV, it)
                }
            })
        }
    }

    private fun initRecyclerView(recyclerView: RecyclerView, productInSameCategory: List<Product>) {
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = ProductsAdapter(
            productInSameCategory as MutableList<Product>,
            gridLayoutManager,
            this
        ) { position, id -> }
    }

    private fun initRelatedProductsRV(it: List<Product>) {
        val relatedProductsGridLayoutManager = GridLayoutManager(context, 2)
        relatedProductsRV.layoutManager = relatedProductsGridLayoutManager
        relatedProductsRV.adapter = ProductsAdapter(
            it as MutableList<Product>,
            relatedProductsGridLayoutManager,
            this
        ) { position, id ->
        }
    }

    override fun onDestroy() {
        try {
            baseActivity.dismissProgress()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }
}
