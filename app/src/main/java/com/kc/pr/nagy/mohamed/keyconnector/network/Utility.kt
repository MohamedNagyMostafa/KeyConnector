package com.kc.pr.nagy.mohamed.keyconnector.network

import android.net.wifi.WifiConfiguration
import java.lang.StringBuilder
import java.util.*

/**
 * Created by mohamednagy on 8/25/2017.
 */

abstract class Utility{

    private val SSID_NAME_RONDAMLY:StringBuilder =
            StringBuilder().append("AKYOE1242DAOEUV-AOmDemaoEQEwpEOaslkt37890ZXYeoaG")
    
    public fun generateConfigurationAccessPoint(): WifiConfiguration{
        val wifiConfiguration:WifiConfiguration = WifiConfiguration()
        val SSIDNAME:String = getSSID()
        
        wifiConfiguration.SSID = "\"$SSIDNAME\""
        wifiConfiguration.preSharedKey = "\"password\""
        wifiConfiguration.status = WifiConfiguration.Status.ENABLED
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
        wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
        wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN)
        
        return wifiConfiguration
    }
    
    private fun getSSID():String{
        var SSID_NAME: String = ""

        for(item in 1..SSID_NAME_RONDAMLY.length){
            SSID_NAME = SSID_NAME_RONDAMLY.get(Random().nextInt(SSID_NAME_RONDAMLY.length)).toString()
        }

        return SSID_NAME
    }
}