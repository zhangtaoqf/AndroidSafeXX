package com.zt.jiamishouji.ui;

import android.app.ProgressDialog;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.zt.jiamishouji.R;
import com.zt.jiamishouji.adapter.RVAdapter;
import com.zt.jiamishouji.bean.SensorEntity;

import java.util.ArrayList;

public class JianCActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RVAdapter adapter;
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            SensorEntity obj = (SensorEntity) msg.obj;
            adapter.add(obj);
            recyclerView.scrollTo(0,(int)y0);
            if(msg.what == 11)
            {
                progressDialog.dismiss();
                Toast.makeText(JianCActivity.this, "检测完毕", Toast.LENGTH_LONG).show();
            }

        }
    };
    private ProgressDialog progressDialog;
    private float y0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jian_c);

        this.setTitle("传感器检测");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = ((RecyclerView) findViewById(R.id.recyclerViewId));
        y0 = recyclerView.getY();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RVAdapter(new ArrayList<SensorEntity>(),this);
        recyclerView.setAdapter(adapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在拼命的检测中。。。");
        progressDialog.show();
        //加载数据
        loadData();
    }
    private void loadData() {

        SensorManager systemService = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor orientation = systemService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        SensorEntity sensorEntity1 = new SensorEntity();
        sensorEntity1.setName("方向传感器");
        if(orientation==null)
        {
            sensorEntity1.setIsValue(false);
        }else
        {
            sensorEntity1.setIsValue(true);
        }
        Message msg = Message.obtain();
        msg.obj = sensorEntity1;
        msg.what=12;
        handler.sendMessageDelayed(msg,1000);

        Sensor accler = systemService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEntity sensorEntity2 = new SensorEntity();
        sensorEntity2.setName("加速传感器");
        if(accler==null)
        {
            sensorEntity2.setIsValue(false);
        }else
        {
            sensorEntity2.setIsValue(true);
        }
        Message msg2 = Message.obtain();
        msg2.obj = sensorEntity2;
        msg2.what=12;
        handler.sendMessageDelayed(msg2, 2000);


        Sensor ambient = systemService.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        SensorEntity sensorEntity3 = new SensorEntity();
        sensorEntity3.setName("温度传感器");
        if(ambient==null)
        {
            sensorEntity3.setIsValue(false);
        }else
        {
            sensorEntity3.setIsValue(true);
        }

        Message msg3 = Message.obtain();
        msg3.obj = sensorEntity3;
        msg3.what=12;
        handler.sendMessageDelayed(msg3, 3000);
        Sensor gravity = systemService.getDefaultSensor(Sensor.TYPE_GRAVITY);
        SensorEntity sensorEntity4 = new SensorEntity();
        sensorEntity4.setName("重力传感器");
        if(gravity==null)
        {
            sensorEntity4.setIsValue(false);
        }else
        {
            sensorEntity4.setIsValue(true);
        }

        Message msg4 = Message.obtain();
        msg4.obj = sensorEntity4;
        msg4.what=12;
        handler.sendMessageDelayed(msg4, 4000);
        Sensor gyroscope = systemService.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        SensorEntity sensorEntity5 = new SensorEntity();
        sensorEntity5.setName("陀螺传感器");
        if(gyroscope==null)
        {
            sensorEntity5.setIsValue(false);
        }else
        {
            sensorEntity5.setIsValue(true);
        }
        Message msg5 = Message.obtain();
        msg5.obj = sensorEntity5;
        msg5.what=12;
        handler.sendMessageDelayed(msg5, 5000);


        Sensor light = systemService.getDefaultSensor(Sensor.TYPE_LIGHT);
        SensorEntity sensorEntity6 = new SensorEntity();
        sensorEntity6.setName("光线传感器");
        if(light==null)
        {
            sensorEntity6.setIsValue(false);
        }else
        {
            sensorEntity6.setIsValue(true);
        }
        Message msg6 = Message.obtain();
        msg6.obj = sensorEntity6;
        msg6.what=12;
        handler.sendMessageDelayed(msg6, 6000);



        Sensor magnetic = systemService.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        SensorEntity sensorEntity7 = new SensorEntity();
        sensorEntity7.setName("磁力传感器");
        if(magnetic==null)
        {
            sensorEntity7.setIsValue(false);
        }else
        {
            sensorEntity7.setIsValue(true);
        }
        Message msg7 = Message.obtain();
        msg7.obj = sensorEntity7;
        msg7.what=12;
        handler.sendMessageDelayed(msg7, 7000);

        Sensor pressure = systemService.getDefaultSensor(Sensor.TYPE_PRESSURE);
        SensorEntity sensorEntity8 = new SensorEntity();
        sensorEntity8.setName("压力传感器");
        if(pressure==null)
        {
            sensorEntity8.setIsValue(false);
        }else
        {
            sensorEntity8.setIsValue(true);
        }
        Message msg8 = Message.obtain();
        msg8.obj = sensorEntity8;
        msg8.what=12;
        handler.sendMessageDelayed(msg8, 8000);

        Sensor proximity = systemService.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        SensorEntity sensorEntity9 = new SensorEntity();
        sensorEntity9.setName("距离传感器");
        if(proximity==null)
        {
            sensorEntity9.setIsValue(false);
        }else
        {
            sensorEntity9.setIsValue(true);
        }
        Message msg9 = Message.obtain();
        msg9.obj = sensorEntity9;
        msg9.what=12;
        handler.sendMessageDelayed(msg9, 9000);

        Sensor relative_humidity = systemService.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        SensorEntity sensorEntity10 = new SensorEntity();
        sensorEntity10.setName("湿度传感器");
        if(relative_humidity==null)
        {
            sensorEntity10.setIsValue(false);
        }else
        {
            sensorEntity10.setIsValue(true);
        }
        Message msg10 = Message.obtain();
        msg10.obj = sensorEntity10;
        msg10.what=12;
        handler.sendMessageDelayed(msg10, 10000);

        Sensor roaation_vector = systemService.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        SensorEntity sensorEntity11 = new SensorEntity();
        sensorEntity11.setName("旋转适量传感器");
        if(roaation_vector==null)
        {
            sensorEntity11.setIsValue(false);
        }else
        {
            sensorEntity11.setIsValue(true);
        }
        Message msg11 = Message.obtain();
        msg11.obj = sensorEntity11;
        msg11.what=11;
        handler.sendMessageDelayed(msg11, 11000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.sendEmptyMessage(122);
    }


}
