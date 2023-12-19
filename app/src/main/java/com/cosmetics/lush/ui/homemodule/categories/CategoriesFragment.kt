package com.cosmetics.lush.ui.homemodule.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cosmetics.core.base.BaseFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.model.home.request.Category
import com.cosmetics.lush.R
import kotlinx.android.synthetic.main.fragment_categories.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : BaseFragment(), CategoriesAdapter.OnCategoryClickListener {

    private var rootView: View? = null
    private val viewModel: CategoriesViewModel by viewModel()
    private var adapter: CategoriesAdapter? = null

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_categories, container, false)
        }
        return rootView?.rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initCategoriesRV()
    }

    override fun onCategoryClickListener(categoryItem: Category) {
        if (categoryItem.categories.isNullOrEmpty()) {
            showSnackBarError(String.format(getString(R.string.subcategories_to_choose_from), 0))
        } else {
            findNavController().navigate(
                CategoriesFragmentDirections
                    .actionHomeFragmentToSubCategoryFragment(categoryItem)
            )
        }
    }

    private fun initCategoriesRV() {
        if (adapter == null) {
            viewModel.getCategories().observe(viewLifecycleOwner, Observer {
                adapter = CategoriesAdapter(it.data, this)
                categoriesRV.adapter = adapter
            })
        }
    }

}

