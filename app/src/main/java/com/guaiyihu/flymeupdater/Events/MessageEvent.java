package com.guaiyihu.flymeupdater.Events;

/**
 * Created by GuaiYiHu on 16/6/19.
 */
public class MessageEvent {

    private int netState;

    public MessageEvent(int netState) {
        this.netState = netState;
    }

    public int getNetState() {
        return netState;
    }

    public void setNetState(int netState) {
        this.netState = netState;
    }

}
