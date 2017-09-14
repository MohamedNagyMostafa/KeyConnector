package com.kc.pr.nagy.mohamed.keyconnector.network

/**
 * Created by mohamednagy on 8/24/2017.
 */


enum class WifiManagerClass private constructor(var stringMethodName: String) {
    getWifiApConfiguration("getWifiApConfiguration"),
    setWifiApConfiguration("setWifiApConfiguration"),
    isWifiEnabled("isWifiEnabled"),
    setWifiApEnabled("setWifiApEnabled"),
    isWifiApEnabled("isWifiApEnabled"),
    setWifiEnabled("setWifiEnabled")
}
