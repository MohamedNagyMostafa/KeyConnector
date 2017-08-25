package com.kc.pr.nagy.mohamed.keyconnector.network;

/**
 * Created by mohamednagy on 8/24/2017.
 */


public enum WifiManagerClass {
    getWifiApConfiguration("getWifiApConfiguration"),
    setWifiApConfiguration("setWifiApConfiguration"),
    isWifiEnabled("isWifiEnabled"),
    setWifiApEnabled("setWifiApEnabled"),
    isWifiApEnabled("isWifiApEnabled"),
    setWifiEnabled("setWifiEnabled");

    public String stringMethodName;
    WifiManagerClass(String stringName){
        stringMethodName = stringName;
    }
}
