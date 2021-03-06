package com.bixi.bixi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bixi.bixi.Interfaces.LoginPresenter;
import com.bixi.bixi.Interfaces.LoginView;
import com.bixi.bixi.Network.Injector;
import com.bixi.bixi.Presenter.LoginPresenterImpl;
import com.bixi.bixi.Utility.Constants;
import com.bixi.bixi.Views.HomeActivity;
import com.bixi.bixi.Views.AddUserActivity;
import com.bixi.bixi.bixi.basics.ApplyCustomFont;
import com.bixi.bixi.bixi.basics.RecuperarContrasena;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static android.R.id.progress;

public class Login extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener, LoginView {

    private CallbackManager callbackManagerFaceBook;
    private TwitterAuthClient client;

    @BindString(R.string.twitter_key)
    String twitterKey;

    @BindString(R.string.twitter_secret)
    String twitterSecret;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.progressBarLogin)
    ProgressBar progressBar;

    @BindView(R.id.imgLogin)
    ImageView imgLogin;

    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    private LoginPresenter presenter;
    private String token;
    public static final String PACKAGE = "com.bixi.bixi";
    private static final String host = "api.linkedin.com";
    private static final String url = "https://" + host
            + "/v1/people/~:" +
            "(email-address,formatted-name,phone-numbers,picture-urls::(original))";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        inicializaciones();
        setUpTwitter();
        setUpGoogle();
        makeNoLimits();
        if(checkIfTokenExits())
            Log.e("Token existente: ",token);
        else
            Log.e("No existe Token","");

        ApplyCustomFont.applyFont(this,findViewById(R.id.activity_main),"fonts/Corbel.ttf");
//        generateHashkey();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    private void setUpGoogle() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @OnClick(R.id.imgLogin)
    void login()
    {
        presenter.validarUsuario(edtEmail.getText().toString().trim(),edtPassword.getText().toString().trim());
     //     Intent i = new Intent(Login.this, HomeActivity.class);
     //   Intent i = new Intent(Login.this, homeDrawable.class);
     //     i.putExtra(Constants.extraToken,token);
     //   startActivity(i);
    }

    @OnClick(R.id.txtContrasena)
    void goToPasswordActivity()
    {
        Intent intent = new Intent(this, RecuperarContrasena.class);
        startActivity(intent);
    }

    @OnClick(R.id.txtRegistrate)
    void gotToRegistrarActivity()
    {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

    private void setUpTwitter()
    {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(twitterKey, twitterSecret);
        Fabric.with(this, new Twitter(authConfig));
        client = new TwitterAuthClient();
    }

    @OnClick(R.id.imageViewGoogle)
    void handleGoogleLogin()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnClick(R.id.imageViewTwitter)
    void handleTwitterLogin()
    {
        client.authorize(Login.this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> twitterSessionResult) {
                Toast.makeText(Login.this, "success", Toast.LENGTH_SHORT).show();
                System.out.println("onSuccess");
                System.out.println("Twitter ID: "+twitterSessionResult.data.getUserId());
                System.out.println("Twitter Auth Token: "+twitterSessionResult.data.getAuthToken());
                System.out.println("Twitter UserCreate Name: "+twitterSessionResult.data.getUserName());

                final TwitterSession twitterSession = twitterSessionResult.data;
                client.requestEmail(twitterSession, new com.twitter.sdk.android.core.Callback<String>() {
                    @Override
                    public void success(Result<String> emailResult) {
                        String email = emailResult.data;
                        presenter.validarUsuario_LoginSocial(email);
                        // ...
                    }

                    @Override
                    public void failure(TwitterException e) {
                   //     callback.onTwitterSignInFailed(e);
                    }
                });
            }

            @Override
            public void failure(TwitterException e) {
                Toast.makeText(Login.this, "failure", Toast.LENGTH_SHORT).show();
                System.out.println("Twitter Error"+e.getMessage().toString());
            }
        });
    }

    @OnClick(R.id.imageViewLinkedin)
    void handleLinkdin()
    {
        LISessionManager.getInstance(getApplicationContext())
                .init(this, buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {

     //                   Toast.makeText(getApplicationContext(), "success" +
     //                                   LISessionManager.getInstance(getApplicationContext())
     //                                           .getSession().getAccessToken().toString(),
     //                           Toast.LENGTH_LONG).show();

                        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
                        apiHelper.getRequest(Login.this, url, new ApiListener() {
                            @Override
                            public void onApiSuccess(ApiResponse result) {
                                try {
                                    if(result != null)
                                        presenter.validarUsuario_LoginSocial(result.getResponseDataAsJson().get("emailAddress").toString());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onApiError(LIApiError error) {

                            }
                        });
                    }

                    @Override
                    public void onAuthError(LIAuthError error) {
                        Toast.makeText(getApplicationContext(), "failed "
                                        + error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }, true);
    }

    @OnClick(R.id.imageViewFacebook)
    void handleFbLogin()
    {
        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));

        LoginManager.getInstance().registerCallback(callbackManagerFaceBook,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        System.out.println("onSuccess");
                        System.out.println("Facebook ID: "+loginResult.getAccessToken().getUserId());
                        System.out.println("Facebook Auth Token: "+loginResult.getAccessToken().getToken());

                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject json, GraphResponse response) {
                                // Application code
                                if (response.getError() != null) {
                                    System.out.println("ERROR");
                                } else {
                                    System.out.println("Success");
                                    String jsonresult = String.valueOf(json);
                                    System.out.println("JSON Result" + jsonresult);

                                    String fbUserEmail = json.optString("email");
                                    presenter.validarUsuario_LoginSocial(fbUserEmail);
                                }
                                Log.v("FaceBook Response :", response.toString());
                            }
                        });

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        System.out.println("Facebook Error: "+exception.toString());
                    }
                });
    }

    private void inicializaciones()
    {
      //  AppEventsLogger.activateApp(this);
        ButterKnife.bind(this);
        callbackManagerFaceBook = CallbackManager.Factory.create();
        presenter = new LoginPresenterImpl(this);


       // TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
    }

    private void makeNoLimits()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManagerFaceBook.onActivityResult(requestCode, resultCode, data);
        client.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


        LISessionManager.getInstance(getApplicationContext())
                .onActivityResult(this,
                        requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("googleLogin", "handleSignInResult: " + result.isSuccess());
        Log.d("googleLogin_status", "handleSignInResult_status: " + result.getStatus().toString());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if(acct != null && acct.getEmail() != null)
            {
                System.out.println("onSuccess");
                System.out.println("Google ID: "+acct.getId());
                System.out.println("Google Auth Token: "+acct.getIdToken());
                System.out.println("Google UserCreate Name: "+acct.getDisplayName());
                presenter.validarUsuario_LoginSocial(acct.getEmail());
            }

          //  updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
          //  updateUI(false);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        imgLogin.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        imgLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void setErrorUser() {
        edtEmail.setError("Error");
    }

    @Override
    public void setErrorPassword() {
        edtPassword.setError("Error");
    }

    @Override
    public void setError(String error) {
        errorCamposIncorrectos(error);
    }

    @Override
    public void setToken(String token) {
        SharedPreferences prefs =
                getSharedPreferences(Constants.appPreferences,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", token);
        editor.commit();
        this.token = token;
        Log.e("Token: ",token);
    }


    private boolean checkIfTokenExits(){
        SharedPreferences prefs =
                getSharedPreferences(Constants.appPreferences,Context.MODE_PRIVATE);
        token = prefs.getString("token", "");

        if(token != null && !token.equals(""))
            return true;
        else
            return false;
    }

    @Override
    public void navigateToHome() {
        Intent i = new Intent(Login.this, homeDrawable.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra(Constants.extraToken,token);
        startActivity(i);
    }

    @Override
    public void navigateToRegister() {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

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

    public void generateHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    PACKAGE,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                System.out.println("Linkin Hasf: "+Base64.encodeToString(md.digest(),
                        Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Name not found", e.getMessage(), e);

        } catch (NoSuchAlgorithmException e) {
            Log.d("Error", e.getMessage(), e);
        }
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }


}
