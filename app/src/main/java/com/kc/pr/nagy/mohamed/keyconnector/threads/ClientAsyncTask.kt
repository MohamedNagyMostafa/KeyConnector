package com.kc.pr.nagy.mohamed.keyconnector.threads

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.kc.pr.nagy.mohamed.keyconnector.interfaces.MainThreadCallback
import java.io.FileReader

/**
 * Created by mohamednagy on 8/25/2017.
 */
class ClientAsyncTask(context :Context, mainThreadCallback: MainThreadCallback)
    : AsyncTaskLoader<Unit>(context){

    private val SEARCH_TIME_LIMIT:Int = 10
    private val THREAD_SLEEP:Long = 2000
    private val FILE_AP_INFO:String = "proc/net/arp"
    private val INCREASING_CONSTANT:Int = 2
    private val FIRST_LINE_IGNORE:Int = 0
    private val mMainThreadCallback:MainThreadCallback = mainThreadCallback


    override fun onStartLoading() {
        super.onStartLoading()
        mMainThreadCallback.mainThreadUiRunStartRefresh()
        forceLoad()
    }
    /**
     * The process of method worked for 10 seconds ... every two second
     * check the hotspot clients if there is a new client is connected.
     */
    override fun loadInBackground() {
        var timer = 0

        while (timer != SEARCH_TIME_LIMIT){
            getClientsAndUpdate()
            Thread.sleep(THREAD_SLEEP)
            timer += INCREASING_CONSTANT
        }
    }

    private fun getClientsAndUpdate(){
        val file = FileReader(FILE_AP_INFO)
        val lines = file.readLines()
        mMainThreadCallback.mainThreadUiRunClearUi()

        for(line in lines){
            var ipAddress = ""
            if(lines.indexOf(line) != FIRST_LINE_IGNORE){
                line.trim()
                for(ipAddressAsChars in line){
                    val ipChar:Char = ipAddressAsChars
                    if(ipAddressAsChars == ' '){
                        break;
                    }
                    ipAddress += ipChar
                }
            }
            mMainThreadCallback.mainThreadUiRunAddIp(ipAddress)
        }

    }


}