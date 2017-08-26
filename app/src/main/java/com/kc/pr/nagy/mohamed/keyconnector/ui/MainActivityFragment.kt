package com.kc.pr.nagy.mohamed.keyconnector.ui

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kc.pr.nagy.mohamed.keyconnector.R
import com.kc.pr.nagy.mohamed.keyconnector.network.NetworkAccessPoint

/**
 * Created by mohamednagy on 8/25/2017.
 */
class MainActivityFragment:Fragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val wifiManager = activity.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val networkAccessPoint = NetworkAccessPoint.getInstance(wifiManager)

        networkAccessPoint.generateConfigurationAccessPoint()

        return inflater!!.inflate(R.layout.fragment_main, container, false);
    }

}