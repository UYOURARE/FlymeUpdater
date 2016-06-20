package com.guaiyihu.flymeupdater.Events;

/**
 * Created by GuaiYiHu on 16/6/20.
 */
public class OTAEvent {

    private int OTAMode;

    public OTAEvent(int OTAMode){
        this.OTAMode = OTAMode;
    }

    public int getOTAMode(){
        return OTAMode;
    }

    public void setOTAMode(int OTAMode){
        this.OTAMode = OTAMode;
    }

}
