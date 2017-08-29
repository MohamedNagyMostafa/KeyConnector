package com.kc.pr.nagy.mohamed.keyconnector.threads

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import android.util.Log
import com.kc.pr.nagy.mohamed.keyconnector.process.Utility
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Created by mohamednagy on 8/25/2017.
 */
class SendingDataAsyncTask private constructor(port:Int, ipAddress:String, context: Context,
                                               private val sendingDataCallback: SendingDataCallback) :
        AsyncTaskLoader<Unit>(context) {

    private var SERVER_PORT:Int = port
    private var SERVER_IP_ADDRESS:String = ipAddress
    private var movingAction:Utility.MovingAction? = null
    private var dataTransferSocket:Socket? = Socket()

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground() {
        var dataOutputStream: DataOutputStream? = null

        if(!dataTransferSocket!!.isConnected) {
            try {
                Log.e("connect socket", "done")
                dataTransferSocket!!.connect(InetSocketAddress(SERVER_IP_ADDRESS, SERVER_PORT))
            } catch (e: Exception) {
                Log.e("error socket", "done")
                Log.e("error", e.message + e.localizedMessage)
            }

            try {
                Log.e("put data socket", "done")
                dataOutputStream = DataOutputStream(dataTransferSocket!!.getOutputStream())
                if (movingAction != null)
                    dataOutputStream.writeUTF(movingAction!!.value())
                Log.e("data send", "done")
            } finally {
                if (dataOutputStream != null)
                    dataOutputStream.close()
            }
        }else {
            try {
                Log.e("put data socket", "done")
                dataOutputStream = DataOutputStream(dataTransferSocket!!.getOutputStream())
                if (movingAction != null)
                    dataOutputStream.writeUTF(movingAction!!.value())
                Log.e("data send", "done")
            } finally {
                if (dataOutputStream != null)
                    dataOutputStream.close()
            }
        }

    }

    companion object {
        // to ensure there is no more than one instance is created.
         fun getInstance(port: Int, ipAddress: String, context: Context,
                         sendingDataCallback: SendingDataCallback): SendingDataAsyncTask {
            val instance: SendingDataAsyncTask by lazy { SendingDataAsyncTask(port, ipAddress, context, sendingDataCallback = sendingDataCallback) }

            return instance
        }

    }

    fun connect(movingAction: Utility.MovingAction){

        this.movingAction = movingAction;
        sendingDataCallback.connect()
    }

    fun disConnect(){
        Log.e("close socket","done")
        if(dataTransferSocket != null)
            dataTransferSocket!!.close()
    }
}