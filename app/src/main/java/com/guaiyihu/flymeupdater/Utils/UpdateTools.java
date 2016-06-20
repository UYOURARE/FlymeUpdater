package com.guaiyihu.flymeupdater.Utils;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.SystemProperties;

import com.guaiyihu.flymeupdater.Events.OTAEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by GuaiYiHu on 16/6/19.
 */
public class UpdateTools {

    public void download(String ri, String newVersion, DownloadManager downloadManager) {

        Uri uri = Uri.parse(ri);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationInExternalPublicDir("download", newVersion + ".zip");
        request.setDescription(newVersion + "下载");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);
        downloadManager.enqueue(request);
    }

    public void sendUpdateMessage(int i){
        EventBus.getDefault().post(new OTAEvent(i));
    }

    public String getCurrentVersion(){
        return SystemProperties.get("ro.build.display.id");
    }

}

