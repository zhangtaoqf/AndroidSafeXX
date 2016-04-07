package com.zt.jiamishouji.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zt.jiamishouji.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends BaseFragment {


    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 newInstance() {

        Fragment1 fragment = new Fragment1();

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_fragment1, container, false);
    }


}
