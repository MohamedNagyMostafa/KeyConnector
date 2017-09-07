package com.kc.pr.nagy.mohamed.keyconnector.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.Loader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kc.pr.nagy.mohamed.keyconnector.R
import com.kc.pr.nagy.mohamed.keyconnector.process.Utility
import com.kc.pr.nagy.mohamed.keyconnector.process.moving.OnTouchListenerMovingAction
import com.kc.pr.nagy.mohamed.keyconnector.threads.data_sender.SendingDataAsyncTask
import com.kc.pr.nagy.mohamed.keyconnector.threads.data_sender.SendingDataCallback

/**
 * A placeholder fragment containing a simple view.
 */
class ComputerMouseScreenActivityFragment : Fragment(), SendingDataCallback {

    var ipAddress:String? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater!!.inflate(R.layout.fragment_computer_mouse_screen, container, false)
        val computerMouseScreenViewHolder: PrViewHolder.ComputerMouserScreenViewHolder =
                PrViewHolder().ComputerMouserScreenViewHolder(view)
        ipAddress = activity.intent.extras.getString(Utility.Extras.IP_ADDRESS_EXTRA.value())
        Log.e("ip ", ipAddress)

        computerMouseScreenViewHolder.MOUSE_SCREEN_LAYOUT.setOnTouchListener(OnTouchListenerMovingAction(ipAddress!!, context, this))


        return view
    }

    override fun connect() {
        if(loaderManager.getLoader<Unit>(Utility.Loaders.SENDING_DATA_LOADER.value()) != null){
            Log.e("used pre","done")
            loaderManager.restartLoader(Utility.Loaders.SENDING_DATA_LOADER.value(), null, this);
        }else{
            Log.e("create new","done")
            loaderManager.initLoader(Utility.Loaders.SENDING_DATA_LOADER.value(), null, this);

        }
    }
    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Unit>? {
        Log.e("start create new","done")
        return SendingDataAsyncTask.getInstance(8888, ipAddress = ipAddress!!, context = context, sendingDataCallback = this)
    }


    override fun onLoadFinished(loader: android.support.v4.content.Loader<Unit>?, data: Unit?) {
        Log.e("task finfished", "done")
    }


    override fun onLoaderReset(loader: android.support.v4.content.Loader<Unit>?) {
        Log.e("task finfished", "rester")

    }
}
