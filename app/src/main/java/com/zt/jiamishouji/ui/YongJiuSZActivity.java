package com.zt.jiamishouji.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zt.jiamishouji.R;
import com.zt.jiamishouji.appconfig.AppConfig;
import com.zt.jiamishouji.fragment.BaseFragment;
import com.zt.jiamishouji.fragment.Fragment1;
import com.zt.jiamishouji.fragment.Fragment2;
import com.zt.jiamishouji.fragment.Fragment3;
import com.zt.jiamishouji.util.MySharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class YongJiuSZActivity extends AppCompatActivity implements View.OnClickListener,Fragment3.CallbackFragment3 {

    private Button shangBtn;
    private Button xiaBtn;
    private int curPage;
    private List<BaseFragment> fragments;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yong_jiu_sz);
        shangBtn = ((Button) findViewById(R.id.yongjiu_sz_btnShangId));
        xiaBtn = ((Button) findViewById(R.id.yongjiu_sz_btnXiaId));
        shangBtn.setOnClickListener(this);
        xiaBtn.setOnClickListener(this);

        //显示默认页
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.yongjiushezhi_containerId,Fragment1.newInstance()).commit();

        curPage=0;
        //实例化fragment
        initFragments();
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(Fragment1.newInstance());
        fragments.add(Fragment2.newInstance());
        fragments.add(Fragment3.newInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //上一页
            case R.id.yongjiu_sz_btnShangId:
                if(curPage==0)
                {
                    Toast.makeText(YongJiuSZActivity.this,"没有上一页了",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    curPage--;
                    showCurPage();
                }
                break;
            //下一页
            case R.id.yongjiu_sz_btnXiaId:
                if(curPage==fragments.size()-1)
                {
                    Toast.makeText(YongJiuSZActivity.this,"没有下一页了",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    if(curPage==1)
                    {
                        if("".equals(((Fragment2) fragments.get(curPage)).getContent()))
                        {
                            Toast.makeText(YongJiuSZActivity.this, "输入的号码不能为空", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            //保存该电话号码
                            String phoneNums = ((Fragment2) fragments.get(curPage)).getContent();
                            MySharePreferenceUtils sharePreferenceUtils = new MySharePreferenceUtils(AppConfig.YONGJIU_SHAREPREFERENCE, this);
                            sharePreferenceUtils.saveParamter(AppConfig.YONGJIU_PHONE,phoneNums);
                            //切换下一页
                            curPage++;
                            showCurPage();
                        }
                    }else {
                        curPage++;
                        showCurPage();
                    }
                }
                break;
        }
    }

    private void showCurPage() {
        fragmentManager.beginTransaction().replace(R.id.yongjiushezhi_containerId,fragments.get(curPage)).commit();
    }

    @Override
    public void closeCurAcitivity() {
        finish();
    }
}
