package com.kc.pr.nagy.mohamed.keyconnector.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kc.pr.nagy.mohamed.keyconnector.R
import com.kc.pr.nagy.mohamed.keyconnector.process.OnTouchListenerMovingAction
import com.kc.pr.nagy.mohamed.keyconnector.process.Utility

/**
 * A placeholder fragment containing a simple view.
 */
class ComputerMouseScreenActivityFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater!!.inflate(R.layout.fragment_computer_mouse_screen, container, false)
        val computerMouseScreenViewHolder: PrViewHolder.ComputerMouserScreenViewHolder =
                PrViewHolder().ComputerMouserScreenViewHolder(view)
        val ipAddress = activity.intent.extras.getString(Utility.Extras.IP_ADDRESS_EXTRA.value())

        computerMouseScreenViewHolder.MOUSE_SCREEN_LAYOUT.setOnTouchListener(OnTouchListenerMovingAction(ipAddress))

        return view
    }
}
