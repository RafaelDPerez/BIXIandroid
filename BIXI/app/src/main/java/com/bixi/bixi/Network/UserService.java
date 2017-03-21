package com.bixi.bixi.Network;

import com.bixi.bixi.Pojos.CreateUserResponse;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.Products;
import com.bixi.bixi.Pojos.ProductsSearch;
import com.bixi.bixi.Pojos.UserCreate;
import com.bixi.bixi.Pojos.UserLogin;
import com.bixi.bixi.Pojos.UserSimple;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by telynet on 1/21/2017.
 */

public interface UserService {

    @PUT("user")
    Call<CreateUserResponse> putCreateUser(@Body UserCreate userCreate);

    @PUT("user")
    Call<CreateUserResponse>putCreateUser(@Query("first_name") String nombre, @Query("email") String email, @Query("password") String password, @Query("password_confirm") String password2);

    @POST("login")
    Call<UserLogin> postAttempLogin(@Body UserSimple user);

    @POST("search_products")
    Call<ProductsJson> postProdcuts(@Body ProductsSearch productsSearch);
}
