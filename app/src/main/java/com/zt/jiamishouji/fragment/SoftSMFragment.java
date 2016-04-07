package com.zt.jiamishouji.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zt.jiamishouji.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SoftSMFragment extends BaseFragment {


    public SoftSMFragment() {
        // Required empty public constructor
    }

    public static SoftSMFragment newInstance() {

        SoftSMFragment fragment = new SoftSMFragment();

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_soft_sm, container, false);
    }


}
