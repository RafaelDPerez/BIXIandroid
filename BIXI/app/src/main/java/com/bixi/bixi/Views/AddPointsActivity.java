package com.bixi.bixi.Views;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bixi.bixi.Interfaces.GeneralInterface;
import com.bixi.bixi.Manifest;
import com.bixi.bixi.MapsActivity;
import com.bixi.bixi.Pojos.OffersPointsClaim;
import com.bixi.bixi.Presenter.PasswordActivityPresenterImpl;
import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.bixi.bixi.bixi.basics.ApplyCustomFont;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPointsActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener, GeneralInterface {

    private String productId;
    private String token;
    private boolean addPoints;
    private String points;
    private String addPointsDescripcion;

    @BindView(R.id.qrdecoderview)
    QRCodeReaderView qrCodeReaderView;

    @BindView(R.id.tvEscanearCodigo)
    TextView tvScan;

    @BindView(R.id.pbAddPointsActivity)
    ProgressBar pb;
    private PasswordActivityPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_points);
        ButterKnife.bind(this);
        presenter = new PasswordActivityPresenterImpl(this);
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
        ApplyCustomFont.applyFont(this,findViewById(R.id.father),"fonts/Corbel.ttf");

        inicializarqrReader();

    }

    void inicializarqrReader()
    {
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }

    @OnClick(R.id.tvEscanearCodigo)
    void scann()
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                tvScan.setVisibility(View.INVISIBLE);
                qrCodeReaderView.setVisibility(View.VISIBLE);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }else {
            tvScan.setVisibility(View.INVISIBLE);
            qrCodeReaderView.setVisibility(View.VISIBLE);
        }

    }

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Camera Permission Needed")
                        .setMessage("This app needs the Camera permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(AddPointsActivity.this,
                                        new String[]{android.Manifest.permission.CAMERA},
                                        MY_PERMISSIONS_REQUEST_CAMERA );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA );
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvScan.setVisibility(View.VISIBLE);
        qrCodeReaderView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        qrCodeReaderView.stopCamera();
        qrCodeReaderView.setVisibility(View.INVISIBLE);
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
        pb.setVisibility(View.VISIBLE);

        OffersPointsClaim obj = new OffersPointsClaim();
        obj.setQr(text);
        presenter.addPointsOffer(token,obj);
    }
    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    @Override
    public void exito(String msg) {
        pb.setVisibility(View.INVISIBLE);
        tvScan.setVisibility(View.VISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                AddPointsActivity.this);
        builder.setMessage(msg);
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
        pb.setVisibility(View.INVISIBLE);
        tvScan.setVisibility(View.VISIBLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(
                AddPointsActivity.this);
        builder.setTitle("Error");
        builder.setMessage(msg);
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
    public void hideProgress() {
        pb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgress() {
        pb.setVisibility(View.VISIBLE);
    }
}
