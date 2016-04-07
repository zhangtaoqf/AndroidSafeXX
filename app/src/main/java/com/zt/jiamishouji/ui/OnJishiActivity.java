package com.zt.jiamishouji.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zt.jiamishouji.MyApp;
import com.zt.jiamishouji.R;
import com.zt.jiamishouji.service.SensorService;

public class OnJishiActivity extends BaseActivity {

    private ImageView imageView;
    private boolean isTrue=true;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_jishi);
        imageView = ((ImageView) findViewById(R.id.on_imageViewId));
        intent = new Intent(OnJishiActivity.this, SensorService.class);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag ;
                if(isTrue)
                {
                    imageView.setImageResource(R.mipmap.btn_off_bg);
                    isTrue = false;
                    Toast.makeText(OnJishiActivity.this, "已经开启及时防盗", Toast.LENGTH_SHORT).show();
                    startService(intent);
                    flag = true;
                }else
                {
                    imageView.setImageResource(R.mipmap.btn_on_bg);
                    isTrue = true;
                    Toast.makeText(OnJishiActivity.this, "已经关闭及时防盗", Toast.LENGTH_SHORT).show();
                    stopService(intent);
                    flag = false;
                }
                //设置标记
                MyApp.getApp().setIsOn(flag);
            }
        });
    }
}
