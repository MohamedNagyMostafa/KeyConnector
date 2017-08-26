package com.kc.pr.nagy.mohamed.keyconnector.interfaces

/**
 * Created by mohamednagy on 8/26/2017.
 */
interface MainThreadCallback {
    fun mainThreadUiRunAddIp(clientIpAddress: String)
    fun mainThreadUiRunClearUi()
    fun mainThreadUiRunStopRefresh()
    fun mainThreadUiRunStartRefresh()
}