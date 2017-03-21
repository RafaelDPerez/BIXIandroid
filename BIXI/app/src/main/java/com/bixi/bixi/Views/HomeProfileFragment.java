package com.bixi.bixi.Views;


import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bixi.bixi.Login;
import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.bixi.bixi.homeDrawable;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeProfileFragment extends Fragment {


    private static HomeProfileFragment instance;
    private View view;
    private ImageButton imageButtonCamera;
    private Button btnResetPassword;

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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageButtonCamera = (ImageButton) view.findViewById(R.id.imageButtonCamera_profile);
        btnResetPassword = (Button) view.findViewById(R.id.btnResetPassword);
        buttonOnClick();
    }



    private void buttonOnClick()
    {
        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), imageButtonCamera);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_camera, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        
                        return true;
                    }
                });

                popup.show();//showing popup menu

            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ResetPasswordActivity.class);
                startActivity(i);
            }
        });
    }
}
