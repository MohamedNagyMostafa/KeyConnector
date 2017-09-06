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

    //private val TOUCH_PC_FACTOR:Float = 4f;
    object Position{
        @JvmStatic var x:Float? = null
        @JvmStatic var y:Float? = null
    }
    private val TOUCH_FACTOR:Float = 2f
    private var mSendingDataAsyncTask:SendingDataAsyncTask =
            SendingDataAsyncTask.getInstance(8888, ipAddress, context, sendingDataCallback)

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        when(p1!!.action){
            MotionEvent.ACTION_MOVE ->{

                    Log.e("called start", "start")
                    Log.e("x data", "prev : " + Position.x + " new : " + p1.x)
                    Log.e("y data", "prev : " + Position.y + " new : " + p1.y)

                    val moving_X_Distance = Math.round((p1.x - Position.x!!))
                    val moving_Y_Distance = Math.round((p1.y - Position.y!!))
                    val movingPositionCoordinates = MovingPositionCoordinates(
                            moving_X_Distance , moving_Y_Distance )

                    mSendingDataAsyncTask.addNewAction(movingPositionCoordinates)


                    Position.x = p1.x
                    Position.y = p1.y
                    Log.e("called end", "end")


            }
            MotionEvent.ACTION_UP ->{
                    Log.e("touch up", "done")
                    Position.x = null
                    Position.y = null
            }

            MotionEvent.ACTION_DOWN ->{
                    Log.e("initial Down", "done")
                    Position.x = p1.x
                    Position.y = p1.y


            }
        }
        return true
    }
}