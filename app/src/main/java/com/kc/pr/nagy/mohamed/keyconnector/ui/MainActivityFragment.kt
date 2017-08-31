package com.kc.pr.nagy.mohamed.keyconnector.ui

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.Loader
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
import com.kc.pr.nagy.mohamed.keyconnector.threads.SendingDataAsyncTask
import com.kc.pr.nagy.mohamed.keyconnector.threads.SendingDataCallback

/**
 * Created by mohamednagy on 8/25/2017.
 */
class MainActivityFragment:Fragment(), MainThreadCallback, SendingDataCallback{
    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Unit>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Unit>?, data: Unit?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun connect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
        MAIN_ACTIVITY_VIEW_HOLDER!!.CLIENT_ADDRESS_LIST_VIEW.adapter = clientsIpAddressListAdapter!!

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
                    val computerMouseScreen = Intent(context, ComputerMouseScreenActivity::class.java)
                    computerMouseScreen.putExtra(Utility.Extras.IP_ADDRESS_EXTRA.value() , (p1 as TextView).text.toString())
                    Log.e("start sending", "done");
                    activity.startActivity(computerMouseScreen)
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