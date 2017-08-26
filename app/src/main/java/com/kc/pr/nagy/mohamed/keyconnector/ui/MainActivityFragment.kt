package com.kc.pr.nagy.mohamed.keyconnector.ui

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.kc.pr.nagy.mohamed.keyconnector.R
import com.kc.pr.nagy.mohamed.keyconnector.network.NetworkAccessPoint

/**
 * Created by mohamednagy on 8/25/2017.
 */
class MainActivityFragment:Fragment(){

    private var networkAccessPoint:NetworkAccessPoint? = null
    private val CONFIGURATION_WIFI_HOTSPOT_SAVE_KEY = "configuration-key"
    private val CONFIGURATION_WIFI_HOTSPOT_IS_CREATED = 1

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater!!.inflate(R.layout.fragment_main, container, false);

        val wifiManager = activity.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val clientsList = ArrayList<String>()
        val adapterClient = ArrayAdapter<String>(context, R.layout.client_ip_address_list_view, clientsList)
        val clientAddressIpListView = view.findViewById(R.id.clientAddressIpListView) as ListView

        networkAccessPoint = NetworkAccessPoint.getInstance(wifiManager)
        clientAddressIpListView.adapter = adapterClient
        if(isAccessPointCreated(savedInstanceState)) {
            //create and open access point.
            networkAccessPoint!!.generateConfigurationAccessPoint()
        }else{
            networkAccessPoint!!.setWifiAPEnabled(networkAccessPoint!!.wifiConfiguration, true)
        }
        //get connection devices
            networkAccessPoint!!.startClientsSearch(context, clientsList.toMutableList(), activity.supportLoaderManager, adapterClient)

        return view
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putInt(CONFIGURATION_WIFI_HOTSPOT_SAVE_KEY, CONFIGURATION_WIFI_HOTSPOT_IS_CREATED)
        super.onSaveInstanceState(outState)
    }

    private fun isAccessPointCreated(savedInstanceState: Bundle?):Boolean{
        var result = true
        if(savedInstanceState != null){
            val configurationCheck = savedInstanceState.getInt(CONFIGURATION_WIFI_HOTSPOT_SAVE_KEY)
            if(configurationCheck == CONFIGURATION_WIFI_HOTSPOT_IS_CREATED)
                result = false
        }

        return result
    }
}