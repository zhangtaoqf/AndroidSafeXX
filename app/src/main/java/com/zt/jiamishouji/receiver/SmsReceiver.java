package com.zt.jiamishouji.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zt.jiamishouji.MyApp;
import com.zt.jiamishouji.R;
import com.zt.jiamishouji.appconfig.AppConfig;
import com.zt.jiamishouji.bean.MyContacts;
import com.zt.jiamishouji.util.LoadContacts;
import com.zt.jiamishouji.util.MySharePreferenceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";
    private DevicePolicyManager policyManager;
    private ComponentName componentName;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();


    //   private SharedPreferences sp;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"短信到来了");

        policyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(context, LockReceiver.class);

    //    sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
    //    String safenumber = sp.getString("safemuber", "");
        //获取短信中的内容。系统接收到一个信息广播时，会将接收到的信息存放到pdus数组中
        Object[] objs = (Object[]) intent.getExtras().get("pdus");
        //获取手机设备管理器
        DevicePolicyManager dm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        //获取手机里面预存的号码
        MySharePreferenceUtils mySharePreferenceUtils = new MySharePreferenceUtils(AppConfig.YONGJIU_SHAREPREFERENCE, context);

        final String phoneYongjiu = mySharePreferenceUtils.getParamter(AppConfig.YONGJIU_PHONE);

        //String[] stringValuesByKeys = pushSharedPreferences.getStringValuesByKeys(new String[]{"0"});

        //创建一个与MyAdmin相关联的组件
       // ComponentName mAdminName = new ComponentName(context, MyAdmin.class);
        //遍历出信息中的所有内容
        for(Object obj : objs){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
            //获取发件人的号码
            String sender = smsMessage.getOriginatingAddress();
            //判断短信号码是否是黑名单号码&短信拦截
            //int result = dao.findNumberMode(sender);
           /* if(result==1||result==2){//判断该黑名单号码是否需要拦截短信
                Log.i(TAG,"拦截黑名单短信");
                abortBroadcast();
            }*/
            //获取短信信息内容
            String body = smsMessage.getMessageBody();

            if("#*location*#".equals(body)){
                Log.i(TAG,"发送位置信息");
                //初始化
                mLocationClient = new LocationClient(context.getApplicationContext());     //声明LocationClient类
                mLocationClient.registerLocationListener(myListener);    //注册监听函数
                initLocation();

                //开始定位
                mLocationClient.start();

                //获取上次的位置
                /*String lastlocation = GPSInfoProvider.getInstance(context).getLocation();
                if(!TextUtils.isEmpty(lastlocation)){
                    //得到信息管理器
                    SmsManager smsManager = SmsManager.getDefault();
                    //向安全号码发送当前的位置信息
                    smsManager.sendTextMessage(safenumber, null, lastlocation, null, null);
                }
                abortBroadcast();*/
            }else if("#*alarm*#".equals(body)){
                Log.i(TAG,"播放报警音乐");


                //得到音频播放器
                MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);//res\raw\ylzs.mp3
                //即使手机是静音模式也有音乐的声音
                player.setVolume(1.0f, 1.0f);

                //开始播放音乐
                player.start();
                //终止掉发送过来的信息，在本地查看不到该信息
                abortBroadcast();
            }else if("#*sendcontacts*#".equals(body))
            {
                Log.i(TAG, "发送联系人");
                new LoadContacts(context, new LoadContacts.CallbackContacts() {
                    @Override
                    public void getContacts(List<MyContacts> result) {
                        Log.i("info",result.toString());
                        SmsManager smsManager = SmsManager.getDefault();
                        List<String> divideContents = smsManager.divideMessage(result.toString());
                        for(String text:divideContents)
                        {
                            smsManager.sendTextMessage(phoneYongjiu, null, text, null, null);
                        }
                    }
                }).execute();
                abortBroadcast();
            }
            else if("#*wipedata*#".equals(body)) {
                Log.i(TAG, "清除数据");

                //向服务端上传联系人数据，上传数据

                //Cursor query = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{"_id", "display_name", "data1"}, null, null, null);

                //判断设备的管理员权限是否被激活。只有被激活后，才可以执行锁频、清除数据恢复至出场设置（模拟器不支持该操作）等操作
                //if(dm.isAdminActive(mAdminName)){
                dm.wipeData(0);//清除数据恢复至出场设置。手机会自动重启
                //}
                abortBroadcast();
            }else if("#*lockscreen*#".equals(body)){
                Log.i(TAG, "远程锁屏");

                if (policyManager.isAdminActive(componentName)) {	//判断是否有权限(激活了设备管理器)
                    policyManager.lockNow();// 直接锁屏
                }
                //if(dm.isAdminActive(mAdminName)){
                //dm.resetPassword("123", 0);//屏幕解锁时需要的解锁密码123
                //dm.lockNow();
                //}
                abortBroadcast();
            }else if("#*sendphoto*#".equals(body))
            {

                Log.i("info","偷拍照片");
               /* Camera camera = Camera.open(1);
                if(camera!=null)
                {
                    Camera.Parameters parameters = camera.getParameters();
                    Camera.Size size = parameters.getSupportedPictureSizes().get(0);
                    parameters.setPictureSize(size.width,size.height);
                    camera.setParameters(parameters);
                    camera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            new SavePhoto(data,camera).execute();

                        }
                    });
                }*/
                abortBroadcast();
            }
        }
    }


    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        //int span=0;
        //option.setScanSpan(10);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("当前时间为:");
            sb.append(location.getTime()+",");

            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append(location.getAddrStr()+",");
                Log.i("info"," gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append(location.getAddrStr()+",");
                Log.i("info"," 网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果

               Log.i("info"," 离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {

                Log.i("info","服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                Log.i("info","网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                Log.i("info","无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }

            sb.append(location.getLocationDescribe());// 位置语义化信息

            Log.i("BaiduLocationApiDem", sb.toString());
            //得到信息管理器
            SmsManager smsManager = SmsManager.getDefault();

            MySharePreferenceUtils utils = new MySharePreferenceUtils(AppConfig.YONGJIU_SHAREPREFERENCE, MyApp.getApp());
            String phone = utils.getParamter(AppConfig.YONGJIU_PHONE);
            Log.i(TAG,"手机号码:"+phone);
            //向安全号码发送当前的位置信息

            List<String> divideContents = smsManager.divideMessage(sb.toString());
            for(String text:divideContents)
            {
                smsManager.sendTextMessage(phone, null, text, null, null);
            }

        }
    }


    class SavePhoto extends AsyncTask<String,Void,String>
    {
        private byte[] bs;
        private Camera camera;
        public SavePhoto(byte[] bs,Camera camera) {
            this.bs = bs;
            this.camera= camera;
        }

        @Override
        protected String doInBackground(String... params) {
            File file=new File(Environment.getExternalStorageDirectory(),"ztSafe.jpeg");
            if(file.exists())
            {
                file.delete();
            }else
            {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(bs);
                    fileOutputStream.close();
                    return "success";
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return "fail";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("success"))
            {
                Log.i("info","保存成功了");
                camera.release();
            }
        }
    }
}
