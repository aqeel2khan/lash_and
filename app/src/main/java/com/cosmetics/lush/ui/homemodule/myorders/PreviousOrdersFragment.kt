package com.cosmetics.lush.ui.homemodule.myorders


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cosmetics.lush.R
import kotlinx.android.synthetic.main.fragment_previous_orders.*

class PreviousOrdersFragment : Fragment() {

    companion object {

        fun newInstance() = PreviousOrdersFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous_orders, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initPreviousOrdersRV()
    }

    private fun initPreviousOrdersRV() {
        previousOrdersRV.layoutManager = LinearLayoutManager(context)
        previousOrdersRV.adapter = PreviousOrdersAdapter()
    }
}
