package com.example.sanchous19.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    public static NetworkState getConnectivityStatus(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return NetworkState.WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return NetworkState.MOBILE;
        }
        return NetworkState.NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(NetworkState state) {
        if (state == NetworkState.WIFI)
            return "Wifi enabled";
        else if (state == NetworkState.MOBILE)
            return "Mobile data enabled";
        else
            return "Not connected to Internet";
    }
}
