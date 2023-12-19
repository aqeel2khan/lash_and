package com.cosmetics.lush.ui.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.LKEYS
import com.cosmetics.domain.model.product.Product
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentProductsListBinding
import com.cosmetics.lush.ui.HomeNavigationActivity
import com.cosmetics.lush.utils.navigate
import kotlinx.android.synthetic.main.fragment_products_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


fun Fragment.setHomeTittle(tittle: String) {
    if (activity is HomeNavigationActivity)
        (activity as HomeNavigationActivity).setHomeTitle(tittle)
}
class ProductsListFragment : BaseBindingFragment<FragmentProductsListBinding>(),
    ProductsPageAdapter.OnProductsClickListener, View.OnClickListener {
    private var categoryId: Int = 0
    private val viewModel by viewModel<ProductListViewModel>()
    private var isListDisplaySelected = true
    private var gridLayoutManager: GridLayoutManager? = null
    private var productAdapter: ProductsPageAdapter? = null
    private val productsListFragmentArgs by navArgs<ProductsListFragmentArgs>()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getLayoutId(): Int = R.layout.fragment_products_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgumentInfo()
        viewModel.initPagingList()
    }

    override fun onResume() {
        super.onResume()
        setHomeTittle(productsListFragmentArgs.categoryName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerSortByBottomLine.setOnClickListener(this)
        imageViewSpinnerDropDown.setOnClickListener(this)
        listDisplayIV.setOnClickListener(this)
        gridDisplayIV.setOnClickListener(this)
        initProductsList()
        initObserver()
    }

    override fun onProductClick(product: Product) {
        //     var similarProductList = viewModel.getProductInSameCategory(product)
        try {
            navigate(
                ProductsListFragmentDirections.actionProductListFragmentToProductDetailsFragment(
                    product,
                    null,
                    productId = product.productId,
                    categoryId = categoryId
                )
            )
        } catch (e: Exception) {
        }
    }

    private fun initObserver() {
        viewModel.postLiveData?.observe(viewLifecycleOwner, Observer {
            productAdapter?.submitList(it)
        })
        viewModel.getNetworkState()?.observe(viewLifecycleOwner, Observer {
            productAdapter?.setNetworkState(it)
        })
    }

    private fun initArgumentInfo() {
        categoryId = if (arguments?.get(LKEYS.CATEGORY_ID) != null) {
            arguments?.get(LKEYS.CATEGORY_ID) as Int
        } else {
            productsListFragmentArgs.categoryId
        }
        viewModel.categoryId = categoryId
    }


    private fun initProductsList() {
        if (productAdapter == null) {
            listDisplayIV.isSelected = true
            gridLayoutManager = GridLayoutManager(context, 1)
            productAdapter = ProductsPageAdapter(gridLayoutManager!!, this) {
                viewModel.retry()
            }
            productsRV.adapter = productAdapter
            productsRV.layoutManager = gridLayoutManager
        }
    }

    /* private fun toggleFilterOptions() {
         if (filterCL.visibility == View.INVISIBLE) {
             filterCL.openWithCircularReveal()
             filterCL.visibility = View.VISIBLE
             opacityBg.animateAlpha(1f)
             filterIV.rotateToOther(otherDrawable = R.drawable.ic_close_white, angle = 180f)
         } else {
             filterCL.closeWithCircularReveal()
             opacityBg.animateAlpha(0f)
             filterIV.rotateToOther(R.drawable.ic_filter, 0f)
         }
     }*/

    override fun onClick(view: View) {
        when (view.id) {
            R.id.spinnerSortByBottomLine, R.id.imageViewSpinnerDropDown -> {
                spinnerSortBy.performClick()
            }
            R.id.listDisplayIV -> {
                isListDisplaySelected = true
                view.isSelected = true
                gridDisplayIV.isSelected = false
                gridLayoutManager?.spanCount = 1
                productAdapter?.let { productAdapter ->
                    productAdapter.notifyItemRangeChanged(0, productAdapter.itemCount)
                }
            }
            R.id.gridDisplayIV -> {
                isListDisplaySelected = false
                view.isSelected = true
                listDisplayIV.isSelected = false
                gridLayoutManager?.spanCount = 2
                productAdapter?.let { productAdapter ->
                    productAdapter.notifyItemRangeChanged(0, productAdapter.itemCount)
                }
            }
        }
    }

}
