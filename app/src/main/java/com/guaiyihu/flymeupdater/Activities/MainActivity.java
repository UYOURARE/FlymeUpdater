package com.guaiyihu.flymeupdater.Activities;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.RecoverySystem;
import android.widget.Toast;

import com.guaiyihu.flymeupdater.Application.CustomApplication;
import com.guaiyihu.flymeupdater.Events.OTAEvent;
import com.guaiyihu.flymeupdater.R;
import com.guaiyihu.flymeupdater.Model.MainModel;
import com.guaiyihu.flymeupdater.Presenter.MainPresenter;
import com.guaiyihu.flymeupdater.Recievers.NetworkChangeReceiver;
import com.guaiyihu.flymeupdater.Events.MessageEvent;
import com.guaiyihu.flymeupdater.Utils.UpdateTools;
import com.guaiyihu.flymeupdater.View.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MainView{

    private String currentVersion;//获取当前flyme版本
    private Button check;
    private TextView newMessage;
    private TextView version;
    private File otaZip = null;
    private IntentFilter intentFilter;
    private EditText newVersionMessage;
    private int netState = 0;//0.Wi-Fi,1.移动数据,2.无网络
    private int mode = 0;
    private NetworkChangeReceiver networkChangeReceiver;
    private DownloadReceiver receiver;
    private AlertDialog.Builder dialog;
    private DownloadManager dManager;
    private UpdateTools updateTools;
    private MainPresenter mMainPresenter;
    private CustomApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();//实例化各种类
        EventBus.getDefault().register(this);
        registerReceiver(networkChangeReceiver,intentFilter);

        version.setText("当前版本 " + currentVersion);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(netState == 2){
                    updateTools.sendUpdateMessage(6);
                    Toast.makeText(MainActivity.this, "当前没有网络", Toast.LENGTH_SHORT).show();
                }else {
                    if(mode == 0) {//0模式点击后检查更新
                        newMessage.setText("");
                        mMainPresenter.check();
                        updateTools.sendUpdateMessage(4);
                    } else if(mode == 1) {//1模式监听下载事件然后
                        updateTools.download(app.getUri(), app.getNewVersion(), dManager);
                        updateTools.sendUpdateMessage(2);
                    }else if(mode == 3) {//3模式执行安装
                        otaZip = Environment.getExternalStoragePublicDirectory("/sdcard/Download/" + app.getNewVersion() + ".zip");
                        try {
                            RecoverySystem.installPackage(MainActivity.this, otaZip);
                        } catch (IOException e) {
                            Log.e("", e.getMessage());
                        }
                    }
                }

            }
        });

    }

    private void initView() {
        app = (CustomApplication) getApplication();
        updateTools = new UpdateTools();
        currentVersion = updateTools.getCurrentVersion();
        check = (Button)findViewById(R.id.check);
        version = (TextView)findViewById(R.id.version);
        newMessage = (TextView)findViewById(R.id.message);
        receiver = new DownloadReceiver();
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver(MainActivity.this);
        dialog =new AlertDialog.Builder(MainActivity.this);
        dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        newVersionMessage = (EditText)findViewById(R.id.newMessage);
        mMainPresenter = new MainPresenter();
        mMainPresenter.attachView(this);
    }


    //EventBus处理接收到的讯息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMessageEvent(MessageEvent messageEvent) {
        switch (messageEvent.getNetState()){
            case 0:
                break;
            case 1:
                Toast.makeText(MainActivity.this, "注意：你在使用手机流量哟~", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                dialog.setTitle("当前没有网络！");
                dialog.setMessage("请检查你的网络连接！");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setNegativeButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =  new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                        startActivity(intent);
                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowOTAEvent(OTAEvent otaEvent) {
        switch (otaEvent.getOTAMode()){
            case 1:
                check.setText("立即下载");
                newVersionMessage.setText("检测到新版本: " + app.getNewVersion() + "\n" + app.getOTAMessage());
                newMessage.setText("");
                mode = 1;
                break;
            case 2:
                check.setText("正在下载");
                mode = 2;
                break;
            case 3:
                check.setText("立即安装");
                mode = 3;
                break;
            case 4:
                check.setText("正在检查更新...");
                mode = 4;
                break;
            case 5:
                check.setText("检查更新");
                newMessage.setText("当前无可用更新");
                mode = 0;
                break;
            case 6:
                check.setText("检查更新");
                newMessage.setText("无法检查更新");
                break;
            case 7:
                check.setText("检查更新");
                newMessage.setText("当前版本过老,请下载完整包或者历史ota升级包");
                mode = 0;
                break;
        }
    }

    @Override
    public void compareVersion(MainModel mainModel) {

        app.setNeedVersion(mainModel.getNeedVersion());
        app.setOTAMessage(mainModel.getOTAMessage());
        app.setNewVersion(mainModel.getNewVersion());
        app.setUri(mainModel.getUri());

        if(currentVersion.equals(mainModel.getNewVersion())){
            updateTools.sendUpdateMessage(5);
        }else if(currentVersion.equals(mainModel.getNeedVersion())){
            updateTools.sendUpdateMessage(1);
        }else{
            updateTools.sendUpdateMessage(7);
        }
    }

    //监测下载是否完成
    class DownloadReceiver extends BroadcastReceiver {

        @SuppressLint("NewApi")
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                updateTools.sendUpdateMessage(3);
            }
        }
    }

    @Override
    protected void onResume() {
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        registerReceiver(networkChangeReceiver,intentFilter);
        super.onResume();
    }

    @Override
    protected void onDestroy() {

        if(receiver != null){
            unregisterReceiver(receiver);
        }

        if(networkChangeReceiver != null){
            unregisterReceiver(networkChangeReceiver);
        }

        EventBus.getDefault().unregister(this);
        mMainPresenter.detachView();
        super.onDestroy();
    }

}
