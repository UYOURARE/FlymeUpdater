package com.guaiyihu.flymeupdater.Model;

/**
 * Created by GuaiYiHu on 16/6/20.
 */
public class MainModel {

    String newVersion;
    String uri;
    String OTAMessage;
    String needVersion;

    public MainModel(){

    }

    public MainModel(String newVersion, String uri, String OTAMessage, String needVersion){
        this.newVersion = newVersion;
        this.uri = uri;
        this.OTAMessage = OTAMessage;
        this.needVersion = needVersion;
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
