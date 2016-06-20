package com.guaiyihu.flymeupdater.Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.guaiyihu.flymeupdater.Events.MessageEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by GuaiYiHu on 16/6/19.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    private int netState = 0;
    private Context mContext;

    public NetworkChangeReceiver(Context context){
        this.mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent){
        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mWifi.isConnected()) {
            netState = 0;

        }else if(mMobile.isConnected()){
            netState = 1;

        }else{
            netState = 2;

        }
        EventBus.getDefault().post(new MessageEvent(netState));

    }
}
