package com.bixi.bixi.Views;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.bixi.bixi.bixi.basics.ApplyCustomFont;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPointsFragment extends Fragment {


    @BindView(R.id.edtAddPoints)
    EditText addPoints;

    @BindView(R.id.edtAddPoints_descripcion)
    EditText addPointsDescripcion;

    private static AddPointsFragment instance;
    private View view;
    private String token;

    public AddPointsFragment() {
        // Required empty public constructor
    }

    public static AddPointsFragment getInstance()
    {
        if(instance == null)
            instance = new AddPointsFragment();

        return instance;
    }

    private void loadView(LayoutInflater inflater, ViewGroup container) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_add_points, container, false);
        ApplyCustomFont.applyFont(getInstance().getActivity(),view.findViewById(R.id.fragment_add_points_id),"fonts/Corbel.ttf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loadView(inflater,container);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null) {
            token = extras.getString(Constants.extraToken);
        }

    }

    @OnClick(R.id.btnAddPoints)
    void btnAddPoints()
    {
        if(addPoints.getText().toString().trim() != null && !addPoints.getText().toString().trim().equals("") && addPointsDescripcion.getText().toString().trim() != null && !addPointsDescripcion.getText().toString().trim().equals(""))
        {
            Intent i = new Intent(getActivity(), PasswordActivity.class);
            Bundle bundle = new Bundle();
            i.putExtra("addPoints",true);
            i.putExtra("points",addPoints.getText().toString().trim());
            i.putExtra("addPointsDescripcion",addPointsDescripcion.getText().toString().trim());
            i.putExtra(Constants.extraToken,token);
            startActivity(i);
        }else
        {
            Snackbar.make(view,"Debe completar los campos",Snackbar.LENGTH_SHORT).show();
        }
    }
}
