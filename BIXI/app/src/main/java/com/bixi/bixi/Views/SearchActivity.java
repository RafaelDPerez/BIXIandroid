package com.bixi.bixi.Views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bixi.bixi.Interfaces.SearchActivityPresenter;
import com.bixi.bixi.Interfaces.SearchActivityView;
import com.bixi.bixi.Pojos.Combos.ComboGeneralPojo;
import com.bixi.bixi.Pojos.Combos.ComboPojo;
import com.bixi.bixi.Presenter.SearchActivityPresenterImpl;
import com.bixi.bixi.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
    En estos momentos esta clase tiene datos dummy y esta realizada de una manera que sirve
    para mostrar datos de prueba.

    Queda pendiente cambiar la estructura de esta clase a MVP y asi vaya acorde a
    el resto del proyecto.

    9-02-2017
 */
public class SearchActivity extends AppCompatActivity implements SearchActivityView {

    @BindView(R.id.spinnerTipoEstablecimiento)
    Spinner tipoEstablecimiento;

    @BindView(R.id.spinnerUbicacion)
    Spinner spinnerUbicacion;

    @BindView(R.id.spinnerOrdernarPor)
    Spinner spinnerOrdenarPor;

    @BindView(R.id.seekBar)
    SeekBar seekBar;

    @BindView(R.id.textView5)
    TextView tvSeekBarMax;

    @BindView(R.id.edtSearch)
    EditText edtSearch;

    private SearchActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        overridePendingTransition(0, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }
        ButterKnife.bind(this);
        presenter = new SearchActivityPresenterImpl(this);
        setUpSpinners();
        seekBarProgress();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void seekBarProgress() {
        seekBar.setProgress(300);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                if(progress <= 10)
                    tvSeekBarMax.setText("10");
                else
                    tvSeekBarMax.setText(String.valueOf(progress));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                tvSeekBarMax.setText(tvSeekBarMax.getText()+"B");
            }
        });
    }


    /*
    Este medoto y la manera en como se cargar los spinner es para fines de prueba y demostracion.
     */
    private void setUpSpinners()
    {
        //Inicializamos el spinner a un valor por defecto.
        ComboGeneralPojo pojo = new ComboGeneralPojo();
        ComboPojo combo = new ComboPojo();
        combo.setTypeCommerceId("-999");
        combo.setTypeCommerce("Establecimiento");
        ArrayList<ComboPojo> arrayCombo = new ArrayList<ComboPojo>();
        arrayCombo.add(combo);
        pojo.setComboPojo(arrayCombo);
        setArrayAdapter(pojo);


        presenter.loadTypeCommerceList();
      //  fillSpinnerEstablecimiento();
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
                "Oferta",
                "SI",
                "NO",
                "ULTIMO MINUTO"
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
                    return true;
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


    }

    @OnClick(R.id.imageSearchGoNextPage)
    void imgOnClick()
    {
        String search = edtSearch.getText().toString().trim();
        ComboPojo combo = (ComboPojo) tipoEstablecimiento.getSelectedItem();
        String ubicacionId = "";
        if(combo != null && combo.getTypeCommerceId() != null && !combo.getTypeCommerceId().equals(-999) && !combo.getTypeCommerceId().equals(""))
        {
            ubicacionId = combo.getTypeCommerceId();
        }
        String odernarTemp = (String) spinnerOrdenarPor.getSelectedItem();
        String ordernar = "";
        if(odernarTemp != null && !odernarTemp.equals("") && !odernarTemp.equals("ordenar por:"))
        {
            ordernar = odernarTemp;
        }
        String offerTemp = (String) spinnerUbicacion.getSelectedItem();
        String isOffer = "";
        if(offerTemp != null && !offerTemp.equals("") && !offerTemp.equals("Oferta"))
        {
            isOffer = offerTemp;
        }

        int pointFrom = 10;
        int pointTo = seekBar.getProgress();
        if(pointTo <= 10)
            pointTo = 10;

        Intent returnIntent = new Intent();
        returnIntent.putExtra("search",search);
        returnIntent.putExtra("ubicacionId",ubicacionId);
        returnIntent.putExtra("ordenar",ordernar);
        returnIntent.putExtra("isOffer",isOffer);
        returnIntent.putExtra("pointFrom",pointFrom);
        returnIntent.putExtra("pointTo",pointTo);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public void operacionExitosa(ComboGeneralPojo comboGeneralPojo) {
        // Initializing an ArrayAdapter
        setArrayAdapter(comboGeneralPojo);

    }

    private void setArrayAdapter(ComboGeneralPojo comboGeneralPojo)
    {
        ComboPojo pojo = new ComboPojo();
        pojo.setTypeCommerceId("-999");
        pojo.setTypeCommerce("Establecimiento");
        comboGeneralPojo.getComboPojo().add(0,pojo);
        final ArrayAdapter<ComboPojo> spinnerArrayAdapter = new ArrayAdapter<ComboPojo>(
                this,R.layout.spinner_style,comboGeneralPojo.getComboPojo()){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
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

    @Override
    public void error(String error) {

    }
}
