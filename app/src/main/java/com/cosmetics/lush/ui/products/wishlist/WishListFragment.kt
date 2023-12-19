package com.cosmetics.lush.ui.products.wishlist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.model.product.wishlist.ProductWishList
import com.cosmetics.domain.model.product.wishlist.ProductWishListResponse
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ActivityWishListBinding
import kotlinx.android.synthetic.main.activity_wish_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WishListFragment : BaseBindingFragment<ActivityWishListBinding>() {
    private lateinit var adapter: WishListAdapter
    private val viewModel: WishListViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getLayoutId(): Int = R.layout.activity_wish_list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initWishListRV()
    }

    private fun initWishListRV() {
        viewModel.getProductWishList().observe(viewLifecycleOwner, Observer {
            handleWishListResponse(it)
        })
    }

    private fun handleWishListResponse(it: ProductWishListResponse) {
        if (!it.productWishList.isNullOrEmpty()) {
            adapter =
                WishListAdapter(it.productWishList as MutableList<ProductWishList>,
                    { id, position ->
                        viewModel.removeFromWishList(id)
                            .observe(viewLifecycleOwner, Observer { isSuccess ->
                                if (isSuccess) {
                                    adapter.updateWishList(viewModel, position)
                                }
                            })
                    }) { productId, categoryId ->
                    findNavController().navigate(
                        WishListFragmentDirections.actionWishListFragmentToProductDetailsFragment(
                            null,
                            null,
                            productId,
                            categoryId
                        )
                    )
                }
            wishListRV.adapter = adapter
        } else {
            textViewWishList.visibility = View.VISIBLE
        }
    }

}
