package com.kc.pr.nagy.mohamed.keyconnector.threads

import android.os.AsyncTask
import android.util.Log
import com.kc.pr.nagy.mohamed.keyconnector.process.Utility
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Created by mohamednagy on 8/25/2017.
 */
class SendingDataAsyncTask private constructor(port:Int, ipAddress:String) : AsyncTask<Utility.MovingAction, Unit, Unit>() {

    private var SERVER_PORT:Int = port
    private var SERVER_IP_ADDRESS:String = ipAddress
    private val TIME_OUT:Int = 5000

    // thread connection state
    private val CONNECTION_UNDER_PROCESS:Int = 0
    private val CONNECTION_REFUSED:Int = 1
    private val CONNECTION_DELAY:Int = 2

    // States of thread.
    private val UNDER_WAITING_NEW_PROCESS = 1
    private val IDLE_STATE = 2
    private val CONNECTION_FIELD = 3

    private var mIsThreadRunning:Int = IDLE_STATE

    private var dataTransferSocket:Socket? = null

    companion object {
        // to ensure there is no more than one instance is created.
         fun getInstance(port: Int, ipAddress: String): SendingDataAsyncTask {
            val instance: SendingDataAsyncTask by lazy { SendingDataAsyncTask(port, ipAddress) }

            return instance
        }

    }

    override fun doInBackground(vararg movingAction: Utility.MovingAction) {

        if(!dataTransferSocket!!.isConnected) {
            try {
                Log.e("connect socket", "done")
                dataTransferSocket!!.connect(InetSocketAddress(SERVER_IP_ADDRESS, SERVER_PORT))
            } catch (e: Exception) {
                Log.e("error socket", "done")
                Log.e("error", e.message + e.localizedMessage)
                mIsThreadRunning = CONNECTION_FIELD
            }
        }else{
            var dataOutputStream: DataOutputStream? = null
            try{
                Log.e("put data socket", "done")
                dataOutputStream = DataOutputStream(dataTransferSocket!!.getOutputStream())
                dataOutputStream.writeUTF(movingAction[0].value())
                Log.e("data send", "done")
            }finally {
                if(dataOutputStream != null)
                    dataOutputStream.close()
            }
        }

    }

    override fun onPostExecute(data:Unit) {
        mIsThreadRunning = UNDER_WAITING_NEW_PROCESS // ready to get new process.

    }


    public fun getState(): Int = mIsThreadRunning

    public fun connect(movingAction: Utility.MovingAction){

        if(dataTransferSocket == null){
            Log.e("create socket", "done")
            dataTransferSocket = Socket()
        }
        execute(movingAction)
    }

    public fun disConnect(){
        if(dataTransferSocket != null)
            dataTransferSocket!!.close()
    }
}