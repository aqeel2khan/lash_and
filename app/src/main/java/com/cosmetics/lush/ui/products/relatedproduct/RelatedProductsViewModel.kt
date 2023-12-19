package com.cosmetics.lush.ui.products.relatedproduct

import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.model.product.Product
import com.cosmetics.lush.BR
import com.cosmetics.lush.ui.products.SimilarProductCategoryList

class RelatedProductsViewModel : BaseViewModel() {
    override fun getBindingId() = BR.viewModel

    var productList: List<Product>? = null
    private var similarProductCategoryList = SimilarProductCategoryList()
/*
    fun getProductInSameCategory(item: Product): SimilarProductCategoryList {
        productList?.let { initProductInSameCategory(it as MutableList<Product>) }
        with(similarProductCategoryList) {
            if (!productInSameCategory.isNullOrEmpty()) {
                productInSameCategory =
                    productInSameCategory.filter { item.id != it.id }.toMutableList()
                if (productInSameCategory.size > 2) {
                    productInSameCategory = productInSameCategory.subList(0, 2)
                }
            }
        }
        return similarProductCategoryList
    }

    private fun initProductInSameCategory(products: MutableList<Product>) {
        with(similarProductCategoryList) {
            if (!products.isNullOrEmpty()) {
                if (products.size > 3) {
                    productInSameCategory.addAll(products.subList(0, 4))
                } else {
                    productInSameCategory.addAll(products)
                }
            }
        }
    }*/

}
