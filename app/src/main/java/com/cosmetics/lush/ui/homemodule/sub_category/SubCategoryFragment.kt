package com.cosmetics.lush.ui.homemodule.sub_category

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.cosmetics.core.commonviews.GridSpacingItemDecoration
import com.cosmetics.core.utils.LKEYS
import com.cosmetics.domain.model.home.request.Category
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.HomeNavigationActivity
import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategoryFragment : Fragment(R.layout.activity_sub_category),
    OnSubCategorySelectedListener {

    private var categoryItem: Category? = null
    private val navArgs = navArgs<SubCategoryFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryItem = if (arguments?.get(LKEYS.CATEGORY_ITEM) != null) {
            arguments?.get(LKEYS.CATEGORY_ITEM) as Category
        } else {
            navArgs.value.categoryItem
        }

    }

    override fun onStart() {
        super.onStart()
        categoryItem?.let {
            if (activity is HomeNavigationActivity) {
                (activity as HomeNavigationActivity).setHomeTitle(it.formattedName)
            }
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSubCategoryRV()
    }

    override fun onSubCategorySelected(category: Category) {
        findNavController().navigate(
            SubCategoryFragmentDirections.actionSubcategoryToProductListFragment(
                category.formattedName,
                category.categoryId
            )
        )
    }

    private fun initSubCategoryRV() {
        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 1)
                    return 1
                else if (position % 3 == 2)
                    return 2
                return 1
            }
        }
        subCategoryRV.layoutManager = gridLayoutManager
        subCategoryRV.addItemDecoration(
            GridSpacingItemDecoration(
                resources.getDimension(R.dimen.grid_spacing).toInt()
            )
        )
        subCategoryRV.adapter = categoryItem?.categories?.let {
            SubCategoryAdapter(
                it,
                this
            )
        }
    }

}
