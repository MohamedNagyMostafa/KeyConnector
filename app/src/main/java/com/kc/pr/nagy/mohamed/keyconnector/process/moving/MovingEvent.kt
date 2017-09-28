package com.kc.pr.nagy.mohamed.keyconnector.process.moving

import android.view.MotionEvent
import com.kc.pr.nagy.mohamed.keyconnector.threads.data_sender.SendingDataAsyncTask
import java.util.*

/**
 * Created by mohamednagy on 9/26/2017.
 */
class MovingEvent(private val mSendingDataAsyncTask: SendingDataAsyncTask){
    private val NULL = 0
    private var mX_distance: Int = NULL
    private var mY_distance: Int = NULL
    private var mDuration: Long = NULL.toLong()

    private var mPre_xCoordinate: Int = NULL
    private var mPre_yCoordinate: Int = NULL
    private var mPreTime: Long = NULL.toLong()

    private var mCurrTime: Long = NULL.toLong()
    private var mCurr_xCoordinate: Int = NULL
    private var mCurr_yCoordinate: Int = NULL

    fun update(motionEvent: MotionEvent){
        mCurr_xCoordinate = Math.round(motionEvent.x)
        mCurr_yCoordinate = Math.round(motionEvent.y)
        updateTime()

        if(mPre_xCoordinate != NULL || mPre_yCoordinate != NULL) {
            mX_distance = mCurr_xCoordinate - mPre_xCoordinate
            mY_distance = mCurr_yCoordinate - mPre_yCoordinate

            updateDuration()

            if (mX_distance + mY_distance != NULL)
                addAction()
        }
    }

    private fun updateTime(){
        mCurrTime = Calendar.getInstance().timeInMillis
    }

    fun updateMotionCompleted(){
        mPreTime = mCurrTime
        mPre_xCoordinate = mCurr_xCoordinate
        mPre_yCoordinate = mCurr_yCoordinate
    }

    private fun updateDuration(){
        mDuration = mCurrTime - mPreTime
    }

    fun stopDetect(){
        mPre_xCoordinate = NULL
        mPre_yCoordinate = NULL
        mCurr_yCoordinate = NULL
        mCurr_xCoordinate = NULL
        mPreTime = NULL.toLong()
        mCurrTime = NULL.toLong()
    }

    fun getDistance(): Int = mY_distance + mX_distance

    private fun addAction(){
        mSendingDataAsyncTask.addNewAction(MovingPositionCoordinates(x_coordinateDistance =  mX_distance,
                y_coordinateDistance =  mY_distance, duration = mDuration))
//        Log.e("data " , "X: $mX_distance Y: $mY_distance duration : $mDuration")
    }
}