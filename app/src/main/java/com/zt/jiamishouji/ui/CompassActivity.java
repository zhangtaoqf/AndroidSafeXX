package com.zt.jiamishouji.ui;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.zt.jiamishouji.R;

public class CompassActivity extends AppCompatActivity implements SensorEventListener{

    // 定义显示指南针图片的组件
    private ImageView image;
    // 记录指南针图片转过的角度
    private float currentDegree = 0f;
    // 定义真机的Sensor管理器
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        image = (ImageView) findViewById(R.id.compass_imageView);
        // 获取真机的传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 为系统的方向传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 取消注册
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 如果真机上触发event的传感器类型为水平传感器类型
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            // 获取绕Z轴转过的角度
            float degree = event.values[0];
            // 创建旋转动画（反向转过degree度）
            RotateAnimation ra = new RotateAnimation(currentDegree, -degree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            // 设置动画的持续时间
            ra.setDuration(200);
            // 设置动画结束后的保留状态
            ra.setFillAfter(true);
            // 启动动画
            image.startAnimation(ra);
            currentDegree = -degree;
        }
    }
}
