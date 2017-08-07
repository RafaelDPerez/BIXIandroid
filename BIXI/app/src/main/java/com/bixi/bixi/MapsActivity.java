package com.bixi.bixi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.bixi.bixi.Adaptadores.RVAdapterHome;
import com.bixi.bixi.Adaptadores.RVAdapterMapProducts;
import com.bixi.bixi.Interfaces.HomePresenter;
import com.bixi.bixi.Interfaces.HomeView;
import com.bixi.bixi.Interfaces.RecyclerViewClickListenerHome;
import com.bixi.bixi.Pojos.ObjSearchProducts.Product;
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
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
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
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import android.Manifest;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,HomeView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,RecyclerViewClickListenerHome {

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
    protected static final int REQUEST_CHECK_SETTINGS =54;

    @BindView(R.id.recyclerViewMaps)
    RecyclerView rv;

    @BindView(R.id.progressBar6)
    ProgressBar bar;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
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
        activity = this;
        ApplyCustomFont.applyFont(this,findViewById(R.id.father),"fonts/Corbel.ttf");
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
        rv.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.GRAY)
                        .margin(50)
                        .build());

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        bar.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);


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
                return false;
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
            RVAdapterMapProducts adapter = new RVAdapterMapProducts(resultProductsJsons.get(position).getProducts(),this,this,true);
            rv.setAdapter(adapter);

            /*
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
                    String[] selItemArray = new String[obj.getProducts().get(posiSubOfert).getImages().size()];
                    for(int x = 0;x<obj.getProducts().get(posiSubOfert).getImages().size();x++)
                    {
                        selItemArray[x] = obj.getProducts().get(posiSubOfert).getImages().get(x);
                    }
                    i.putExtra("arrayImages",selItemArray);
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

            */
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
        bar.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        bar.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }

    @Override
    public void setError() {
        Toast.makeText(this,"No ha sido posible cargar las ofertas",Toast.LENGTH_SHORT);
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
                try {
                    int cantidadOfertas = resultProductsJsons.get(i).getProducts().size();
                    double latitud = Double.parseDouble(resultProductsJsons.get(i).getCommerceLat());
                    double longitud = Double.parseDouble(resultProductsJsons.get(i).getCommerceLng());

                    MarkerOptions markerOptions = new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(String.valueOf(cantidadOfertas))))
                            .position(new LatLng(latitud, longitud))
                            .title(resultProductsJsons.get(i).getCommerceName())
                            .snippet(String.valueOf(i))
                            .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

                    mMap.addMarker(markerOptions);
                }catch (Exception e)
                {
                    
                }
            }
            resultProductsJsons.get(i).setMaxquantityOffers(resultProductsJsons.get(i).getProducts().size());
        }



        RVAdapterMapProducts adapter = new RVAdapterMapProducts(resultProductsJsons.get(0).getProducts(),this,this,true);
        rv.setAdapter(adapter);
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

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        builder.setAlwaysShow(true);


        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        startLocationRequest();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS );
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });


    }

    private void startLocationRequest()
    {
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

        Timber.d("Cargar from server: Location Change");

        presenter.cargarProductsFromServer(token,"","","","",0,0,location.getLatitude(),location.getLongitude());
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
                Timber.d("Cargar from server: On Result");

                presenter.cargarProductsFromServer(
                        token,
                        data.getStringExtra("search"),
                        data.getStringExtra("ubicacionId"),
                        data.getStringExtra("ordenar"),
                        data.getStringExtra("isOffer"),
                        data.getIntExtra("pointFrom",10),
                        data.getIntExtra("pointTo",300));

            }
        }else if(requestCode == REQUEST_CHECK_SETTINGS)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                startLocationRequest();
            }else if(resultCode == Activity.RESULT_CANCELED)
            {

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

    @Override
    public void recyclerViewListClicked(View v, int position, ResultProductsJson resultProductsJson) {
        ResultProductsJson obj = resultProductsJson;
        int posiSubOfert = obj.getOferDisplay();
        Intent i = new Intent(MapsActivity.this,DetailActivity.class);
        i.putExtra("nombre",obj.getProducts().get(posiSubOfert).getName());
        i.putExtra("url",obj.getProducts().get(posiSubOfert).getImages().get(0));
        i.putExtra("detalle",obj.getProducts().get(posiSubOfert).getDescription());
        i.putExtra("bixiPoints",obj.getProducts().get(posiSubOfert).getPoints());
        i.putExtra("product_id",obj.getProducts().get(posiSubOfert).getProductId());
        i.putExtra("offerDisplay",posiSubOfert);

        String[] selItemArray = new String[obj.getProducts().get(posiSubOfert).getImages().size()];
        for(int x = 0;x<obj.getProducts().get(posiSubOfert).getImages().size();x++)
        {
            selItemArray[x] = obj.getProducts().get(posiSubOfert).getImages().get(x);
        }
        i.putExtra("arrayImages",selItemArray);
        i.putExtra(Constants.extraToken,token);
        startActivity(i);
    }

    @Override
    public void recyclerViewListClicked(View v, int position, Product product) {
        Product obj = product;
   //     int posiSubOfert = obj.getOferDisplay();
        Intent i = new Intent(MapsActivity.this,DetailActivity.class);
        i.putExtra("nombre",obj.getName());
        if(obj.getImages() != null && obj.getImages().size() > 0 && obj.getImages().get(0) != null)
            i.putExtra("url",obj.getImages().get(0));
        else
            i.putExtra("url","");
        i.putExtra("detalle",obj.getDescription());
        i.putExtra("bixiPoints",obj.getPoints());
        i.putExtra("product_id",obj.getProductId());
        i.putExtra("likeIt",obj.getIsFavorite());
   //     i.putExtra("offerDisplay",posiSubOfert);

        String[] selItemArray = new String[obj.getImages().size()];
        for(int x = 0;x<obj.getImages().size();x++)
        {
            selItemArray[x] = obj.getImages().get(x);
        }
        i.putExtra("arrayImages",selItemArray);
        i.putExtra(Constants.extraToken,token);
        startActivity(i);
    }

    @Override
    public void recyclerViewClicked(View v, int position) {

    }

    @Override
    public void recyclerViewRemoveItem(View v, int position) {

    }

    @Override
    public void recyclerViewLiketItem(View v, int position, ResultProductsJson resultProductsJson, boolean likeIt) {
        if(token != null && !token.equals(""))
        {
            ResultProductsJson obj = resultProductsJson;
            int posiSubOfert = obj.getOferDisplay();

            if(likeIt)
                presenter.likeProductsFromServer(token,obj.getProducts().get(posiSubOfert).getProductId(),position);
            else
                presenter.dislikeProductsFromServer(token,obj.getProducts().get(posiSubOfert).getProductId(),position);
        }else {
            alertaToken();
        }

    }
}
