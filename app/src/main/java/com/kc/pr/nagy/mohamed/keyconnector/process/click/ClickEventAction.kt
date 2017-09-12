package com.kc.pr.nagy.mohamed.keyconnector.process.click

import android.support.annotation.Nullable
import android.util.Log
import com.kc.pr.nagy.mohamed.keyconnector.process.Utility
import com.kc.pr.nagy.mohamed.keyconnector.threads.data_sender.SendingDataAsyncTask
import java.util.*

/**
 * Created by mohamednagy on 9/7/2017.
 */

class ClickEventAction(private val ASYNC_DATA_SENDER_TASK: SendingDataAsyncTask) {

    private val ACTIVE = 0x001
    private val INACTIVE = 0x002

    private val mClickEvent = ClickEvent()
    private val mLongClickEvent = LongClickEvent()
    private val mSelectEvent = SelectEvent()

    var launchActionAsTimeInMills: Long? = null

    fun startListener(){
        launchActionAsTimeInMills = Calendar.getInstance().timeInMillis

        mClickEvent.onEvent(ACTIVE)
        mLongClickEvent.onEvent(ACTIVE)
    }

    fun notifyTouchChanging() {
        if (mClickEvent.state() == ACTIVE) {
            mLongClickEvent.onEvent(INACTIVE)

            if(mClickEvent.canRun()){
                mClickEvent.action()
            }else{
                mClickEvent.onEvent(INACTIVE)
            }

        }else if(mSelectEvent.state() == ACTIVE) {
            mSelectEvent.stopAction()
        }
    }

    /**
     * Called when user's finger touch screen (moving or not)
     * if there is previous click and the duration between previous click
     * and this touch less than or equal 300 ms start select and set both
     * click and long click to inactive state.
     * if distance change and there is event created before(by test if click event inactive or not)
     * then this is just moving.
     * if there is no previous click or duration of select is left then check long click event state
     *  cases :
     *         o the long duration 1sec is left and there is no motion then :
     *                                  o set both select & click event to inactive ..
     *                                     this prevent create click and  send release select action
     *                                     in up finger touch case.
     *                                  o set long event to inactive to prevent create another right
     *                                     click event.
     *         o long event is active but there is a motion then :
     *                                  o set inactive to long click, click and select event.
     */
    fun notifyTouchChanging(distance: Int){
        if (mSelectEvent.canRun() && mClickEvent.state() == ACTIVE) {
            mSelectEvent.startAction()
        } else if (mLongClickEvent.state() == ACTIVE) {
            if (distance == 0) {
                if (mLongClickEvent.canRun()) {
                    mLongClickEvent.action()
                }
            } else {
                mLongClickEvent.onEvent(INACTIVE)
                mClickEvent.onEvent(INACTIVE)
                mSelectEvent.onEvent(INACTIVE, null)
            }
        }

    }

    inner class ClickEvent{
        private var mStart:Int = INACTIVE
        private val mDuration = 0x12c

        fun onEvent(startSt:Int){ mStart = startSt }
        fun canRun(): Boolean = ((Calendar.getInstance().timeInMillis - launchActionAsTimeInMills!!) <= mDuration)
        fun state(): Int = mStart
        fun action(){
            ASYNC_DATA_SENDER_TASK.addNewAction(ClickAction(Utility.Click.LEFT_CLICK.value()))
            mClickEvent.onEvent(INACTIVE)
            mSelectEvent.onEvent(ACTIVE, Calendar.getInstance().timeInMillis)
            Log.e("action","left click")
        }

    }

    inner class LongClickEvent{
        private var mStart:Int = INACTIVE
        private val mDuration = 0x3E8

        fun onEvent(startSt:Int){ mStart = startSt }
        fun canRun(): Boolean = ((Calendar.getInstance().timeInMillis - launchActionAsTimeInMills!!) >= mDuration)
        fun state(): Int = mStart
        fun action(){
            mLongClickEvent.onEvent(INACTIVE)
            mClickEvent.onEvent(INACTIVE)
            mSelectEvent.onEvent(INACTIVE, null)
            ASYNC_DATA_SENDER_TASK.addNewAction(ClickAction(Utility.Click.RIGHT_CLICK.value()))
            Log.e("action","right click")

        }
    }

    inner class SelectEvent{
        private var mStart:Int = INACTIVE
        private var mDuration = 0xC8
        private var mTime:Long? = null

        fun onEvent(startSt:Int,@Nullable time: Long?) {
            mStart = startSt
            mTime = time
        }
        fun canRun(): Boolean = (mStart == ACTIVE &&
                (Calendar.getInstance().timeInMillis - mTime!!) <= mDuration)
        fun state(): Int = mStart
        // stop select
        fun stopAction(){
            mSelectEvent.onEvent(INACTIVE, null)
            ASYNC_DATA_SENDER_TASK.addNewAction(ClickAction(Utility.Click.LEFT_CLICK_SELECT_RELEASE.value()))
            Log.e("action","unselect click")

        }
        // start select
        fun startAction(){
            mLongClickEvent.onEvent(INACTIVE)
            mClickEvent.onEvent(INACTIVE)
            ASYNC_DATA_SENDER_TASK.addNewAction(ClickAction(Utility.Click.LEFT_CLICK_SELECT.value()))
            Log.e("action","select click")

        }
    }


}
