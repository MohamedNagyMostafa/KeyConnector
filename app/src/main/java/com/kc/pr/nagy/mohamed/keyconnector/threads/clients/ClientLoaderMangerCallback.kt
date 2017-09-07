package com.kc.pr.nagy.mohamed.keyconnector.threads.clients

import android.content.Context
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import com.kc.pr.nagy.mohamed.keyconnector.interfaces.MainThreadCallback

/**
 * Created by mohamednagy on 8/25/2017.
 */
class ClientLoaderMangerCallback(context: Context, mainThreadCallback: MainThreadCallback):
        LoaderManager.LoaderCallbacks<Unit>{

    private val mContext:Context = context
    private val mMainThreadCallback:MainThreadCallback = mainThreadCallback

    override fun onCreateLoader(p0: Int, p1: Bundle?) = ClientAsyncTask(mContext, mMainThreadCallback)
    override fun onLoaderReset(loader: Loader<Unit>?) {
        mMainThreadCallback.mainThreadUiRunClearUi()
    }

    override fun onLoadFinished(loader: Loader<Unit>?, data: Unit?) {
    }

}