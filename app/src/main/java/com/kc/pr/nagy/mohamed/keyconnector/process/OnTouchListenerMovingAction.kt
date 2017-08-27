package com.kc.pr.nagy.mohamed.keyconnector.process

import android.view.MotionEvent
import android.view.View
import com.kc.pr.nagy.mohamed.keyconnector.threads.SendingDataAsyncTask

/**
 * Created by mohamednagy on 8/28/2017.
 */
class OnTouchListenerMovingAction(ipAddress: String) : View.OnTouchListener {
    private var mPreviousXPosition:Int? = null
    private var mPreviousYPosition:Int? = null
    private var mSendingDataAsyncTask:SendingDataAsyncTask =
            SendingDataAsyncTask.getInstance(8888, ipAddress)

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        if(mPreviousXPosition == null || mPreviousYPosition == null){
            if(p1 != null) {
                mPreviousXPosition = p1.x.toInt()
                mPreviousYPosition = p1.y.toInt()
            }
        }else {
            if (mPreviousXPosition!! > p1!!.x && mPreviousYPosition!! > p1.y)
                mSendingDataAsyncTask.connect(MovingAction.DECREASE_X_Y_POSITION)
            else if (mPreviousXPosition!! < p1.x && mPreviousYPosition!! < p1.y)
                mSendingDataAsyncTask.connect(MovingAction.INCREASING_X_Y_POSITION)
            else if (mPreviousXPosition!! > p1.x && mPreviousYPosition!! < p1.y)
                mSendingDataAsyncTask.connect(MovingAction.INCREASE_Y_DECREASE_X_POSITION)
            else if (mPreviousXPosition!! < p1.x && mPreviousYPosition!! > p1.y)
                mSendingDataAsyncTask.connect(MovingAction.INCREASE_X_DECREASE_Y_POSITION)
            else if (mPreviousXPosition!! < p1.x)
                mSendingDataAsyncTask.connect(MovingAction.INCREASE_X_POSITION)
            else if (mPreviousXPosition!! < p1.x)
                mSendingDataAsyncTask.connect(MovingAction.DECREASE_X_POSITION)
            else if (mPreviousXPosition!! < p1.y)
                mSendingDataAsyncTask.connect(MovingAction.INCREASE_Y_POSITION)
            else if (mPreviousXPosition!! < p1.y)
                mSendingDataAsyncTask.connect(MovingAction.DECREASE_Y_POSITION)
        }
        return false
    }
}