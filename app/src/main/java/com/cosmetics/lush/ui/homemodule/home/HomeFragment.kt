package com.cosmetics.lush.ui.homemodule.home


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cosmetics.core.base.BaseBindingFragment
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.model.home.request.Category
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.FragmentHomeBinding
import com.cosmetics.lush.ui.CartCountViewModel
import com.cosmetics.lush.ui.HomeNavigationActivity
import com.cosmetics.lush.ui.MainViewModel
import com.cosmetics.lush.ui.homemodule.sub_category.OnSubCategorySelectedListener
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(), OnSubCategorySelectedListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var rootView: View? = null
    private var adapter: HomeCategoryAdapter? = null
    private val cartCountViewModel: CartCountViewModel by sharedViewModel()
    private val viewModel: MainViewModel by sharedViewModel()

    override fun getViewModel(): BaseViewModel = viewModel
    private var homeActivity: HomeNavigationActivity? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initCategoryRV()
    }

  /*  override fun onResume() {
        super.onResume()
        cartCountViewModel.getCartCount()
    }*/

    private fun initCategoryRV() {
        viewModel.refreshAPIs()
        viewModel.getCategories()
        viewModel.categoryData.observe(viewLifecycleOwner, Observer {
            if (adapter == null) {
                adapter = HomeCategoryAdapter(it.data, this) { categoryItem ->
                    findNavController().navigate(
                        HomeFragmentDirections
                            .actionHomeFragmentToSubCategoryFragment(categoryItem)
                    )
                }
                adapter?.setHasStableIds(true)
                homeCategoryRV.adapter = adapter
                homeActivity = activity as HomeNavigationActivity
                homeActivity?.setDrawerItems(it.data)
            } else {
                adapter?.updateItems(it.data)
            }
        })
    }

    override fun onSubCategorySelected(category: Category) {
        findNavController().navigate(
            HomeFragmentDirections.actionTabHomeToProductListFragment(
                category.formattedName,
                category.categoryId
            )
        )
    }

    override fun getLayoutId(): Int = R.layout.fragment_home


}
