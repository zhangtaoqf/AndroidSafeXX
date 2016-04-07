package com.zt.jiamishouji.ui;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.zt.jiamishouji.R;

public class YaoYiYaoActivity extends BaseActivity {


    private ImageView imageView_main_logoup;
    private ImageView imageView_main_logodown;
    private SensorManager sensorManager;
    private Vibrator mVibrator = null;
    private SoundPool soundPool = null;
    private int soundId = 0;
    private SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float valueX = event.values[0];
            float valueY = event.values[1];
            float valueZ = event.values[2];
            if (valueX > 10 || valueY > 10 || valueZ > 10) {
                startAnimator();
                startVibrator();
                startSound();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yao_yi_yao);

        initView();

        initSensor();

        initVibrator();

        initSoundPool();
    }

    private void initView() {
        imageView_main_logoup = (ImageView) findViewById(R.id.imageView_main_logoup);
        imageView_main_logodown = (ImageView) findViewById(R.id.imageView_main_logodown);
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void initVibrator() {
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void initSoundPool() {
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(5);// 传入音频数量
            // AudioAttributes是一个封装音频各种属性的方法
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);// 设置音频流的合适的属性
            builder.setAudioAttributes(attrBuilder.build());// 加载一个AudioAttributes
            soundPool = builder.build();
        } else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        // 往声音池中放置声音文件
        soundId = soundPool.load(this, R.raw.awe, 1);
    }

    private void startAnimator() {
        // 上部分图片的动画效果
        AnimationSet animatorSetUp = new AnimationSet(true);
        TranslateAnimation animationUp0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                -1.0f);
        animationUp0.setDuration(1000);

        TranslateAnimation animationUp1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
                0);
        animationUp1.setDuration(1000);
        animationUp0.setStartOffset(500);

        animatorSetUp.addAnimation(animationUp0);
        animatorSetUp.addAnimation(animationUp1);
        imageView_main_logoup.startAnimation(animatorSetUp);

        // 下部分图片的动画效果
        AnimationSet animatorSetDown = new AnimationSet(true);
        TranslateAnimation animationDown0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                +1.0f);
        animationDown0.setDuration(1000);
        animationDown0.setStartOffset(500);

        TranslateAnimation animationDown1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
        animationDown1.setDuration(1000);

        animatorSetDown.addAnimation(animationDown0);
        animatorSetDown.addAnimation(animationDown1);
        imageView_main_logodown.startAnimation(animatorSetDown);

    }

    private void startVibrator() {
        // 定义震动
        // 只有1个参数的时候，第一个参数用来指定振动的毫秒数。
        // 要传递2个参数的时候，第1个参数用来指定振动时间的样本，第2个参数用来指定是否需要循环，-1为不重复，非-1则从pattern的指定下标开始重复
        // 振动时间的样本是指振动时间和等待时间的交互指定的数组，即震动节奏数组。
        // ※下面的例子，在程序起动后等待3秒后，振动1秒，再等待2秒后，振动5秒，再等待3秒后，振动1秒
        // long[] pattern = {3000, 1000, 2000, 5000, 3000, 1000};
        // 震动节奏分别为：OFF/ON/OFF/ON…
        mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1);
    }

    private void startSound() {
        soundPool.play(soundId, 1, 1, 1, 0, 1);
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
