package com.kc.pr.nagy.mohamed.keyconnector.ui

import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ListView
import com.kc.pr.nagy.mohamed.keyconnector.R

/**
 * Created by mohamednagy on 8/26/2017.
 */
class PrViewHolder{

    inner class MainActivityViewHolder(mainActivityView: View){
        val CLIENT_ADDRESS_LIST_VIEW: ListView = mainActivityView.findViewById(R.id.client_address_list_view) as ListView
        val SWAP_REFRESH_LAYOUT: SwipeRefreshLayout = mainActivityView.findViewById(R.id.swap_refresh_layout) as SwipeRefreshLayout
    }

}