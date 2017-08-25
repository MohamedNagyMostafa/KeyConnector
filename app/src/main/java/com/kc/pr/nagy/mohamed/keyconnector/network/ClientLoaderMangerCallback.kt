package com.kc.pr.nagy.mohamed.keyconnector.network

import android.app.LoaderManager
import android.content.Context
import android.content.Loader
import android.os.Bundle

/**
 * Created by mohamednagy on 8/25/2017.
 */
class ClientLoaderMangerCallback(context: Context, clients: MutableList<String>):LoaderManager.LoaderCallbacks<MutableList<String>>{

    val mContext:Context = context
    var mClients: MutableList<String>? = clients

    override fun onLoaderReset(p0: Loader<MutableList<String>>?) {
        mClients = null
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<MutableList<String>> = ClientAsyncTask(mContext, clients = mClients)

    override fun onLoadFinished(p0: Loader<MutableList<String>>?, p1: MutableList<String>?) {

    }
}