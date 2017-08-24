package com.kc.pr.nagy.mohamed.keyconnector.network;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by mohamednagy on 8/24/2017.
 */

public class NetworkAccessPoint {
    private WifiManager mWifiManager = null;

    private NetworkAccessPoint(WifiManager wifiManager){
        mWifiManager = wifiManager;
    }

    public NetworkAccessPoint setInstance(WifiManager wifiManager){
        if(mWifiManager == null){
            NetworkAccessPoint networkAccessPoint =
                    new NetworkAccessPoint(wifiManager);
        }
        return this;
    }

    public WifiConfiguration getWifiConfiguration(){

        Method getWifiApConfiguration;
        Class<WifiManager> wifiMangerClass = WifiManager.class;
        WifiConfiguration wifiConfiguration = null;

        try{
            getWifiApConfiguration = wifiMangerClass.getDeclaredMethod(WifiManagerClass.getWifiApConfiguration.stringMethodName);
            wifiConfiguration = (WifiConfiguration) getWifiApConfiguration.invoke(mWifiManager);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return wifiConfiguration;
    }

    public boolean setWifiConfiguration(WifiConfiguration wifiConfiguration){

        Method setWifiApConfiguration;
        Class<WifiManager> wifiManagerClass = WifiManager.class;
        Boolean processState = false;

        try{
            setWifiApConfiguration = wifiManagerClass.getDeclaredMethod(WifiManagerClass.setWifiApConfiguration.stringMethodName);
            processState = (Boolean) setWifiApConfiguration.invoke(mWifiManager, wifiConfiguration);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return processState;
    }

    public boolean isWifiEnabled(){

        Method isWifiEnabled;
        Class<WifiManager> wifiManagerClass = WifiManager.class;
        Boolean isEnabled = false;

        try{
            isWifiEnabled = wifiManagerClass.getDeclaredMethod(WifiManagerClass.isWifiEnabled.stringMethodName);
            isEnabled = (Boolean) isWifiEnabled.invoke(mWifiManager);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return isEnabled;
    }

    public boolean isWifiApEnabled(){

        Method isWifiApEnabled;
        Class<WifiManager> wifiManagerClass = WifiManager.class;
        Boolean isEnabled = false;

        try{
            isWifiApEnabled = wifiManagerClass.getDeclaredMethod(WifiManagerClass.isWifiApEnabled.stringMethodName);
            isEnabled = (Boolean) isWifiApEnabled.invoke(mWifiManager);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return isEnabled;
    }

    public boolean setWifiEnabled(WifiConfiguration wifiConfiguration, boolean enabled){

        Method setWifiEnabled;
        Class<WifiManager> wifiManagerClass = WifiManager.class;
        Boolean isEnabled = false;

        try{
            setWifiEnabled = wifiManagerClass.getDeclaredMethod(WifiManagerClass.setWifiEnabled.stringMethodName);
            isEnabled = (Boolean) setWifiEnabled.invoke(mWifiManager, wifiConfiguration, enabled);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return isEnabled;
    }

    public boolean setWifiAPEnabled(WifiConfiguration wifiConfiguration, boolean enabled){

        Method setWifiAPEnabled;
        Class<WifiManager> wifiManagerClass = WifiManager.class;
        Boolean isEnabled = false;

        try{
            setWifiAPEnabled = wifiManagerClass.getDeclaredMethod(WifiManagerClass.setWifiApEnabled.stringMethodName);
            isEnabled = (Boolean) setWifiAPEnabled.invoke(mWifiManager, wifiConfiguration, enabled);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return isEnabled;
    }
    

}
