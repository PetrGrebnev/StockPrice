package com.example.stockprice.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class MyUtils {
    companion object {
        fun isInternetAvailable(context: Context): Boolean {
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_INTERNET
                    ) ?: false
                } else {
                    (@Suppress(  "Deprecation")
                    return this.activeNetworkInfo?.isConnected ?: false)
                }
            }
        }
    }
}