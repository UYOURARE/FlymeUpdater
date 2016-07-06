package com.guaiyihu.flymeupdater.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.guaiyihu.flymeupdater.R;

import java.io.IOException;

/**
 * Created by GuaiYiHu on 16/6/22.
 */
public class AboutMe extends AppCompatActivity {

    private CardView weibo;
    private CardView QQ;
    private CardView aliPay;
    private ImageView ali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        weibo = (CardView)findViewById(R.id.weibo);
        QQ = (CardView)findViewById(R.id.QQ);
        aliPay = (CardView)findViewById(R.id.ali);


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

        aliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ali = new ImageView(AboutMe.this);
                ali.setImageResource(R.mipmap.erweima);
                android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(AboutMe.this);
                dialog.setTitle("欢迎捐赠 !");
                dialog.setView(ali);
                dialog.setCancelable(true);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which){
                        Toast.makeText(AboutMe.this, "谢谢您的支持 !", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });

    }

    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            Toast.makeText(AboutMe.this, "您当前没有安装QQ哦~", Toast.LENGTH_SHORT).show();
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
