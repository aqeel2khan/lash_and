package com.cosmetics.lush.ui.products.relatedproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cosmetics.core.base.BaseFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.model.product.Product
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.products.ProductsPageAdapter
import kotlinx.android.synthetic.main.fragment_related_products.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RelatedProductsFragment : BaseFragment(),
    ProductsPageAdapter.OnProductsClickListener {
    private lateinit var adapter: RelatedProductsAdapter
    private val viewModel: RelatedProductsViewModel by viewModel()
    private val relatedProductsFragmentArgs: RelatedProductsFragmentArgs by navArgs()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_related_products, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initProductListRV()
    }

    private fun initProductListRV() {
        var productResponse = relatedProductsFragmentArgs.productList
        viewModel.productList = productResponse.products
        productResponse.products.let { it?.let { it1 -> handleWishListResponse(it1) } }
    }

    private fun handleWishListResponse(it: MutableList<Product>) {
        if (!it.isNullOrEmpty()) {
            adapter =
                RelatedProductsAdapter(it, this)
            productsListRV.adapter = adapter
        }
    }

    override fun onProductClick(product: Product) {
        try {
            val directions =
                RelatedProductsFragmentDirections.actionRelatedProductsFragmentToProductDetailsFragment(
                    product,
                    null,
                    productId = product.productId,
                    categoryId = product.category[0].id
                )
            findNavController().navigate(directions)
        } catch (e: Exception) {
        }
    }

}
