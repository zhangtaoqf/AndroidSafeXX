package com.zt.jiamishouji.ui;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zt.jiamishouji.R;
import com.zt.jiamishouji.bean.MyContacts;
import com.zt.jiamishouji.fragment.JiShiFragment;
import com.zt.jiamishouji.fragment.SoftSMFragment;
import com.zt.jiamishouji.fragment.WeFragment;
import com.zt.jiamishouji.fragment.YongJiuFragment;
import com.zt.jiamishouji.receiver.LockReceiver;
import com.zt.jiamishouji.util.LoadContacts;

import java.util.List;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener ,JiShiFragment.Callback{

    private ImageView imageView;
    private TextView loginState;
    private FragmentManager fragmentManager;
    private ComponentName componentName;
    private DevicePolicyManager policyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater.from(this).inflate(R.layout.activity_main, null);

        setContentView(R.layout.activity_main);



        //开启用户管理器权限
        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, LockReceiver.class);

        if (!policyManager.isAdminActive(componentName)) {
            activeManager();//激活设备管理器获取权限
        }else
        {
            //激活锁屏权限
            policyManager.lockNow();
        }

        if(ContextCompat.checkSelfPermission(this, "android.permission.CAMERA")==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,new String[]{"android.permission.CAMERA"},0);
        }


        new LoadContacts(this, new LoadContacts.CallbackContacts() {
            @Override
            public void getContacts(List<MyContacts> result) {
                Log.i("info",result.toString());
            }
        }).execute();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        //注册登录按钮
        imageView = ((ImageView) headerView.findViewById(R.id.main_login));
        loginState = ((TextView) headerView.findViewById(R.id.main_loginstate));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), 0);
            }
        });


        fragmentManager = getSupportFragmentManager();

        //显示默认界面
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_containerId,SoftSMFragment.newInstance()).commit();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            Toast.makeText(this,"权限没有申请成功",Toast.LENGTH_SHORT).show();
        }
    }

    private void activeManager() {
        //使用隐式意图调用系统方法来激活指定的设备管理器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "我的App");
        startActivity(intent);
    }


    //处理注册的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (id == R.id.nav_jishi) {
            setTitle("即时防盗");
            //即时防盗页面
            transaction.replace(R.id.main_containerId, JiShiFragment.newInstance());

        } else if (id == R.id.nav_yongjiu){
            setTitle("永久防盗");
            //永久防盗界面
            transaction.replace(R.id.main_containerId, YongJiuFragment.newInstance());
        } else if (id == R.id.nav_shuoming) {
            setTitle("软件说明");
            transaction.replace(R.id.main_containerId, SoftSMFragment.newInstance());
        } else if (id == R.id.nav_we) {
            setTitle("我们");
            transaction.replace(R.id.main_containerId, WeFragment.newInstance());
        }

        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void goToAc(Class<?> cls) {
        /*if(Integer.valueOf(android.os.Build.VERSION.SDK)>20)
        {
            startActivity(new Intent(this,cls), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        else
            startActivity(new Intent(this,cls));*/
        startActivity(new Intent(this,cls));
    }
}
