package com.bixi.bixi.Views;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bixi.bixi.Interfaces.GeneralInterface;
import com.bixi.bixi.Interfaces.PasswordActivityPresenter;
import com.bixi.bixi.Pojos.OffersPointsClaim;
import com.bixi.bixi.Presenter.PasswordActivityPresenterImpl;
import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.bixi.bixi.bixi.basics.ApplyCustomFont;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bixi.bixi.R.id.end_padder;
import static com.bixi.bixi.R.id.textView;

public class PasswordActivity extends AppCompatActivity implements GeneralInterface {

    @BindView(R.id.edtPassPassword)
    EditText password;

    @BindView(R.id.pbPassword)
    ProgressBar pb;

    private PasswordActivityPresenter presenter;
    private String productId;
    private String token;
    private boolean addPoints = false;
    private String points;
    private String addPointsDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            productId = extras.getString("product_id");
            token = extras.getString(Constants.extraToken);
            addPoints = extras.getBoolean("addPoints",false);
            if(addPoints)
            {
                points = extras.getString("points");
                addPointsDescripcion = extras.getString("addPointsDescripcion");
            }
        }
        inicializaciones();
        setUpTextWatcher();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpTextWatcher()
    {
        password.addTextChangedListener(passwordWatcher);
    }

    private final TextWatcher passwordWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void afterTextChanged(Editable s) {
            if (s.length() == 6) {
                if(!addPoints) {
                    OffersPointsClaim obj = new OffersPointsClaim();
                    obj.setPin(s.toString().trim());
                    obj.setProduct_id(productId);
                    presenter.reclaimOffer(token, obj);
                }else
                {
                    OffersPointsClaim obj = new OffersPointsClaim();
                    obj.setDescription(addPointsDescripcion);
                    obj.setPin(s.toString().trim());
                    obj.setPoints(Integer.parseInt(points));
                    presenter.addPointsOffer(token,obj);
                }
            }
        }
    };

    private void inicializaciones()
    {
        ButterKnife.bind(this);
        presenter = new PasswordActivityPresenterImpl(this);
        ApplyCustomFont.applyFont(this,findViewById(R.id.activity_password_id),"fonts/Corbel.ttf");
    }

    @OnClick(R.id.btn0Password)
    void write0()
    {
        password.append("0");
    }

    @OnClick(R.id.btn1Password)
    void write1()
    {
        password.append("1");
    }

    @OnClick(R.id.btn2Password)
    void write2()
    {
        password.append("2");
    }

    @OnClick(R.id.btn3Password)
    void write3()
    {
        password.append("3");
    }

    @OnClick(R.id.btn4Password)
    void write4()
    {
        password.append("4");
    }

    @OnClick(R.id.btn5Password)
    void write5()
    {
        password.append("5");
    }

    @OnClick(R.id.btn6Password)
    void write6()
    {
        password.append("6");
    }

    @OnClick(R.id.btn7Password)
    void write7()
    {
        password.append("7");
    }

    @OnClick(R.id.btn8Password)
    void write8()
    {
        password.append("8");
    }

    @OnClick(R.id.btn9Password)
    void write9()
    {
        password.append("9");
    }

    @OnClick(R.id.btnCPassword)
    void clearWrite()
    {
        password.setText("");
    }


    @Override
    public void exito(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                PasswordActivity.this);
        builder.setTitle("Felicidades");
        builder.setMessage("Operación realizada con éxito!");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        finish();
                    }
                });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();

    }


    @Override
    public void error(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                PasswordActivity.this);
        builder.setTitle("Error");
        builder.setMessage("Lo sentimos, se ha producido un error!");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        builder.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyReference();
        presenter = null;
        productId = null;
        token = null;
        password = null;
        pb = null;
    }

    @Override
    public void hideProgress() {
        pb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgress() {
        pb.setVisibility(View.VISIBLE);
    }
}
