package com.bixi.bixi.Views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ViewFlipper;

import com.bixi.bixi.Interfaces.AddUserPresenter;
import com.bixi.bixi.Interfaces.AddUserView;
import com.bixi.bixi.Network.Injector;
import com.bixi.bixi.Pojos.UserCreate;
import com.bixi.bixi.Presenter.AddUserPresenterImpl;
import com.bixi.bixi.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddUserActivity extends AppCompatActivity implements AddUserView {

    @BindView(R.id.vf)
    ViewFlipper vf;

    @BindView(R.id.edtNombre)
    EditText edtNombre;

    @BindView(R.id.edtEdad)
    EditText edtEdad;

    @BindView(R.id.edtSexo)
    EditText edtSexo;

    @BindView(R.id.edtMovil)
    EditText edtMovil;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.edtPasswordConfirmar)
    EditText getEdtPasswordConfirmar;

    @BindView(R.id.edtApellido)
    EditText edtApellido;

    @BindView(R.id.edtCedula)
    EditText edtCedula;

    @BindView(R.id.progressBar2)
    ProgressBar progressBar;

    @BindView(R.id.imgAddUserGo2)
    ImageView go2;
    AddUserPresenter presenter;
    int year, month,day;
    private int DIALOG_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        inicializaciones();
        edtEdad.setFocusable(false);
        edtEdad.setClickable(true);
    }

    private void inicializaciones()
    {
        ButterKnife.bind(this);
        presenter = new AddUserPresenterImpl(this, Injector.provideCreateUserService());
    }

    @OnClick(R.id.imgAddUserGo)
    void nextPage()
    {
        vf.setDisplayedChild(1);
    }

    @OnClick(R.id.imgAddUserGo2)
    void nextPage2()
    {
        UserCreate userCreate = new UserCreate();
        String nombre = getEditTextStringFormatted(edtNombre);
        String edad = getEditTextStringFormatted(edtEdad);
        String sexo = getEditTextStringFormatted(edtSexo);
        String movil = getEditTextStringFormatted(edtMovil);
        String email = getEditTextStringFormatted(edtEmail);
        String password = getEditTextStringFormatted(edtPassword);
        String passwordConfirmar = getEditTextStringFormatted(getEdtPasswordConfirmar);
        userCreate.setFirst_name(nombre);
        userCreate.setBirth_date(edad);
        userCreate.setGender(sexo);
        userCreate.setPhone1(movil);
        userCreate.setEmail(email);
        userCreate.setEmail_confirm(email);
        userCreate.setPassword(password);
        userCreate.setPassword_confirm(passwordConfirmar);
        userCreate.setLast_name(getEditTextStringFormatted(edtApellido));
        userCreate.setDocument_id(getEditTextStringFormatted(edtCedula));
      //  presenter.createUser(nombre, edad, sexo, movil, email, password, passwordConfirmar);
        presenter.createuser(userCreate);

    }

    @OnClick(R.id.imageVireGoLeft)
    void previousPage()
    {
        vf.setDisplayedChild(0);
    }

    @OnClick(R.id.edtEdad)
    void datePicker()
    {
        setCurrentDate();
        showDialog(DIALOG_ID);
    }

    private void setCurrentDate()
    {
        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
    }

    protected Dialog onCreateDialog(int id)
    {
       if(id == DIALOG_ID)
           return new DatePickerDialog(this,dpickerListener,year,month,day);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            edtEdad.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
        }
    };

    private String getEditTextStringFormatted(EditText editText)
    {
        return editText.getText().toString().trim();
    }

    @OnClick(R.id.imgAddUserGo3)
    void nextPage3()
    {
        finish();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        go2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        go2.setVisibility(View.VISIBLE);
    }

    @Override
    public void setErrorPassworsNotMatch() {
        dialogoError();
        edtPassword.setError(getResources().getString(R.string.error));
        getEdtPasswordConfirmar.setError(getResources().getString(R.string.error));

    }

    @Override
    public void SetErrorNeedToCompleteAllForm() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(getResources().getString(R.string.faltanCampos));

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
    public void exito(String mensaje) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(mensaje);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        vf.setDisplayedChild(2);
                    }
                });
        builder1.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                vf.setDisplayedChild(2);
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void errorCamposIncorrectos(String mensaje) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(mensaje);
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

    private void dialogoError()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(getResources().getString(R.string.error));
        builder1.setMessage(getResources().getString(R.string.error_passwordNotMatch));
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
}
