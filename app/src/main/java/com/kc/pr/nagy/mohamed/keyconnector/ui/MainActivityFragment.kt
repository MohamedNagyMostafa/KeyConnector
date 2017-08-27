package com.kc.pr.nagy.mohamed.keyconnector.ui

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kc.pr.nagy.mohamed.keyconnector.R
import com.kc.pr.nagy.mohamed.keyconnector.interfaces.MainThreadCallback
import com.kc.pr.nagy.mohamed.keyconnector.network.NetworkAccessPoint
import com.kc.pr.nagy.mohamed.keyconnector.threads.SendingDataAsyncTask

/**
 * Created by mohamednagy on 8/25/2017.
 */
class MainActivityFragment:Fragment(), MainThreadCallback{

    private var networkAccessPoint:NetworkAccessPoint? = null
    private val CONFIGURATION_WIFI_HOTSPOT_SAVE_KEY = "configuration-key"
    private val CONFIGURATION_WIFI_HOTSPOT_IS_CREATED = 1
    private var clientsIpAddressListAdapter: ArrayAdapter<String>? = null
    private var MAIN_ACTIVITY_VIEW_HOLDER: PrViewHolder.MainActivityViewHolder? = null
    private var sendingDataAsyncTask:SendingDataAsyncTask? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater!!.inflate(R.layout.fragment_main, container, false);
        MAIN_ACTIVITY_VIEW_HOLDER = PrViewHolder().MainActivityViewHolder(view)

        val wifiManager = activity.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val clientsList = ArrayList<String>()
        clientsIpAddressListAdapter = ArrayAdapter<String>(context, R.layout.client_ip_address_list_view, clientsList)

        networkAccessPoint = NetworkAccessPoint.getInstance(wifiManager)
        MAIN_ACTIVITY_VIEW_HOLDER!!.CLIENT_ADDRESS_LIST_VIEW.adapter = clientsIpAddressListAdapter

        if(isNotAccessPointCreated(savedInstanceState)) {
            //create and open access point.
            networkAccessPoint!!.generateConfigurationAccessPoint()
        }else{
            networkAccessPoint!!.setWifiAPEnabled(networkAccessPoint!!.wifiConfiguration, true)
        }

        //get connection devices
        networkAccessPoint!!.startClientsSearch(context, activity.supportLoaderManager, this)

        MAIN_ACTIVITY_VIEW_HOLDER!!.SWAP_REFRESH_LAYOUT.setOnRefreshListener {
            networkAccessPoint!!.startClientsSearch(context, activity.supportLoaderManager, this)
        }

        MAIN_ACTIVITY_VIEW_HOLDER!!.CLIENT_ADDRESS_LIST_VIEW.onItemClickListener =
                AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                    sendingDataAsyncTask = SendingDataAsyncTask.getInstance(8888, (p1 as TextView).text.toString())
                    sendingDataAsyncTask!!.connect("hello")
                }
        return view
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putInt(CONFIGURATION_WIFI_HOTSPOT_SAVE_KEY, CONFIGURATION_WIFI_HOTSPOT_IS_CREATED)
        super.onSaveInstanceState(outState)
    }

    private fun isNotAccessPointCreated(savedInstanceState: Bundle?):Boolean{
        var result = true
        if(savedInstanceState != null){
            val configurationCheck = savedInstanceState.getInt(CONFIGURATION_WIFI_HOTSPOT_SAVE_KEY)
            if(configurationCheck == CONFIGURATION_WIFI_HOTSPOT_IS_CREATED) {
                result = false
                Log.e("Ap created","done")
            }
        }


        return result
    }

    override fun mainThreadUiRunAddIp(clientIpAddress: String) {
        activity.runOnUiThread {
            clientsIpAddressListAdapter!!.add(clientIpAddress)
        }
    }

    override fun mainThreadUiRunClearUi() {
        activity.runOnUiThread {
            clientsIpAddressListAdapter!!.clear()
        }
    }

    override fun mainThreadUiRunStopRefresh() {
        MAIN_ACTIVITY_VIEW_HOLDER!!.SWAP_REFRESH_LAYOUT.isRefreshing = false
    }

    override fun mainThreadUiRunStartRefresh() {
        MAIN_ACTIVITY_VIEW_HOLDER!!.SWAP_REFRESH_LAYOUT.isRefreshing = true
    }


    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to [Activity.onPause] of the containing
     * Activity's lifecycle.
     */
    override fun onPause() {
        if(sendingDataAsyncTask != null)
            sendingDataAsyncTask!!.disConnect()
        super.onPause()
    }
}