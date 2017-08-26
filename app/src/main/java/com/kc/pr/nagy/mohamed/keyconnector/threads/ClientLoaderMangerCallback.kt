package com.kc.pr.nagy.mohamed.keyconnector.threads

import android.content.Context
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.widget.ArrayAdapter
import android.widget.Toast

/**
 * Created by mohamednagy on 8/25/2017.
 */
class ClientLoaderMangerCallback(context: Context, clients: MutableList<String>, clientIpAddressAdapter:ArrayAdapter<String>):
        LoaderManager.LoaderCallbacks<MutableList<String>>{

    private val mContext:Context = context
    private var mClients: MutableList<String>? = clients
    private val mClientIpAddressAdapter:ArrayAdapter<String> = clientIpAddressAdapter

    override fun onLoaderReset(p0: Loader<MutableList<String>>?) {
        mClients = null
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<MutableList<String>> = ClientAsyncTask(mContext, clients = mClients, clientIpAddressAdapter = mClientIpAddressAdapter)

    override fun onLoadFinished(p0: Loader<MutableList<String>>?, p1: MutableList<String>?) {
        Toast.makeText(mContext, "Thread Load Finished", Toast.LENGTH_LONG).show()
        mClientIpAddressAdapter.notifyDataSetChanged()
        for(item in p1!!){
            mClientIpAddressAdapter.add(item)
        }
    }
}