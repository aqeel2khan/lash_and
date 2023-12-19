package com.cosmetics.lush.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.core.utils.Failure
import com.cosmetics.core.utils.Loading
import com.cosmetics.core.utils.ViewState
import com.cosmetics.lush.R

/**
 * A View Holder that can display a loading or have click action.
 * It is used to show the network state of paging.
 */
class NetworkStateItemViewHolder(
    view: View,
    val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(view) {
    private val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
    private val retry = view.findViewById<Button>(R.id.retry_button)
    private val errorMsg = view.findViewById<TextView>(R.id.error_msg)

    init {
        retry.setOnClickListener {
            retryCallback()
        }
    }

    fun bindTo(networkState: ViewState?) {
        progressBar.visibility = toVisibility(networkState is Loading)
        retry.visibility = toVisibility(networkState is Failure)
        errorMsg.visibility = toVisibility(networkState is Failure)
        if (networkState is Failure) {
            errorMsg.text = networkState.pair.first
        }
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view, retryCallback)
        }

        fun toVisibility(constraint: Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
