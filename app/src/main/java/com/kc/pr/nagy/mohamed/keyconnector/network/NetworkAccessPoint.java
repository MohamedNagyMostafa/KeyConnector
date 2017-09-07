package com.kc.pr.nagy.mohamed.keyconnector.network;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v4.app.LoaderManager;
import android.util.Log;

import com.kc.pr.nagy.mohamed.keyconnector.interfaces.MainThreadCallback;
import com.kc.pr.nagy.mohamed.keyconnector.process.Utility;
import com.kc.pr.nagy.mohamed.keyconnector.threads.clients.ClientAsyncTask;
import com.kc.pr.nagy.mohamed.keyconnector.threads.clients.ClientLoaderMangerCallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;


/**
 * Created by mohamednagy on 8/24/2017.
 */

public class NetworkAccessPoint {

    private static final String SSID_NAME_RONDAMLY = "AKYOE1242DAOEUVAOmDemaoEQEwpEOaslkt37890ZXYeoaG";

    private static final int INITIALIZE_LOADER = 0;
    private static final int RESTART_LOADER = 1;

    private WifiManager mWifiManager = null;
    private ClientAsyncTask clientAsyncTask = null;


    private NetworkAccessPoint(){}

    private static class InstanceHolder{
        static final NetworkAccessPoint networkAccessPoint = new NetworkAccessPoint();
    }

    public static NetworkAccessPoint getInstance(WifiManager wifiManager){
        InstanceHolder.networkAccessPoint.mWifiManager = wifiManager;
        return InstanceHolder.networkAccessPoint;
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
            setWifiEnabled = wifiManagerClass.getDeclaredMethod(
                    WifiManagerClass.setWifiEnabled.stringMethodName,
                    WifiConfiguration.class,
                    boolean.class);
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
            setWifiAPEnabled = wifiManagerClass.getDeclaredMethod(WifiManagerClass.setWifiApEnabled.stringMethodName,
                    WifiConfiguration.class, boolean.class);
            isEnabled = (Boolean) setWifiAPEnabled.invoke(mWifiManager, wifiConfiguration, enabled);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return isEnabled;
    }

    /**
     * To start search about the connected devices to access point.
     * If there is loader is created before restart that loader again.
     * if not initialize new one.
     * @param context   context of view which display clients
     * @param loaderManager loader manager for current activity
     * @param mainThreadCallback    
     * @return
     */
    public int startClientsSearch(Context context, LoaderManager loaderManager,
                                  MainThreadCallback mainThreadCallback){
        int searchState;

        if(loaderManager.getLoader(Utility.Loaders.CLIENTS_LOADER_MANAGER.value()) == null) {

            loaderManager.initLoader(
                    Utility.Loaders.CLIENTS_LOADER_MANAGER.value(), null,
                    new ClientLoaderMangerCallback(context, mainThreadCallback));

            searchState = INITIALIZE_LOADER;
        }else{
            loaderManager.restartLoader(
                    Utility.Loaders.CLIENTS_LOADER_MANAGER.value(), null,
                    new ClientLoaderMangerCallback(context, mainThreadCallback));
            searchState = RESTART_LOADER;
        }


        return searchState;

    }


    public void generateConfigurationAccessPoint(){

        // close current configuration.
        if(isWifiApEnabled()){
            WifiConfiguration currentWifiConfiguration = getWifiConfiguration();
            setWifiAPEnabled(currentWifiConfiguration, false);
        }
        // create new configuration
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = String.format("\"%s\"", getSSID());
        wifiConfiguration.preSharedKey = "\"password\"";
        wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        // close wifi when it's opened
        if(isWifiEnabled()){
            mWifiManager.setWifiEnabled(false);
        }
        // set the new access point on.
        boolean isCreated = setWifiAPEnabled(wifiConfiguration, true);
        if(isCreated){
            Log.e("AP is Creater", "SSID : " + wifiConfiguration.SSID );
        }

    }

    private String getSSID(){
        String SSID_NAME = "";

        for(int i = 0 ; i < 25 ; i++){
            SSID_NAME += SSID_NAME_RONDAMLY.charAt(new Random().nextInt(SSID_NAME_RONDAMLY.length() - 1));
        }

        return SSID_NAME;
    }

}
