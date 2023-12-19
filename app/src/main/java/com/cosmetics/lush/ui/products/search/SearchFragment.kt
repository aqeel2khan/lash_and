package com.cosmetics.lush.ui.products.search

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.hideKeyboard
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.model.product.Product
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentSearchBinding
import com.cosmetics.lush.ui.products.ProductsAdapter
import kotlinx.android.synthetic.main.fragment_login.backIV
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseBindingFragment<FragmentSearchBinding>(),
    ProductsAdapter.OnProductsClickListener {
    private var productAdapter: ProductsAdapter? = null
    private val viewModel: SearchViewModel by viewModel()
    override fun getLayoutId(): Int = R.layout.fragment_search
    override fun getViewModel(): BaseViewModel = viewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        backIV.setOnClickListener {
            findNavController().popBackStack()
        }
        initRecentSearchRV()
        initProductsList()
    }

    private fun initRecentSearchRV() {
        if (PreferenceDelegate.recentSearchList.isNotEmpty()) {
            var list = PreferenceDelegate.recentSearchList.split(",")
            setRecentListAdapter(list)
        }
        viewModel.recentSearchList.observe(viewLifecycleOwner, Observer {
            setRecentListAdapter(it)
        })
    }

    private fun setRecentListAdapter(it: List<String>) {
        recentSearchesRV.adapter = RecentSearchAdapter(it) {
            viewModel.searchString.value = it
        }
    }

    override fun onProductClick(product: Product) {
        try {
            //Somehow category id was null.
            NavHostFragment.findNavController(this).navigate(
                SearchFragmentDirections.actionSearchFragmentToProductDetailsFragment(
                    product, null,
                    productId = product.productId,
                    categoryId = product.category[0].id
                )
            )
        } catch (e: Exception) {
        }
    }

    private fun initProductsList() {
        viewModel.productList.observe(viewLifecycleOwner, Observer {
            dismissSnackBar()
            if (productAdapter == null) {
                val gridLayoutManager = GridLayoutManager(context, 1)
                productAdapter = it.products?.let { it1 ->
                    ProductsAdapter(
                        it1, gridLayoutManager, this
                    ) { position, id ->
                        viewModel.addToWishList(id)
                            .observe(viewLifecycleOwner, Observer { isSuccess ->
                                if (isSuccess) {
                                    productAdapter?.updateFavourites(position)
                                }
                            })
                    }
                }
                productsRV.layoutManager = gridLayoutManager
                productsRV.adapter = productAdapter
            } else {
                it.products?.let { it1 -> productAdapter?.updateList(it1) }
            }
        })
        viewModel.onClearProductList.observe(viewLifecycleOwner, Observer {
            productAdapter?.notifyDataSetChanged()
        })
    }

    override fun onStop() {
        super.onStop()
        view?.hideKeyboard()
    }
}
