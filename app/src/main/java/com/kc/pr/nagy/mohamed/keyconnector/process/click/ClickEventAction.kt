package com.kc.pr.nagy.mohamed.keyconnector.process.click

import com.kc.pr.nagy.mohamed.keyconnector.process.Utility
import com.kc.pr.nagy.mohamed.keyconnector.threads.data_sender.SendingDataAsyncTask
import java.util.*

/**
 * Created by mohamednagy on 9/7/2017.
 */

class ClickEventAction(private val ASYNC_DATA_SENDER_TASK: SendingDataAsyncTask) {

    private val CLICK_EVENT_DURATION = 0x12c
    private val LONG_CLICK_EVENT_DURATION = 0x3E8
    private var launchActionAsTimeInMills: Long? = null
    private var oneClickEventProportional = false
    private var longClickEventProportional = false

    fun startListener(){
        val now = Calendar.getInstance()
        launchActionAsTimeInMills = now.timeInMillis
        oneClickEventProportional = true
        longClickEventProportional = true
    }

    fun notifyTouchChanging() {
        if (oneClickEventProportional) {
            longClickEventProportional = false
            if((Calendar.getInstance().timeInMillis - launchActionAsTimeInMills!!) <= CLICK_EVENT_DURATION ){
                ASYNC_DATA_SENDER_TASK.addNewAction(ClickAction(Utility.Click.LEFT_CLICK.value()))
                oneClickEventProportional = false
            }else{
                oneClickEventProportional = false
            }

        }
    }

    fun notifyTouchChanging(distance: Int){
        if(longClickEventProportional) {
            if(distance == 0) {
                if ((Calendar.getInstance().timeInMillis - launchActionAsTimeInMills!!) >= LONG_CLICK_EVENT_DURATION) {
                    ASYNC_DATA_SENDER_TASK.addNewAction(ClickAction(Utility.Click.RIGHT_CLICK.value()))
                    longClickEventProportional = false
                }
            }else{
                longClickEventProportional = false
            }
        }
    }


}
