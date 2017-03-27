package com.bixi.bixi;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bixi.bixi.Interfaces.HomePresenter;
import com.bixi.bixi.Interfaces.HomeView;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsLiketItJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsJson;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Presenter.HomePresenterImpl;
import com.bixi.bixi.Views.HomeLikeIt;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.BubbleIconFactory;
import com.google.maps.android.ui.IconGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.StringTokenizer;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,HomeView {

    private GoogleMap mMap;
    private Marker myMarker;
    private PopupWindow mPopupWindow;
    private SupportMapFragment mapFragment;
    private HomePresenter presenter;
    private List<ResultProductsJson> resultProductsJsons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        presenter = new HomePresenterImpl(this);
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
        presenter.cargarProductsFromServer("");
        mMap = googleMap;



        // Add a marker in Sydney and move the camera
 //       LatLng bellaVista = new LatLng(18.454242, -69.941852);
 //       myMarker = mMap.addMarker(new MarkerOptions().position(bellaVista).title("Establecimiento 1"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(bellaVista));
  //      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bellaVista,15));

//        LatLng plazaLama = new LatLng(18.462323, -69.936295);
//       mMap.addMarker(new MarkerOptions().position(plazaLama).title("Establecimiento 2"));


//        LatLng bellaVista3 = new LatLng(18.449592, -69.952679);
//        mMap.addMarker(new MarkerOptions().position(bellaVista3).title("Establecimiento 3"));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                if(arg0 != null && arg0.equals(myMarker)); // if marker  source is clicked
                    inflateCustomLayout(arg0.getSnippet());
                return true;
            }

        });

    }

    private void inflateCustomLayout(String tag)
    {
        int position = Integer.parseInt(tag);

        if(resultProductsJsons.get(position) != null)
        {
            String url = "";
            if(resultProductsJsons.get(position).getProducts().get(0).getImages() != null && resultProductsJsons.get(position).getProducts().get(0).getImages().size() > 0);
            {
                try {
                    url = resultProductsJsons.get(position).getProducts().get(0).getImages().get(0);
                }catch (Exception e)
                {
                    url = "http://static.viagrupo.com/thumbs/unnamed_3_63_PNG_464x464.png";
                }
            }
            String establecimiento = resultProductsJsons.get(position).getCommerceName();
            String descripcion = resultProductsJsons.get(position).getCommerceAddress();
            final Dialog d = new Dialog(MapsActivity.this);
      //      d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            d.setContentView(R.layout.home_recycler_layout);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(d.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            lp.gravity = Gravity.CENTER;

            d.getWindow().setAttributes(lp);

            ImageView image = (ImageView) d.findViewById(R.id.ivOferta);
 //           String url = "http://static.viagrupo.com/thumbs/unnamed_3_63_PNG_464x464.png";

            if(url != null && !url.equals(""))
            {
                Picasso.with(getApplicationContext())
                        .load(url)
                        .fit()
                        .placeholder(R.color.colorAccent)
                        .error(R.color.colorAccent)
                        .into(image);
            }


            TextView txtTitulo = (TextView) d.findViewById(R.id.tvTitulo);
            txtTitulo.setText(establecimiento);

            TextView txtDescripcion = (TextView) d.findViewById(R.id.tvDescripcion);
            txtDescripcion.setText(descripcion);

            ImageView imgGoRigh = (ImageView) d.findViewById(R.id.imageView3);
            ImageView imgGoLeft = (ImageView) d.findViewById(R.id.imageView4);
            ImageView imgLiketIt = (ImageView) d.findViewById(R.id.imageView2);

            imgLiketIt.setVisibility(View.VISIBLE);
            imgGoRigh.setVisibility(View.VISIBLE);
            imgGoLeft.setVisibility(View.VISIBLE);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            });

            imgGoRigh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            imgGoLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            d.show();
        }

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setError() {

    }

    @Override
    public void operacionExitosa(List<Oferta> ofertas) {

    }

    @Override
    public void operacionExitosaFromServer(ProductsJson productsJson) {
        this.resultProductsJsons = productsJson.getResult();

        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setStyle(IconGenerator.STYLE_RED);

        for(int i = 0; i<resultProductsJsons.size();i++)
        {
            if(resultProductsJsons.get(i).getCommerceLat() != null && !resultProductsJsons.get(i).getCommerceLat().equals("")
                    && resultProductsJsons.get(i).getCommerceLng() != null && !resultProductsJsons.get(i).getCommerceLng().equals(""))
            {
                int cantidadOfertas = resultProductsJsons.get(i).getProducts().size();
                double latitud = Double.parseDouble(resultProductsJsons.get(i).getCommerceLat());
                double longitud = Double.parseDouble(resultProductsJsons.get(i).getCommerceLng());

                MarkerOptions markerOptions = new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(String.valueOf(cantidadOfertas))))
                        .position(new LatLng(latitud , longitud))
                        .snippet(String.valueOf(i))
                        .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

                mMap.addMarker(markerOptions);

                  LatLng move = new LatLng(latitud , longitud);
                  mMap.moveCamera(CameraUpdateFactory.newLatLng(move));
                  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(move,15));
            }

        }
    }

    @Override
    public void operacionExitosaLikeItFromServer(ProductsLiketItJson productsLiketItJson) {

    }

    @Override
    public void updateRV(boolean success, int position) {

    }
}
