package com.kc.pr.nagy.mohamed.keyconnector.process

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.kc.pr.nagy.mohamed.keyconnector.threads.SendingDataAsyncTask
import com.kc.pr.nagy.mohamed.keyconnector.threads.SendingDataCallback

/**
 * Created by mohamednagy on 8/28/2017.
 */
class OnTouchListenerMovingAction(ipAddress: String, context:Context, sendingDataCallback: SendingDataCallback) : View.OnTouchListener {
    
    object Position{
        @JvmStatic var x:Int? = null
        @JvmStatic var y:Int? = null
    }
    private val TOUCH_FACTOR:Float = 2f
    private var mSendingDataAsyncTask:SendingDataAsyncTask =
            SendingDataAsyncTask.getInstance(8888, ipAddress, context, sendingDataCallback)

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

        if(p1!!.action == MotionEvent.ACTION_MOVE) {
            if (Position.x == null || Position.y == null) {
                    Log.e("initial touch", "done")
                    Position.x = Math.round(p1.x)
                    Position.y = Math.round(p1.y)

            } else {
                Log.e("x data", "prev : " + Position.x + " new : " + p1.x)
                Log.e("y data", "prev : " + Position.y + " new : " + p1.y)

                if (Position.x!! > Math.round(p1.x) + TOUCH_FACTOR && Position.y!! > Math.round(p1.y) + TOUCH_FACTOR) {
                    mSendingDataAsyncTask.addNewAction(Utility.MovingAction.DECREASE_X_Y_POSITION)
                    Log.e("dis ", "dec x,y")
                }else if (Position.x!! < Math.round(p1.x) - TOUCH_FACTOR && Position.y!! < Math.round(p1.y) - TOUCH_FACTOR) {
                    mSendingDataAsyncTask.addNewAction(Utility.MovingAction.INCREASE_X_Y_POSITION)
                    Log.e("dis ", "inc x,y")

                }else if (Position.x!! > Math.round(p1.x) + TOUCH_FACTOR && Position.y!! < Math.round(p1.y) - TOUCH_FACTOR) {
                    mSendingDataAsyncTask.addNewAction(Utility.MovingAction.INCREASE_Y_DECREASE_X_POSITION)
                    Log.e("dis ", "dec x, inc y")

                }else if (Position.x!! < Math.round(p1.x) - TOUCH_FACTOR && Position.y!! > Math.round(p1.y) + TOUCH_FACTOR) {
                    mSendingDataAsyncTask.addNewAction(Utility.MovingAction.INCREASE_X_DECREASE_Y_POSITION)
                    Log.e("dis ", "dec y, inc x")

                }else if (Position.x!! < Math.round(p1.x)) {
                    mSendingDataAsyncTask.addNewAction(Utility.MovingAction.INCREASE_X_POSITION)
                    Log.e("dis ", "inc x")

                }else if (Position.x!! > Math.round(p1.x)) {
                    mSendingDataAsyncTask.addNewAction(Utility.MovingAction.DECREASE_X_POSITION)
                    Log.e("dis ", "dec x")
                }else if (Position.y!! < Math.round(p1.y)) {
                    mSendingDataAsyncTask.addNewAction(Utility.MovingAction.INCREASE_Y_POSITION)
                    Log.e("dis ", "inc y")
                }else if (Position.y!! > Math.round(p1.y)) {
                    mSendingDataAsyncTask.addNewAction(Utility.MovingAction.DECREASE_Y_POSITION)
                    Log.e("dis ", "dec x")
                }else{
                    Log.e("dix","not defined")
                }

                Position.x = Math.round(p1.x)
                Position.y = Math.round(p1.y)

            }

        }
        return true
    }
}