package com.kc.pr.nagy.mohamed.keyconnector.interfaces

/**
 * Created by mohamednagy on 8/26/2017.
 */
/**
 * This interface is created to handle with view during thread running in
 * main ui thread.
 * Used when app launch background thread to get  ip of the connected devices
 * with access point and display it in @ListView. Also used when user click refresh for
 * devices ip list to restart background thread. Used to add new device ip to
 * list adapter and when adapter needs to clear it's current elements.
 */
interface MainThreadCallback {
    // Called during thread launching to set the detected ip to
    // list adapter.
    fun mainThreadUiRunAddIp(clientIpAddress: String)
    // Called during thread launching to clear adapter form it's previous
    // data to set new one every new update.
    fun mainThreadUiRunClearUi()
    // Called when the thread time is terminated to stop refresh progress button.
    fun mainThreadUiRunStopRefresh()
    // Called when the thread is beginning to start refresh progress button.
    fun mainThreadUiRunStartRefresh()
}