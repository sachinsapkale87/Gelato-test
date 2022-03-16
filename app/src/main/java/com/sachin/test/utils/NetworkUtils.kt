package com.sachin.test.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

class NetworkUtils(context: Context) {

    private val connectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }


    fun isNetworkAvailable(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isNetworkAvailableApi23()
        } else {
            isNetworkAvailableCompat()
        }
    }

    private fun isNetworkAvailableCompat(): Boolean {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo ?: return false

        return activeNetworkInfo.isConnected &&
                activeNetworkInfo.type == ConnectivityManager.TYPE_VPN ||
                activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI ||
                activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkAvailableApi23(): Boolean {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
        ) ?: return false

        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}