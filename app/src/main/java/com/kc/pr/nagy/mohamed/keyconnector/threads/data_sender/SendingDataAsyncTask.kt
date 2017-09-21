package com.kc.pr.nagy.mohamed.keyconnector.threads.data_sender

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.kc.pr.nagy.mohamed.keyconnector.process.click.ClickAction
import java.io.DataOutputStream
import java.io.IOError
import java.net.InetSocketAddress
import java.net.Socket
import java.util.*

/**
 * Created by mohamednagy on 8/25/2017.
 */
class SendingDataAsyncTask private constructor(port:Int, ipAddress:String, context: Context,
                                               private val sendingDataCallback: SendingDataCallback) :
        AsyncTaskLoader<Unit>(context) {

    private var SERVER_PORT:Int = port
    private var SERVER_IP_ADDRESS:String = ipAddress

    object Action{
        val IDLE_STATE = 1;
        val WAITING_NEW_PROCESS = 2;
        @JvmStatic var actionsQueue:Queue<Any> = LinkedList<Any>()
        @JvmStatic var mThreadState = IDLE_STATE
    }
    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()

    }

    override fun loadInBackground() {
        Action.mThreadState = Action.WAITING_NEW_PROCESS
        val dataTransferSocket = Socket()
        dataTransferSocket.connect(InetSocketAddress(SERVER_IP_ADDRESS, SERVER_PORT))

        while(true){
            while(Action.actionsQueue.isEmpty());
            var dataOutputStream:DataOutputStream?
            try {
                dataOutputStream = DataOutputStream(dataTransferSocket.getOutputStream())
                val actionData = Action.actionsQueue.poll()

                dataOutputStream.writeUTF(encodeData(actionData))

            } catch (e: Exception) {
                break;

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

    fun addNewAction(action: Any){

        Action.actionsQueue.add(action)

        if(Action.mThreadState == Action.IDLE_STATE)
            sendingDataCallback.connect()
    }

    /**
     * Determine which action will be send to pc.
     * Action type .. Moving action and click action.
     */
    private fun encodeData(actionData: Any): String = when (actionData) {
            is String  -> actionData.substring(0, actionData.length -1)
            is ClickAction -> StringBuilder().append(actionData.getClickType()).toString()
            else -> throw IOError(Throwable("no data to send"))

    }
}