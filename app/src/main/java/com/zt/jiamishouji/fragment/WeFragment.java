package com.zt.jiamishouji.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zt.jiamishouji.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeFragment extends BaseFragment {


    private TextView gongNengJj;
    private TextView lianxiWe;
    private TextView shengming;
    private TextView shuoming;

    public WeFragment() {
        // Required empty public constructor
    }

    public static WeFragment newInstance() {
        WeFragment fragment = new WeFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_we, container, false);
        //以下是软件帮助里面we的相关数据的填充
        gongNengJj = ((TextView) view.findViewById(R.id.we_gongnengjianjie));
        lianxiWe = ((TextView) view.findViewById(R.id.we_lianxiwomen));
        shengming = ((TextView) view.findViewById(R.id.we_shengming));
        shuoming = ((TextView) view.findViewById(R.id.we_shuoming));

        //设置内容的代码
        gongNengJj.setText(R.string.we_fragment_gongnengjj);
        shengming.setText(R.string.we_fragment_shengming);
        shuoming.setText(R.string.we_fragment_shiyongsm);
        lianxiWe.setText(R.string.we_fragment_lianxiwe);

        return view;
    }


}
