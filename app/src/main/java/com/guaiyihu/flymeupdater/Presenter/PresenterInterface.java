package com.guaiyihu.flymeupdater.Presenter;

import com.guaiyihu.flymeupdater.View.MainView;

/**
 * Created by GuaiYiHu on 16/6/20.
 */
public interface PresenterInterface {

    void attachView(MainView view);

    void detachView();

}
