package com.bixi.bixi.Views;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bixi.bixi.MapsActivity;
import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.bixi.bixi.bixi.basics.ApplyCustomFont;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

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

    @BindView(R.id.progressBarAddPoint)
    ProgressBar bar;

    @BindView(R.id.btnAddPoints)
    Button btnAddpoints;

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
            bar.setVisibility(View.VISIBLE);
            btnAddpoints.setVisibility(View.GONE);
            inflateCustomLayout();
            /*
            Intent i = new Intent(getActivity(), PasswordActivity.class);
            Bundle bundle = new Bundle();
            i.putExtra("addPoints",true);
            i.putExtra("points",addPoints.getText().toString().trim());
            i.putExtra("addPointsDescripcion",addPointsDescripcion.getText().toString().trim());
            i.putExtra(Constants.extraToken,token);
            startActivity(i);
            */

        }else
        {
            Snackbar.make(view,"Debe completar los campos",Snackbar.LENGTH_SHORT).show();
        }
    }

    private void inflateCustomLayout()
    {

        if(getInstance() != null && getInstance().getActivity() != null)
        {
            int QRcodeWidth = 500;
            Bitmap bitmap ;
            Dialog d = new Dialog(getInstance().getActivity());
            //      d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            d.setContentView(R.layout.qr_layout);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(d.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

            lp.gravity = Gravity.CENTER;

            d.getWindow().setAttributes(lp);

            final ImageView image = (ImageView) d.findViewById(R.id.imageViewQR);

            try
            {
                bitmap = TextToImageEncode(addPoints.getText().toString().trim()+" "+addPointsDescripcion.getText().toString().trim());
                if(image != null)
                {
                    btnAddpoints.setVisibility(View.VISIBLE);
                    bar.setVisibility(View.GONE);
                    image.setImageBitmap(bitmap);
                }

            }catch (WriterException e) {
                e.printStackTrace();
                btnAddpoints.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);
            }

            ApplyCustomFont.applyFont(getInstance().getActivity(),d.findViewById(R.id.imageViewQR),"fonts/Corbel.ttf");
            d.show();
        }

    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    500, 500, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.tw__solid_white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
