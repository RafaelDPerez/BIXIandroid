package com.bixi.bixi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bixi.bixi.Interfaces.HomePresenter;
import com.bixi.bixi.Interfaces.HomeView;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsLiketItJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsJson;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Presenter.HomePresenterImpl;
import com.bixi.bixi.Utility.Constants;
import com.bixi.bixi.Views.DetailActivity;
import com.bixi.bixi.Views.HomeLikeIt;
import com.bixi.bixi.Views.SearchActivity;
import com.bixi.bixi.bixi.basics.ApplyCustomFont;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import android.Manifest;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,HomeView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private Marker myMarker;
    private PopupWindow mPopupWindow;
    private SupportMapFragment mapFragment;
    private HomePresenter presenter;
    private List<ResultProductsJson> resultProductsJsons;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    static final int SEARCH_REQUEST = 1;
    private String token;
    private Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        overridePendingTransition(0, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            token = extras.getString(Constants.extraToken);
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        presenter = new HomePresenterImpl(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuhome, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mybutton) {
            // do something here
            Intent i = new Intent(MapsActivity.this,SearchActivity.class);
            startActivityForResult(i,SEARCH_REQUEST);
        }else if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
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


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void inflateCustomLayout(String tag)
    {
        final int position = Integer.parseInt(tag);

        if(resultProductsJsons.get(position) != null)
        {
            resultProductsJsons.get(position).setOferDisplay(0);
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
            final String descripcion = resultProductsJsons.get(position).getProducts().get(0).getName();
             d = new Dialog(MapsActivity.this);
      //      d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            d.setContentView(R.layout.home_recycler_layout_dif);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(d.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            lp.gravity = Gravity.CENTER;

            d.getWindow().setAttributes(lp);

            final ImageView image = (ImageView) d.findViewById(R.id.ivOferta);
 //           String url = "http://static.viagrupo.com/thumbs/unnamed_3_63_PNG_464x464.png";




            TextView txtTitulo = (TextView) d.findViewById(R.id.tvTitulo);
            txtTitulo.setText(establecimiento);

            final TextView txtDescripcion = (TextView) d.findViewById(R.id.tvDescripcion);
            txtDescripcion.setText(descripcion);

            final ImageView imgGoRigh = (ImageView) d.findViewById(R.id.imageView3);
            final ImageView imgGoLeft = (ImageView) d.findViewById(R.id.imageView4);
            final ImageView imgLiketIt = (ImageView) d.findViewById(R.id.imageView2);
            final TextView tvBixiPoints = (TextView) d.findViewById(R.id.tvBixiPoints);
            final ProgressBar pb = (ProgressBar) d.findViewById(R.id.progressBar3);

            tvBixiPoints.setVisibility(View.INVISIBLE);
            imgLiketIt.setVisibility(View.VISIBLE);
            imgGoRigh.setVisibility(View.VISIBLE);
            imgGoLeft.setVisibility(View.VISIBLE);

            paintImage(url,image,pb);

            if(resultProductsJsons.get(position).getMaxquantityOffers() == 1)
            {
                updateArrows(imgGoLeft,false);
                updateArrows(imgGoRigh,false);
            }
            if(resultProductsJsons.get(position).getOferDisplay() == 0)
            {
                updateArrows(imgGoLeft,false);
            }

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    ResultProductsJson obj = resultProductsJsons.get(position);
                    int posiSubOfert = obj.getOferDisplay();
                    Intent i = new Intent(MapsActivity.this,DetailActivity.class);
                    i.putExtra("nombre",obj.getProducts().get(posiSubOfert).getName());
                    i.putExtra("url",obj.getProducts().get(posiSubOfert).getImages().get(0));
                    i.putExtra("detalle",obj.getProducts().get(posiSubOfert).getDescription());
                    i.putExtra("bixiPoints",obj.getProducts().get(posiSubOfert).getPoints());
                    i.putExtra("product_id",obj.getProducts().get(posiSubOfert).getProductId());
                    i.putExtra(Constants.extraToken,token);
                        startActivity(i);


                }
            });

            imgGoRigh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(resultProductsJsons.get(position).getOferDisplay()< resultProductsJsons.get(position).getMaxquantityOffers() - 1)
                    {
                        int currentPosition = resultProductsJsons.get(position).getOferDisplay();
                        currentPosition ++;
                        pb.setVisibility(View.VISIBLE);
                        txtDescripcion.setText(resultProductsJsons.get(position).getProducts().get(currentPosition).getName());
                        paintImage(resultProductsJsons.get(position).getProducts().get(currentPosition).getImages().get(0),image,pb);
                        resultProductsJsons.get(position).setOferDisplay(currentPosition);
                        tvBixiPoints.setText(resultProductsJsons.get(position).getProducts().get(currentPosition).getPoints()+"B");

                        if(resultProductsJsons.get(position).getOferDisplay() == resultProductsJsons.get(position).getMaxquantityOffers() - 1)
                        {
                            updateArrows(imgGoRigh,false);
                            updateArrows(imgGoLeft,true);
                        }else
                        {
                            updateArrows(imgGoRigh,true);
                            updateArrows(imgGoLeft,true);
                        }
                    }
                }
            });

            imgGoLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = resultProductsJsons.get(position).getOferDisplay();
                    currentPosition --;
                    pb.setVisibility(View.VISIBLE);
                    txtDescripcion.setText(resultProductsJsons.get(position).getProducts().get(currentPosition).getName());
                    paintImage(resultProductsJsons.get(position).getProducts().get(currentPosition).getImages().get(0),image,pb);
                    resultProductsJsons.get(position).setOferDisplay(currentPosition);
                    tvBixiPoints.setText(resultProductsJsons.get(position).getProducts().get(currentPosition).getPoints()+"B");

                    if(resultProductsJsons.get(position).getOferDisplay() == 0)
                    {
                        updateArrows(imgGoRigh,true);
                        updateArrows(imgGoLeft,false);
                    }else
                    {
                        updateArrows(imgGoRigh,true);
                        updateArrows(imgGoLeft,true);
                    }
                }

            });

            imgLiketIt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(token != null && !token.equals(""))
                    {
                        ResultProductsJson obj = resultProductsJsons.get(position);
                        int posiSubOfert = obj.getOferDisplay();
                        presenter.likeProductsFromServer(token,obj.getProducts().get(posiSubOfert).getProductId(),position);
                    }else
                    {
                        alertaToken();
                    }

                }
            });

            ApplyCustomFont.applyFont(this,d.findViewById(R.id.home_recycler_id_dif),"fonts/Corbel.ttf");

            d.show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(d != null)
        {
            if(d.isShowing())
            {
                d.dismiss();
            }
        }
    }

    private void updateArrows(ImageView img, boolean show)
    {
        if(show)
            img.setVisibility(View.VISIBLE);
        else
            img.setVisibility(View.INVISIBLE);
    }

    private void paintImage(String url, ImageView image, final ProgressBar pbar)
    {
        if(url != null && !url.equals(""))
        {
            Picasso.with(getApplicationContext())
                    .load(url)
                    .fit()
                    .placeholder(R.color.colorAccent)
                    .error(R.color.colorAccent)
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                            pbar.setVisibility(View.INVISIBLE);
                        }
                    });
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

            }

            resultProductsJsons.get(i).setMaxquantityOffers(resultProductsJsons.get(i).getProducts().size());
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void operacionExitosaLikeItFromServer(ProductsLiketItJson productsLiketItJson) {

    }

    @Override
    public void updateRV(boolean success, int position) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));

        //optionally, stop location updates if only current location is needed
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SEARCH_REQUEST)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                presenter.cargarProductsFromServer(
                        data.getStringExtra("search"),
                        data.getStringExtra("ubicacionId"),
                        data.getStringExtra("ordenar"),
                        data.getStringExtra("isOffer"),
                        data.getIntExtra("pointFrom",10),
                        data.getIntExtra("pointTo",300));
            }
        }
    }

    private void alertaToken()
    {

        final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(MapsActivity.this,R.style.DialogAlertTheme).create();
        alertDialog.setTitle("Alerta");
        alertDialog.setMessage("Debe iniciar sesión o registrarse.");
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent newIntent = new Intent(MapsActivity.this,Login.class);
                        startActivity(newIntent);
                    }
                });
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
