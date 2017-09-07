package com.kc.pr.nagy.mohamed.keyconnector.threads.data_sender

import android.support.v4.app.LoaderManager


/**
 * Created by mohamednagy on 8/29/2017.
 */
interface SendingDataCallback : LoaderManager.LoaderCallbacks<Unit> {
    fun connect()
}