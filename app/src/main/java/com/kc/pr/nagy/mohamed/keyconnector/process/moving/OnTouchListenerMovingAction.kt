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

    private val mSendingDataAsyncTask: SendingDataAsyncTask =
            SendingDataAsyncTask.getInstance(port =  SERVER_PORT, ipAddress =  ipAddress, context = context,
                    sendingDataCallback = sendingDataCallback)
    private val mClickEventAction: ClickEventAction = ClickEventAction(ASYNC_DATA_SENDER_TASK =  mSendingDataAsyncTask)
    private val mMovingEventAction: MovingEvent = MovingEvent(mSendingDataAsyncTask =  mSendingDataAsyncTask)

    override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {

        when(motionEvent!!.action){
            MotionEvent.ACTION_MOVE ->{
                mMovingEventAction.update(motionEvent)
                mClickEventAction.notifyTouchChanging(mMovingEventAction.getDistance())
                mMovingEventAction.updateMotionCompleted()
            }

            MotionEvent.ACTION_UP ->{
                mClickEventAction.notifyTouchChanging()
                mMovingEventAction.stopDetect()
            }

            MotionEvent.ACTION_DOWN ->{
                mMovingEventAction.update(motionEvent)
                mClickEventAction.startListener()
            }
        }
        return true;
    }
}