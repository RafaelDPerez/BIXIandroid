package com.bixi.bixi.Views;


import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bixi.bixi.Interfaces.GeneralInterface;
import com.bixi.bixi.Interfaces.HomeProfilePresenter;
import com.bixi.bixi.Interfaces.HomeProfileView;
import com.bixi.bixi.Interfaces.ResetPasswordPresenter;
import com.bixi.bixi.Login;
import com.bixi.bixi.Pojos.Result;
import com.bixi.bixi.Pojos.UserLogin;
import com.bixi.bixi.Presenter.HomeProfilePresenterImpl;
import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.bixi.bixi.bixi.basics.ApplyCustomFont;
import com.bixi.bixi.homeDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.frosquivel.magicalcamera.Utilities.ConvertSimpleImage;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeProfileFragment extends Fragment implements HomeProfileView, GeneralInterface{


    @BindView(R.id.tv_profile_name)
    TextView tvProfileName;

    @BindView(R.id.tv_profile_bixi_points)
    TextView tvProfileBixiPoints;

    @BindView(R.id.tv_profile_correo)
    TextView tvProfileCorreo;

    @BindView(R.id.tv_profile_sexo)
    TextView tvProfileSexo;

    @BindView(R.id.tv_profile_telefono)
    TextView tvProfileTelefono;

    @BindView(R.id.tv_profile_fecha_nacimiento)
    TextView tvProfileFechaNacimiento;

    @BindView(R.id.profile_image)
    CircleImageView imgProfileImage;

    @BindView(R.id.progressBar4)
    ProgressBar pb;

    @BindView(R.id.progressBarProfile)
    ProgressBar pbProfile;

    @BindView(R.id.constNew)
    ConstraintLayout constraintLayout;

    private static HomeProfileFragment instance;
    private View view;
    private ImageButton imageButtonCamera;
    private Button btnResetPassword;
    private HomeProfilePresenter presenter;
    private Result information;
    private String token;
    private MagicalPermissions magicalPermissions;
    private MagicalCamera magicalCamera;
    private String[] permissions = new String[] {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String imageBase64 = "";


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
        ButterKnife.bind(this,view);
        return view;
    }

    private void loadView(LayoutInflater inflater, ViewGroup container) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.user_profile_layout, container, false);
        ApplyCustomFont.applyFont(getInstance().getActivity(),view.findViewById(R.id.profile_id),"fonts/Corbel.ttf");
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.purpleBixi2), PorterDuff.Mode.MULTIPLY);
        pbProfile.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.purpleBixi2), PorterDuff.Mode.MULTIPLY);
        imageButtonCamera = (ImageButton) view.findViewById(R.id.imageButtonCamera_profile);
        btnResetPassword = (Button) view.findViewById(R.id.btnResetPassword);
        magicalPermissions = new MagicalPermissions(this, permissions);
        magicalCamera = new MagicalCamera(getActivity(),20,magicalPermissions);
        buttonOnClick();
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null){
            token = extras.getString(Constants.extraToken);
            presenter = new HomeProfilePresenterImpl(this);
            presenter.getUserInformation(token);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Map<String, Boolean> map = magicalPermissions.permissionResult(requestCode, permissions, grantResults);
        for (String permission : map.keySet()) {
            Log.d("PERMISSIONS", permission + " was: " + map.get(permission));
        }
        //Following the example you could also
        //locationPermissions(requestCode, permissions, grantResults);
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

                        switch (item.getItemId()) {
                            case R.id.one:
                                magicalCamera.takeFragmentPhoto(HomeProfileFragment.this);
                                return true;
                            case R.id.two:
                                magicalCamera.selectFragmentPicture(HomeProfileFragment.this,"example");
                                return true;
                        }

                        magicalCamera.takePhoto();
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
                Bundle bundle = new Bundle();
                bundle.putSerializable("user_info", information);
                i.putExtras(bundle);
                i.putExtra(Constants.extraToken,token);
                startActivity(i);
            }
        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //CALL THIS METHOD EVER
        magicalCamera.resultPhoto(requestCode, resultCode, data);
        if(magicalCamera != null)
        {
            imgProfileImage.setImageBitmap(magicalCamera.getPhoto());
            imageBase64 = ConvertSimpleImage.bytesToStringBase64(convertBitmapToByte(magicalCamera.getPhoto()));
            information.setImage(imageBase64);
            presenter.updateUserInformationPicture(token,information);
            information.setImage(null);
        }
    }

    private byte[] convertBitmapToByte(Bitmap bitmap)
    {
        Bitmap bmp = bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void operacionExitosa(UserLogin obj) {
        if(obj != null && obj.getResult() != null)
        {
            information = obj.getResult();
            if(information.getFirst_name() != null)
                tvProfileName.setText(information.getFirst_name());
            if(information.getBalance_points() != null)
                tvProfileBixiPoints.setText(information.getBalance_points() + "B");
            if(information.getEmail() != null)
                tvProfileCorreo.setText(information.getEmail());
            if(information.getGender() != null)
                tvProfileSexo.setText(information.getGender());
            if(information.getPhone1() != null)
                tvProfileTelefono.setText(information.getPhone1());
            if(information.getBirth_date() != null)
                tvProfileFechaNacimiento.setText(information.getBirth_date());
            if(information.getImage() != null) {
                pbProfile.setVisibility(View.VISIBLE);
                Glide.with(getInstance().getActivity()).load(information.getImage()).dontAnimate().centerCrop().placeholder(R.color.colorAccent)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                pbProfile.setVisibility(View.INVISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                pbProfile.setVisibility(View.INVISIBLE);
                                return false;
                            }
                        })
                        .into(imgProfileImage);
            }
        }
    }

    @Override
    public void error() {

    }

    @Override
    public void showProgress() {
        pb.setVisibility(View.VISIBLE);
        constraintLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void exito(String msg) {

    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void hideProgress() {
        constraintLayout.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
    }
}
