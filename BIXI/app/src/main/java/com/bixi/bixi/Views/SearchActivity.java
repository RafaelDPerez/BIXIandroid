package com.bixi.bixi.Views;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bixi.bixi.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
    En estos momentos esta clase tiene datos dummy y esta realizada de una manera que sirva
    para mostrar datos de prueba.

    Queda pendiente cambiar la estructura de esta clase a MVP y asi vaya acorde a
    el resto del proyecto.

    9-02-2017
 */
public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.spinnerTipoEstablecimiento)
    Spinner tipoEstablecimiento;

    @BindView(R.id.spinnerUbicacion)
    Spinner spinnerUbicacion;

    @BindView(R.id.spinnerOrdernarPor)
    Spinner spinnerOrdenarPor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setUpSpinners();


    }

    /*
    Este medoto y la manera en como se cargar los spinner es para fines de prueba y demostracion.
     */
    private void setUpSpinners()
    {
        fillSpinnerEstablecimiento();
        fillSpinnerUbicacion();
        fillSpinnerOrdenarPor();
    }

    private void fillSpinnerOrdenarPor() {
        // Initializing a String Array
        String[] tipoEstable = new String[]{
                "ordenar por:",
                "NAME-ASC",
                "NAME-DESC",
                "DESCRIPTION-ASC",
                "DESCRIPTION-DESC",
                "TAG-ASC",
                "TAG-DESC",
                "POINT-ASC",
                "POINT-DESC",
        };

        final List<String> ordenarList = new ArrayList<>(Arrays.asList(tipoEstable));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_style,ordenarList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerOrdenarPor.setAdapter(spinnerArrayAdapter);
    }

    private void fillSpinnerUbicacion()
    {

        // Initializing a String Array
        String[] tipoEstable = new String[]{
                "ubicación",
        };

        final List<String> ubicacionList = new ArrayList<>(Arrays.asList(tipoEstable));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_style,ubicacionList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerUbicacion.setAdapter(spinnerArrayAdapter);
    }

    private void fillSpinnerEstablecimiento()
    {
        // Initializing a String Array
        String[] tipoEstable = new String[]{
                "tipo de establecimiento",
                "RESTAURANTE",
                "BAR"
        };

        final List<String> establecimientoList = new ArrayList<>(Arrays.asList(tipoEstable));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_style,establecimientoList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        tipoEstablecimiento.setAdapter(spinnerArrayAdapter);
    }

    @OnClick(R.id.imageSearchGoNextPage)
    void imgOnClick()
    {
        finish();
    }
}
