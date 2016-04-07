package com.zt.jiamishouji.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zt.jiamishouji.MyApp;
import com.zt.jiamishouji.R;
import com.zt.jiamishouji.ui.CompassActivity;
import com.zt.jiamishouji.ui.JianCActivity;
import com.zt.jiamishouji.ui.OnJishiActivity;
import com.zt.jiamishouji.ui.YaoYiYaoActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class JiShiFragment extends BaseFragment {

    private boolean isOn=false;
    public JiShiFragment() {
        // Required empty public constructor
    }

    private Callback callback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity  instanceof Callback)
        {
            callback = ((Callback) activity);
        }
    }

    public static JiShiFragment newInstance() {

        JiShiFragment fragment = new JiShiFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ji_shi, container, false);
        //传感器监听
        view.findViewById(R.id.jishi_chuanganqiBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), JianCActivity.class));
                callback.goToAc(JianCActivity.class);
            }
        });
        //设置监听
        view.findViewById(R.id.jishi_shezhiBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApp.getApp().isOn())
                {
                    Toast.makeText(getActivity(),"已经开启",Toast.LENGTH_SHORT).show();
                }else
                {
//                    startActivity(new Intent(getActivity(), OnJishiActivity.class));
                    callback.goToAc(OnJishiActivity.class);
                }

            }
        });

        //设置帮助
        view.findViewById(R.id.jishi_bangzhuBtnId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(),CompassActivity.class));
                callback.goToAc(CompassActivity.class);
            }
        });

        view.findViewById(R.id.jishi_shupinyiBtnId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.goToAc(YaoYiYaoActivity.class);
            }
        });

        return view;
    }

    public interface Callback
    {
        public void goToAc(Class<?> cls);
    }

}
