package com.kc.pr.nagy.mohamed.keyconnector.ui

import android.content.Context
import android.content.Intent
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
import com.kc.pr.nagy.mohamed.keyconnector.process.Utility

/**
 * Created by mohamednagy on 8/25/2017.
 */
class MainActivityFragment:Fragment(), MainThreadCallback{

    private var networkAccessPoint:NetworkAccessPoint? = null
    private val CONFIGURATION_WIFI_HOTSPOT_SAVE_KEY = "configuration-key"
    private val CONFIGURATION_WIFI_HOTSPOT_IS_CREATED = 1
    private var clientsIpAddressListAdapter: ArrayAdapter<String>? = null
    private var MAIN_ACTIVITY_VIEW_HOLDER: PrViewHolder.MainActivityViewHolder? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater!!.inflate(R.layout.fragment_main, container, false);
        MAIN_ACTIVITY_VIEW_HOLDER = PrViewHolder().MainActivityViewHolder(view)
        // Get wifi service.
        val wifiManager = activity.getSystemService(Context.WIFI_SERVICE) as WifiManager
        // List holds the collection of clients who connected with access point.
        val clientsList = ArrayList<String>()
        // adapter to display clients ip in list view.
        clientsIpAddressListAdapter = ArrayAdapter<String>(context, R.layout.client_ip_address_list_view, clientsList)
        // NetworkAccessPoint class hold all methods of access point creation.
        networkAccessPoint = NetworkAccessPoint.getInstance(wifiManager)
        MAIN_ACTIVITY_VIEW_HOLDER!!.CLIENT_ADDRESS_LIST_VIEW.adapter = clientsIpAddressListAdapter!!
        // check is there is access point is created before or not.
        // used for rotation and when app goes to background.
        if(isNotAccessPointCreated(savedInstanceState)) {
            //create and open access point.
            networkAccessPoint!!.generateConfigurationAccessPoint()
        }else{
            // open hotspot. if it was closed.
            networkAccessPoint!!.setWifiAPEnabled(networkAccessPoint!!.wifiConfiguration, true)
        }

        //get connection devices
        networkAccessPoint!!.startClientsSearch(context, activity.supportLoaderManager, this)

        MAIN_ACTIVITY_VIEW_HOLDER!!.SWAP_REFRESH_LAYOUT.setOnRefreshListener {
            networkAccessPoint!!.startClientsSearch(context, activity.supportLoaderManager, this)
        }

        MAIN_ACTIVITY_VIEW_HOLDER!!.CLIENT_ADDRESS_LIST_VIEW.onItemClickListener =
                AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                    val computerMouseScreen = Intent(context, ComputerMouseScreenActivity::class.java)
                    computerMouseScreen.putExtra(Utility.Extras.IP_ADDRESS_EXTRA.value() , (p1 as TextView).text.toString())
                    Log.e("start sending", "done");
                    activity.startActivity(computerMouseScreen)
                }

        return view
    }

    /**
     * Save the current access point when app goes to background or rotation.
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putInt(CONFIGURATION_WIFI_HOTSPOT_SAVE_KEY, CONFIGURATION_WIFI_HOTSPOT_IS_CREATED)
        super.onSaveInstanceState(outState)
    }

    /**
     * Check if there is an access point is created before or not. using savedInstanceState.
     * and check is there data or not.
     */
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
        activity.runOnUiThread {
            MAIN_ACTIVITY_VIEW_HOLDER!!.SWAP_REFRESH_LAYOUT.isRefreshing = false
        }
    }

    override fun mainThreadUiRunStartRefresh() {
        activity.runOnUiThread {
            MAIN_ACTIVITY_VIEW_HOLDER!!.SWAP_REFRESH_LAYOUT.isRefreshing = true
        }
    }



}