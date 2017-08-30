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
    private var mPreviousXPosition:Int? = null
    private var mPreviousYPosition:Int? = null
    private var mSendingDataAsyncTask:SendingDataAsyncTask =
            SendingDataAsyncTask.getInstance(8888, ipAddress, context, sendingDataCallback)

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        Log.e("called","done");
        if(p1!!.action == MotionEvent.ACTION_MOVE) {
            if (mPreviousXPosition == null || mPreviousYPosition == null) {
                    Log.e("initial touch", "done")
                    mPreviousXPosition = p1.x.toInt()
                    mPreviousYPosition = p1.y.toInt()

            } else {
                if (mPreviousXPosition!! > p1.x && mPreviousYPosition!! > p1.y)
                    mSendingDataAsyncTask.connect(Utility.MovingAction.DECREASE_X_Y_POSITION)
                else if (mPreviousXPosition!! < p1.x && mPreviousYPosition!! < p1.y)
                    mSendingDataAsyncTask.connect(Utility.MovingAction.INCREASING_X_Y_POSITION)
                else if (mPreviousXPosition!! > p1.x && mPreviousYPosition!! < p1.y)
                    mSendingDataAsyncTask.connect(Utility.MovingAction.INCREASE_Y_DECREASE_X_POSITION)
                else if (mPreviousXPosition!! < p1.x && mPreviousYPosition!! > p1.y)
                    mSendingDataAsyncTask.connect(Utility.MovingAction.INCREASE_X_DECREASE_Y_POSITION)
                else if (mPreviousXPosition!! < p1.x)
                    mSendingDataAsyncTask.connect(Utility.MovingAction.INCREASE_X_POSITION)
                else if (mPreviousXPosition!! < p1.x)
                    mSendingDataAsyncTask.connect(Utility.MovingAction.DECREASE_X_POSITION)
                else if (mPreviousXPosition!! < p1.y)
                    mSendingDataAsyncTask.connect(Utility.MovingAction.INCREASE_Y_POSITION)
                else if (mPreviousXPosition!! < p1.y)
                    mSendingDataAsyncTask.connect(Utility.MovingAction.DECREASE_Y_POSITION)
            }
        }
        return true
    }
}