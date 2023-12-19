package com.cosmetics.data.networking.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.cosmetics.data.R
import com.cosmetics.domain.di.getKoinInstance
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

interface ConnectivityInterceptor : Interceptor
class ConnectivityInterceptorImpl(private val context: Context) :
    ConnectivityInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isConnectionOn()) {
            throw NoNetworkException()
        } else if (!isInternetAvailable()) {
            throw NoNetworkException()
        } else {
            chain.proceed(chain.request())
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnectionOn(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return postAndroidMInternetCheck(connectivityManager)
    }

    /*   private fun preAndroidMInternetCheck(connectivityManager: ConnectivityManager): Boolean {
           val activeNetwork = connectivityManager.activeNetworkInfo
           if (activeNetwork != null) {
               return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                       activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
           }
           return false
       }*/

    private fun isInternetAvailable(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val socketAddress = InetSocketAddress("8.8.8.8", 53)

            sock.connect(socketAddress, timeoutMs)
            sock.close()

            true
        } catch (e: IOException) {
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun postAndroidMInternetCheck(connectivityManager: ConnectivityManager): Boolean {
        val network = connectivityManager.activeNetwork
        val connection =
            connectivityManager.getNetworkCapabilities(network)

        return connection != null && (
                connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                )
    }
}


class NoNetworkException : IOException() {
    private val context: Context = getKoinInstance()
    override val message: String
        get() = context.getString(R.string.no_network)
}