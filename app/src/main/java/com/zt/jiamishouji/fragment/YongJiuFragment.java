package com.zt.jiamishouji.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zt.jiamishouji.R;
import com.zt.jiamishouji.appconfig.AppConfig;
import com.zt.jiamishouji.ui.YongJiuSZActivity;
import com.zt.jiamishouji.util.MySharePreferenceUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class YongJiuFragment extends BaseFragment {

    private static final String[] COMMAND_SAFE={"","","","","","","",""};
    private LinearLayout mLinearLayout;
    private JiShiFragment.Callback callback;
    public YongJiuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof JiShiFragment.Callback)
        {
            callback = ((JiShiFragment.Callback) activity);
        }
    }

    public static YongJiuFragment newInstance() {
        YongJiuFragment fragment = new YongJiuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yong_jiu, container, false);
        mLinearLayout = ((LinearLayout) view.findViewById(R.id.yongjiu_linearLayoutId));
        setItemClickListener();
        return view;
    }

    private void setItemClickListener() {
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            View view = mLinearLayout.getChildAt(i);
            view.setTag(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    //处理每一个按钮的事件
                    dealWithPosition(position);
                }
            });
        }
    }

    private void dealWithPosition(int position) {

        
        View mimaFirst = LayoutInflater.from(getContext()).inflate(R.layout.edit_mima_first, null);
        final EditText mima1_first = (EditText) mimaFirst.findViewById(R.id.mima_first_edit1Id);
        final EditText mima2_first = (EditText) mimaFirst.findViewById(R.id.mima_first_edit2Id);

        View mima = LayoutInflater.from(getContext()).inflate(R.layout.edit_mima,null);
        final EditText mimaEt = (EditText) mima.findViewById(R.id.mima_editId);
        


        switch (position)
        {

            case 0:break;
            case 8:break;

            case 9:
                MySharePreferenceUtils utils = new MySharePreferenceUtils(AppConfig.YONGJIU_SHAREPREFERENCE, getActivity());
                String paramter = utils.getParamter(AppConfig.YONGJIU_MIMA);
                if(paramter.equals(""))
                {
                    new AlertDialog.Builder(getActivity()).setTitle("设置保护密码")
                            .setCancelable(true)
                            .setView(mimaFirst)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getActivity(), "已经取消", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String s1 = mima1_first.getText().toString().trim();
                                    String s2 = mima2_first.getText().toString().trim();
                                    if (s1.equals("") || s2.equals("")) {
                                        Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
                                    } else if (!s1.equals(s2)) {
                                        Toast.makeText(getActivity(), "密码不一致", Toast.LENGTH_SHORT).show();
                                    } else {
                                        MySharePreferenceUtils mySharePreferenceUtils = new MySharePreferenceUtils(AppConfig.YONGJIU_SHAREPREFERENCE,getContext());
                                        mySharePreferenceUtils.saveParamter(AppConfig.YONGJIU_MIMA, s1);
                                        //startActivity(new Intent(getActivity(), YongJiuSZActivity.class));
                                        callback.goToAc(YongJiuSZActivity.class);
                                    }

                                }
                            })
                            .create().show();
                }else
                {
                    new AlertDialog.Builder(getActivity()).setTitle("设置保护密码")
                            .setCancelable(true)
                            .setView(mima)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getActivity(), "已经取消", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MySharePreferenceUtils mySharePreferenceUtils = new MySharePreferenceUtils(AppConfig.YONGJIU_SHAREPREFERENCE,getContext());
                                    String s1 = mimaEt.getText().toString().trim();
                                    String s2 = mySharePreferenceUtils.getParamter(AppConfig.YONGJIU_MIMA);
                                    if (s1.equals("")) {
                                        Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
                                    } else if (!s1.equals(s2)) {
                                        Toast.makeText(getActivity(), "密码不正确", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //startActivity(new Intent(getActivity(), YongJiuSZActivity.class));
                                        callback.goToAc(YongJiuSZActivity.class);
                                    }
                                }
                            })
                            .create().show();
                }
                break;
            case 7:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                new AlertDialog.Builder(getActivity())
                        .setMessage("你需要短信输入:"+COMMAND_SAFE[position]+"命令，开启")
                        .setTitle("提示")
                        .create().show();
                break;
        }
    }


}
