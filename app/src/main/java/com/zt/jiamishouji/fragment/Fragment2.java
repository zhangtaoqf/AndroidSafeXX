package com.zt.jiamishouji.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zt.jiamishouji.R;


public class Fragment2 extends BaseFragment {

    private EditText editNum;

    public static Fragment2 newInstance() {
        Fragment2 fragment = new Fragment2();
        return fragment;
    }

    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        editNum = ((EditText) view.findViewById(R.id.fragment2_phoneNumberId));
        return view;
    }

    public String getContent()
    {
        return editNum.getText().toString().trim();
    }

}
