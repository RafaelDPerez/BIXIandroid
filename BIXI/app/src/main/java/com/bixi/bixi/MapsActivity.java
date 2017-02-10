package com.bixi.bixi;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker myMarker;
    private PopupWindow mPopupWindow;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng bellaVista = new LatLng(18.454242, -69.941852);
        myMarker = mMap.addMarker(new MarkerOptions().position(bellaVista).title("Establecimiento 1"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(bellaVista));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bellaVista,15));

        LatLng plazaLama = new LatLng(18.462323, -69.936295);
        mMap.addMarker(new MarkerOptions().position(plazaLama).title("Establecimiento 2"));


        LatLng bellaVista3 = new LatLng(18.449592, -69.952679);
        mMap.addMarker(new MarkerOptions().position(bellaVista3).title("Establecimiento 3"));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                if(arg0 != null && arg0.equals(myMarker)); // if marker  source is clicked
                inflateCustomLayout();
                return true;
            }

        });

    }

    private void inflateCustomLayout()
    {
        final Dialog d = new Dialog(MapsActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //d.setTitle("Select");
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        d.setContentView(R.layout.home_recycler_layout);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        lp.gravity = Gravity.CENTER;

        d.getWindow().setAttributes(lp);

        ImageView image = (ImageView) d.findViewById(R.id.ivOferta);
        String url = "http://static.viagrupo.com/thumbs/unnamed_3_63_PNG_464x464.png";
        Picasso.with(getApplicationContext())
                .load(url)
                .fit()
                .placeholder(R.color.colorAccent)
                .error(R.color.colorAccent)
                .into(image);

        TextView txtTitulo = (TextView) d.findViewById(R.id.tvTitulo);
        txtTitulo.setText("Establecimiento");

        TextView txtDescripcion = (TextView) d.findViewById(R.id.tvDescripcion);
        txtDescripcion.setText("Esta es la descripcion de la oferta");

        d.show();
    }

}
