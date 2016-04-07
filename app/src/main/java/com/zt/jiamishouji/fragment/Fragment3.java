package com.zt.jiamishouji.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zt.jiamishouji.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends BaseFragment {


    private CallbackFragment3 callbackFragment3;

    public Fragment3() {
        // Required empty public constructor
    }

    public static Fragment3 newInstance() {
        Fragment3 fragment = new Fragment3();
        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof CallbackFragment3)
        {
            callbackFragment3 = ((CallbackFragment3) activity);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);

        //退出界面的按钮
        view.findViewById(R.id.fragment3_btnId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackFragment3.closeCurAcitivity();
            }
        });
        return view;
    }

    public interface CallbackFragment3
    {
        public void closeCurAcitivity();
    }


}
