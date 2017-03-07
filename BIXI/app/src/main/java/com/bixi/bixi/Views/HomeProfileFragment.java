package com.bixi.bixi.Views;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bixi.bixi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeProfileFragment extends Fragment {


    private static HomeProfileFragment instance;
    private View view;
    public HomeProfileFragment() {
        // Required empty public constructor
    }

    public static HomeProfileFragment getInstance()
    {
        if(instance == null)
            instance = new HomeProfileFragment();

        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadView(inflater,container);
        return view;
    }

    private void loadView(LayoutInflater inflater, ViewGroup container) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.user_profile_layout, container, false);

    }


}
