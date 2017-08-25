package com.kc.pr.nagy.mohamed.keyconnector

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by mohamednagy on 8/25/2017.
 */
class MainActivityFragment:Fragment(){


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val clients = ArrayList<String>()
        val listAdapter = ArrayAdapter<String>(context, 0, clients)

        clientAddressIpListView.adapter = listAdapter



        return inflater!!.inflate(R.layout.fragment_main, container, false);
    }


}