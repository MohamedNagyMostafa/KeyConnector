package com.kc.pr.nagy.mohamed.keyconnector.process.moving

import android.os.Handler
import com.kc.pr.nagy.mohamed.keyconnector.threads.data_sender.SendingDataAsyncTask
import java.util.*

/**
 * Created by mohamednagy on 9/15/2017.
 */

class SynchronousAction(private val sendingDataAsyncTask: SendingDataAsyncTask){

    private val pointsQueue: Queue<MovingPositionCoordinates>? = LinkedList<MovingPositionCoordinates>()
    private val synchronousActionHandler: Handler = Handler()
    private val DELAY: Long = 0x12C
    private val SPLITTER: Char = '_'
    private val requestNewActionThread: Runnable = Runnable{
        if(hasWork()) {
            val action = StringBuilder()

            while (pointsQueue!!.isNotEmpty()) {
                val point = pointsQueue.poll()
                action.append(encodePoint(point))
            }

            sendingDataAsyncTask.addNewAction(encodeAction(action))
        }
    }

    private var isDataChanged: Boolean = false
    private var asyncState: Boolean = false


    fun startAsync(){ if(!isStarted()) synchronousActionHandler.postDelayed(requestNewActionThread, DELAY) }

    fun update(point: MovingPositionCoordinates){
        pointsQueue!!.add(point)
        notifyDataChanged()
    }

    private fun notifyDataChanged(){ isDataChanged = true }

    private fun hasWork(): Boolean = when (isDataChanged){
        true ->{
            isDataChanged = !isDataChanged
            isDataChanged
        }
        else-> isDataChanged
    }


    private fun isStarted(): Boolean = asyncState

    private fun encodePoint(coordinates: MovingPositionCoordinates): String =
            StringBuilder().append(coordinates.x_coordinate).append(SPLITTER)
                .append(coordinates.y_coordinate).append(SPLITTER).toString()

    private fun encodeAction(action: StringBuilder):
            String = action.removeRange(action.lastIndex, action.lastIndex).toString()

}
