package com.kc.pr.nagy.mohamed.keyconnector.process.moving

import android.content.Context
import android.view.MotionEvent
import android.view.View
import com.kc.pr.nagy.mohamed.keyconnector.process.click.ClickEventAction
import com.kc.pr.nagy.mohamed.keyconnector.threads.data_sender.SendingDataAsyncTask
import com.kc.pr.nagy.mohamed.keyconnector.threads.data_sender.SendingDataCallback

/**
 * Created by mohamednagy on 8/28/2017.
 */
class OnTouchListenerMovingAction(ipAddress: String, context:Context, sendingDataCallback: SendingDataCallback) : View.OnTouchListener {

    private val SERVER_PORT = 8888

    object Position{
        @JvmStatic var x:Float? = null
        @JvmStatic var y:Float? = null
    }

    private val mSendingDataAsyncTask: SendingDataAsyncTask =
            SendingDataAsyncTask.getInstance(SERVER_PORT, ipAddress, context, sendingDataCallback)

    private val mClickEventAction: ClickEventAction = ClickEventAction(mSendingDataAsyncTask)
    private val mSynchronousAction: SynchronousAction = SynchronousAction(mSendingDataAsyncTask)

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        when(p1!!.action){
            MotionEvent.ACTION_MOVE ->{

                val moving_X_Distance:Int = (p1.x - Position.x!!).toInt()
                val moving_Y_Distance:Int = (p1.y - Position.y!!).toInt()
                val movingPositionCoordinates = MovingPositionCoordinates(
                        moving_X_Distance, moving_Y_Distance)
                if(moving_X_Distance != 0 || moving_Y_Distance != 0)
                    mSynchronousAction.update(movingPositionCoordinates)

                mClickEventAction.notifyTouchChanging(moving_X_Distance + moving_Y_Distance)
                Position.x = p1.x
                Position.y = p1.y

            }
            MotionEvent.ACTION_UP ->{
                Position.x = null
                Position.y = null
                mClickEventAction.notifyTouchChanging()
            }

            MotionEvent.ACTION_DOWN ->{
                Position.x = p1.x
                Position.y = p1.y

                mClickEventAction.startListener()
                mSynchronousAction.startAsync()
            }
        }
        return true
    }
}