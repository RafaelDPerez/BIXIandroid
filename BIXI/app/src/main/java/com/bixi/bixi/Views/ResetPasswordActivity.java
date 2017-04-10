package com.bixi.bixi.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.bixi.bixi.Interfaces.GeneralInterface;
import com.bixi.bixi.Interfaces.HomeView;
import com.bixi.bixi.Interfaces.ResetPasswordPresenter;
import com.bixi.bixi.Pojos.Result;
import com.bixi.bixi.Presenter.ResetPasswordPresenterImpl;
import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.bixi.bixi.bixi.basics.ApplyCustomFont;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends AppCompatActivity implements GeneralInterface {

    @BindView(R.id.edtOldPassword)
    EditText oldPass;

    @BindView(R.id.edtNewPassword)
    EditText newPass;

    @BindView(R.id.edtNewPasswordCornf)
    EditText newPassCornf;

    private Result information;
    private ResetPasswordPresenter presenter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }
        inicializaciones();
        getExtras();

        ApplyCustomFont.applyFont(this,findViewById(R.id.reset_password_id),"fonts/Corbel.ttf");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void getExtras()
    {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            information = (Result) bundle.getSerializable("user_info");
            token = bundle.getString(Constants.extraToken);
        }

    }

    private void inicializaciones()
    {
        ButterKnife.bind(this);
        presenter = new ResetPasswordPresenterImpl(this);
    }

    @OnClick(R.id.imgGoRight)
    void actualizar()
    {
        presenter.updateUserInformation(token,
                information,
                oldPass.getText().toString(),
                newPass.getText().toString(),
                newPassCornf.getText().toString());
    }

    @OnClick(R.id.imgGoLeft)
    void goBack()
    {
        finish();
    }

    @Override
    public void exito(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(msg);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        builder1.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void error(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }
}
