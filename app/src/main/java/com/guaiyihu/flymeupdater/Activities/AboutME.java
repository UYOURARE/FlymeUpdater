package com.guaiyihu.flymeupdater.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.guaiyihu.flymeupdater.R;

/**
 * Created by GuaiYiHu on 16/6/22.
 */
public class AboutMe extends AppCompatActivity {

    private CardView weibo;
    private CardView QQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        weibo = (CardView)findViewById(R.id.weibo);
        QQ = (CardView)findViewById(R.id.QQ);

        weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://weibo.com/2169007520"));
                startActivity(i);
            }
        });

        QQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinQQGroup("vQs-sF1YPelzJAwciSXI9eeuyBYNJnrA");
            }
        });

    }

    /****************
     *
     * 发起添加群流程。群号：米3-TD Flyme5交流体验群(568969731) 的 key 为： vQs-sF1YPelzJAwciSXI9eeuyBYNJnrA
     * 调用 joinQQGroup(vQs-sF1YPelzJAwciSXI9eeuyBYNJnrA) 即可发起手Q客户端申请加群 米3-TD Flyme5交流体验群(568969731)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;

        }
        return true;
    }

}
