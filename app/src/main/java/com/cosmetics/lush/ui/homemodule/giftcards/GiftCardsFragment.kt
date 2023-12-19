package com.cosmetics.lush.ui.homemodule.giftcards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cosmetics.lush.R

class GiftCardsFragment : Fragment() {

    companion object {
        fun newInstance() = GiftCardsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gift_cards, container, false)
    }

}
