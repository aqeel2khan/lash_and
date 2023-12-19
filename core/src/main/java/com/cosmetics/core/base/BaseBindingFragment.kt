package com.cosmetics.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.cosmetics.core.utils.FragmentBinding

abstract class BaseBindingFragment<DataBinding : ViewDataBinding> : BaseFragment() {

    abstract fun getLayoutId(): Int

    private val dataBindings
            by FragmentBinding<DataBinding>(getLayoutId())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return dataBindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBindings.apply {
            setVariable(getViewModel().getBindingId(), getViewModel())
            executePendingBindings()
            lifecycleOwner = this@BaseBindingFragment
        }
    }

    fun getDataBinding() = dataBindings
}