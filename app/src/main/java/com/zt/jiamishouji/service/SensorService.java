package com.zt.jiamishouji.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.zt.jiamishouji.R;

/**
 * Created by Administrator on 2016/2/27 0027.
 */
public class SensorService extends Service implements SensorEventListener {

    private MediaPlayer mediaPlayer;
    private SensorManager systemSensor;
    private Sensor lightSensor;
    private Sensor accelSensor;
    private float valueLight;

    @Override
    public void onCreate() {
        mediaPlayer = MediaPlayer.create(this, R.raw.ylzs);
        super.onCreate();
        systemSensor = (SensorManager) getSystemService(SENSOR_SERVICE);
        //光线传感器
        lightSensor = systemSensor.getDefaultSensor(Sensor.TYPE_LIGHT);
        //加速传感器
        accelSensor = systemSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //注册传感器监听

        //加速传感器监听
        systemSensor.registerListener(this,accelSensor,SensorManager.SENSOR_DELAY_UI);
        //光线传感器监听
        systemSensor.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_UI);

        return super.onStartCommand(intent, Service.START_STICKY, startId);
    }

    @Override
    public void onDestroy() {
        systemSensor.unregisterListener(this);
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        switch (sensor.getType())
        {
            case Sensor.TYPE_LIGHT:
                float[] values = event.values;
                valueLight = values[0];
                Log.i("info","光线:"+valueLight+"");
                break;
            case Sensor.TYPE_ACCELEROMETER:
                float[] values1 = event.values;
                /*Log.i("info","加速:X:"+values1[0]+"");
                Log.i("info","加速:Y:"+values1[1]+"");
                Log.i("info","加速:Z:"+values1[2]+"");*/
                acceleDeal(values1);
                break;
        }
    }

    private void acceleDeal(float[] values) {
        float valueX = values[0];
        float valueY = values[1];
        float valueZ = values[2];
        if (valueX > 10 || valueY > 10 || valueZ > 10) {
            if(valueLight>5)
            {
                if(!mediaPlayer.isPlaying())
                {

                    mediaPlayer.setVolume(1.0f,1.0f);
                    mediaPlayer.start();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
