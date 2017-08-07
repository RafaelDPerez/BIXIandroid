package com.bixi.bixi.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bixi.bixi.Interfaces.HomeProfilePresenter;
import com.bixi.bixi.Interfaces.HomeProfileView;
import com.bixi.bixi.Pojos.Result;
import com.bixi.bixi.Pojos.UserLogin;
import com.bixi.bixi.Presenter.HomeProfilePresenterImpl;
import com.bixi.bixi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileActivity extends AppCompatActivity implements HomeProfileView {

    @BindView(R.id.edtNombre)
    EditText edtNombre;

    @BindView(R.id.edtSegundoNombre)
    EditText edtSegundoNombre;

    @BindView(R.id.edtDocumentoDeIdentidad)
    EditText edtDocumentoDeIdentidad;

    @BindView(R.id.edtDireccion)
    EditText edtDireccion;

    @BindView(R.id.edtTelefono1)
    EditText edtTelefono1;

    @BindView(R.id.edtTelefono2)
    EditText edtTelefono2;

    @BindView(R.id.edtFechaDeNacimiento)
    EditText edtFechaNacimiento;

    @BindView(R.id.pbEditProfile)
    ProgressBar pbEditProgressBar;

    @BindView(R.id.imgAddUserGo)
    ImageView imgAddUserGo;

    private String token;
    private Result information;
    private HomeProfilePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        presenter = new HomeProfilePresenterImpl(this);

        Intent intent = this.getIntent();
        if(intent != null)
        {
            Bundle bundle = intent.getExtras();
            information= (Result) bundle.getSerializable("value");
            token = intent.getStringExtra("token");

            if(information != null)
                fillInfo();
        }

        edtFechaNacimiento.setFocusable(false);
        edtFechaNacimiento.setClickable(true);

    }

    private void fillInfo(){
        if(!information.getFirst_name().isEmpty())
            edtNombre.setText(information.getFirst_name());
        if(!information.getLast_name().isEmpty())
            edtSegundoNombre.setText(information.getLast_name());
        if(!information.getDocument_id().isEmpty())
            edtDocumentoDeIdentidad.setText(information.getDocument_id());
        if(!information.getAddress().isEmpty())
            edtDireccion.setText(information.getAddress());
        if(!information.getPhone1().isEmpty())
            edtTelefono1.setText(information.getPhone1());
        if(!information.getPhone2().isEmpty())
            edtTelefono2.setText(information.getPhone2());
        if(!information.getBirth_date().isEmpty())
            edtFechaNacimiento.setText(information.getBirth_date());
    }

    @OnClick(R.id.edtFechaDeNacimiento)
    void fechaNacimientoOnClic()
    {

    }

    @OnClick(R.id.imgAddUserGo)
    void updateInfo()
    {
        Result userInfor = new Result();
        userInfor.setFirst_name(edtNombre.getText().toString().trim());
        userInfor.setLast_name(edtSegundoNombre.getText().toString().trim());
        userInfor.setDocument_id(edtDocumentoDeIdentidad.getText().toString().trim());
        userInfor.setAddress(edtDireccion.getText().toString().trim());
        userInfor.setPhone1(edtTelefono1.getText().toString().trim());
        userInfor.setPhone2(edtTelefono1.getText().toString().trim());
        userInfor.setBirth_date(edtFechaNacimiento.getText().toString().trim());
        userInfor.setEmail(information.getEmail());
        presenter.updateUserInformationPicture(token,userInfor);

    }


    @Override
    public void operacionExitosa(UserLogin obj) {

    }

    @Override
    public void error() {

    }

    @Override
    public void showProgress() {
        pbEditProgressBar.setVisibility(View.VISIBLE);
        imgAddUserGo.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {

        pbEditProgressBar.setVisibility(View.INVISIBLE);
        imgAddUserGo.setVisibility(View.VISIBLE);

    }

    @Override
    public void actualizadoExitoso() {
        finish();
    }
}
