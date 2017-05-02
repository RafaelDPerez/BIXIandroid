package com.bixi.bixi.Network;

import com.bixi.bixi.Pojos.Combos.ComboGeneralPojo;
import com.bixi.bixi.Pojos.CreateUserResponse;
import com.bixi.bixi.Pojos.HistorialPojo;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsLiketItJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsLikerItJson;
import com.bixi.bixi.Pojos.OffersPointsClaim;
import com.bixi.bixi.Pojos.Products;
import com.bixi.bixi.Pojos.ProductsSearch;
import com.bixi.bixi.Pojos.UpdateUserInfoPojo;
import com.bixi.bixi.Pojos.UserCreate;
import com.bixi.bixi.Pojos.UserLogin;
import com.bixi.bixi.Pojos.UserSimple;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Johnny Gil Mejia on 1/21/2017.
 */

public interface UserService {

    @PUT("user")
    Call<CreateUserResponse> putCreateUser(@Body UserCreate userCreate);

    @PUT("user")
    Call<CreateUserResponse> putCreateUser(@Query("first_name") String nombre, @Query("email") String email, @Query("password") String password, @Query("password_confirm") String password2);

    @POST("login")
    Call<UserLogin> postAttempLogin(@Body UserSimple user);

    @POST("login_social")
    Call<UserLogin> postAttempLogin_Social(@Body UserSimple user);

    @POST("search_products")
    Call<ProductsJson> postProdcuts(@Body ProductsSearch productsSearch);

    @GET("user")
    Call<UserLogin> getUserInformation(@Header("X-Request-Id") String token);

    @POST("user")
    Call<UserLogin> updateUserPassword(@Header("X-Request-Id")String token, @Body UpdateUserInfoPojo obj);

    @POST("favorites")
    Call<ProductsLiketItJson> getLiketItOffers(@Header("X-Request-Id")String token);

    @POST("quit_favorites")
    Call<ProductsLiketItJson> setOfferDislike(@Header("X-Request-Id")String token, @Body ResultProductsLikerItJson obj);

    @POST("add_favorites")
    Call<ProductsLiketItJson> setOfferLikeIt(@Header("X-Request-Id")String token, @Body ResultProductsLikerItJson obj);

    @POST("reclaim")
    Call<ProductsLiketItJson> reclaimOffer(@Header("X-Request-Id")String token, @Body OffersPointsClaim obj);

    @POST("add_points")
    Call<ProductsLiketItJson> addPointsOffer(@Header("X-Request-Id")String token, @Body OffersPointsClaim obj);

    @POST("historical")
    Call<HistorialPojo> getHistorical(@Header("X-Request-Id")String token);

    @GET("type_commerce_list")
    Call<ComboGeneralPojo>getTyppeCommerceList();
}

