package com.guaiyihu.flymeupdater.Application;

import android.app.Application;

/**
 * Created by GuaiYiHu on 16/6/20.
 */
public class CustomApplication extends Application{

    String newVersion;
    String uri;
    String OTAMessage;
    String needVersion;

    @Override
    public void onCreate()
    {
        super.onCreate();
    }


    public String getNewVersion(){
        return newVersion;
    }

    public void setNewVersion(String newVersion){
        this.newVersion = newVersion;
    }

    public String getUri(){
        return uri;
    }

    public void setUri(String uri){
        this.uri = uri;
    }

    public String getOTAMessage(){
        return OTAMessage;
    }

    public void setOTAMessage(String OTAMessage){
        this.OTAMessage = OTAMessage;
    }

    public String getNeedVersion(){
        return needVersion;
    }

    public void setNeedVersion(String needVersion){
        this.needVersion = needVersion;
    }


}
