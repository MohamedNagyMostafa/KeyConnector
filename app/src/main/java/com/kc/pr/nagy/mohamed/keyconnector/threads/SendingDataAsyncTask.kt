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
    private var dataTransferSocket:Socket? = Socket()

    object Action{
        val IDLE_STATE = 1;
         val WAITING_NEW_PROCESS = 2;
        @JvmStatic var movingAction:Utility.MovingAction? = null
        @JvmStatic var mThreadState = IDLE_STATE
    }
    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground() {
        Action.mThreadState = Action.WAITING_NEW_PROCESS
        dataTransferSocket!!.connect(InetSocketAddress(SERVER_IP_ADDRESS, SERVER_PORT))

        while(true){
            var dataOutputStream:DataOutputStream? = null
            try {
                Log.e("connect socket", "done")
                dataOutputStream = DataOutputStream(dataTransferSocket!!.getOutputStream())
                if (Action.movingAction != null) {
                    dataOutputStream.writeUTF(Action.movingAction!!.value())
                    Log.e("send ", Action.movingAction!!.value())
                }
            } catch (e: Exception) {
                Log.e("error socket", "done")
                Log.e("error", e.message + e.localizedMessage)
                break;

            }finally {
                if (dataOutputStream != null)
                    dataOutputStream.close()
            }
        }
        Action.mThreadState = Action.IDLE_STATE

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

        Action.movingAction = movingAction;
        if(Action.mThreadState == Action.IDLE_STATE)
            sendingDataCallback.connect()
    }

    fun disConnect(){
        Log.e("close socket","done")
        if(dataTransferSocket != null)
            dataTransferSocket!!.close()
    }
}