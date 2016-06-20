package com.guaiyihu.flymeupdater.Presenter;

import com.guaiyihu.flymeupdater.Model.MainModel;
import com.guaiyihu.flymeupdater.View.MainView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by GuaiYiHu on 16/6/20.
 */
public class MainPresenter implements PresenterInterface{

    private MainView mMainView;


    @Override
    public void attachView(MainView view) {
        this.mMainView = view;
    }

    public void check() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpGet get = new HttpGet("https://raw.githubusercontent.com/GuaiYiHu/FlymeOTASite/master/update.json");
                HttpClient httpClient = new DefaultHttpClient();
                try {
                    HttpResponse httpResponse = httpClient.execute(get);
                    String s = EntityUtils.toString(httpResponse.getEntity());
                    s = s.replace("\n","\\n");
                    try {
                        MainModel mainModel = new MainModel();
                        JSONArray jsonArray = new JSONArray(s);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        try {
                            mainModel.setNewVersion(jsonObject.getString("version"));
                            mainModel.setUri(jsonObject.getString("Uri"));
                            mainModel.setOTAMessage(jsonObject.getString("message"));
                            mainModel.setNeedVersion(jsonObject.getString("needVersion"));
                            mMainView.compareVersion(mainModel);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    public void detachView() {
        this.mMainView = null;
    }

}
