package com.kc.pr.nagy.mohamed.keyconnector.network;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v4.app.LoaderManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.kc.pr.nagy.mohamed.keyconnector.threads.ClientAsyncTask;
import com.kc.pr.nagy.mohamed.keyconnector.threads.ClientLoaderMangerCallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;


/**
 * Created by mohamednagy on 8/24/2017.
 */

public class NetworkAccessPoint {

    private static final String SSID_NAME_RONDAMLY = "AKYOE1242DAOEUVAOmDemaoEQEwpEOaslkt37890ZXYeoaG";

    private static final int CLIENTS_LOADER_MANAGER = 1;
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

    public int startClientsSearch(Context context, List clientsList, LoaderManager loaderManager,
                                  ArrayAdapter<String> clientIpAddressAdapter){
        int searchState;

        if(!loaderManager.hasRunningLoaders()) {
            Toast.makeText(context, "start Loader", Toast.LENGTH_SHORT).show();
            loaderManager.initLoader(CLIENTS_LOADER_MANAGER, null, new ClientLoaderMangerCallback(context, clientsList, clientIpAddressAdapter));
            searchState = INITIALIZE_LOADER;
        }else{
            loaderManager.restartLoader(CLIENTS_LOADER_MANAGER, null, new ClientLoaderMangerCallback(context, clientsList, clientIpAddressAdapter));
            searchState = RESTART_LOADER;
        }

        return searchState;

    }


    public void generateConfigurationAccessPoint(){

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

        if(isWifiEnabled()){
            mWifiManager.setWifiEnabled(false);
        }

        setWifiAPEnabled(wifiConfiguration, true);

    }

    private String getSSID(){
        String SSID_NAME = "";

        for(int i = 0 ; i < 25 ; i++){
            SSID_NAME += SSID_NAME_RONDAMLY.charAt(new Random().nextInt(SSID_NAME_RONDAMLY.length() - 1));
        }

        return SSID_NAME;
    }

}
