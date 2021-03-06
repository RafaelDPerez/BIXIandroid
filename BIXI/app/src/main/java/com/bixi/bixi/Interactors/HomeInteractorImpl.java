package com.bixi.bixi.Interactors;

import com.bixi.bixi.Interfaces.HomeInteractor;
import com.bixi.bixi.Interfaces.OnHomeOfertasFinishListener;
import com.bixi.bixi.Network.UserService;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsLiketItJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsLikerItJson;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Pojos.Products;
import com.bixi.bixi.Pojos.ProductsSearch;
import com.bixi.bixi.Pojos.UserSimple;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by telynet on 1/5/2017.
 */

public class HomeInteractorImpl implements HomeInteractor {
    UserService service;
    public HomeInteractorImpl(UserService service)
    {
        this.service = service;
    }

    @Override
    public void buscarOferta(final OnHomeOfertasFinishListener listener) {


    }

    @Override
    public void loadProductsFromServer(String token, ProductsSearch productsSearch, final OnHomeOfertasFinishListener listener) {
        service.postProdcuts(token,productsSearch).enqueue(new Callback<ProductsJson>() {
            @Override
            public void onResponse(Call<ProductsJson> call, Response<ProductsJson> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getSceResponseCode() == 0)
                    {
                        listener.ofertasCargadasFromServer(response.body());
                    }else
                    {
                        listener.ofertasError();
                    }

                }else
                {

                    if(response != null && response.body() != null && response.body().getSceResponseMsg() != null)
                    {
                        listener.ofertasError();
                    }else
                    {
                        listener.ofertasError();
                    }

                }
            }

            @Override
            public void onFailure(Call<ProductsJson> call, Throwable t) {
                listener.ofertasError();
            }
        });
    }

    @Override
    public void loadProductsFavoritosFromServer(String token, final OnHomeOfertasFinishListener listener) {
        service.getLiketItOffers(token).enqueue(new Callback<ProductsLiketItJson>() {
            @Override
            public void onResponse(Call<ProductsLiketItJson> call, Response<ProductsLiketItJson> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getSceResponseCode() == 0)
                    {
                        listener.ofertasLiketItCargadasFromServer(response.body());
                    }else
                    {
                        listener.ofertasError();
                    }
                }else
                {
                    listener.ofertasError();
                }
            }

            @Override
            public void onFailure(Call<ProductsLiketItJson> call, Throwable t) {
                listener.ofertasError();
            }
        });
    }

    @Override
    public void likeOffer(String token, ResultProductsLikerItJson obj, final OnHomeOfertasFinishListener listener, final int position) {
        service.setOfferLikeIt(token,obj).enqueue(new Callback<ProductsLiketItJson>() {
            @Override
            public void onResponse(Call<ProductsLiketItJson> call, Response<ProductsLiketItJson> response) {

                if(response.isSuccessful())
                {
                    if(response.body().getSceResponseCode() == 0)
                    {
                        listener.ofertasLiketSuccesfully(true, position);
                    }else
                    {
                        listener.ofertasLiketSuccesfully(false, position);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductsLiketItJson> call, Throwable t) {
                    listener.ofertasLiketSuccesfully(false, position);
            }
        });
    }

    @Override
    public void dislikeOffer(String token, ResultProductsLikerItJson obj, final OnHomeOfertasFinishListener listener, final int position) {
        service.setOfferDislike(token,obj).enqueue(new Callback<ProductsLiketItJson>() {
            @Override
            public void onResponse(Call<ProductsLiketItJson> call, Response<ProductsLiketItJson> response) {
                if(response.isSuccessful())
                {
                    if(response.isSuccessful())
                    {
                        if(response.body().getSceResponseCode() == 0)
                        {
                            listener.ofertasDislikeSuccesfully(true,position);
                        }else
                        {
                            listener.ofertasDislikeSuccesfully(false, position);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductsLiketItJson> call, Throwable t) {
                listener.ofertasDislikeSuccesfully(false, position);
            }
        });

    }


    private List<Oferta> getOferta()
    {
        List<Oferta> oferta = new ArrayList<>();
        oferta.add(new Oferta("Oferta 1", "$500","http://static.viagrupo.com/thumbs/unnamed_3_63_PNG_464x464.png","Local 1","Esto es una prueba"));
        oferta.add(new Oferta("Oferta 2", "$600","http://static.viagrupo.com/thumbs/71207fa5-3a92-478e-b51d-ceab88d760e4_2_PNG_464x464_PNG_464x464.png","Local 2","Esto es una prueba"));
        oferta.add(new Oferta("Oferta 3", "$400","http://static.viagrupo.com/thumbs/unnamed_17_JPG_464x464.jpg","Local 3","Esto es una prueba"));
        oferta.add(new Oferta("Oferta 4", "$200","http://static.viagrupo.com/thumbs/unnamed_1_11_JPG_464x464.jpg","Local 4","Esto es una prueba"));
        oferta.add(new Oferta("Oferta 5", "$100","http://static.viagrupo.com/thumbs/unnamed_11_11_PNG_464x464.png","Local 5","Esto es una prueba"));

        for(int i = 0;i<oferta.size();i++)
        {
            oferta.get(i).setImages(getImgsUrl());
        }

        return oferta;
    }

    private List<String> getImgsUrl()
    {
        List<String> imgs = new ArrayList<String>();
        imgs.add("http://static.viagrupo.com/thumbs/unnamed_3_63_PNG_464x464.png");
        imgs.add("http://static.viagrupo.com/thumbs/71207fa5-3a92-478e-b51d-ceab88d760e4_2_PNG_464x464_PNG_464x464.png");
        imgs.add("http://static.viagrupo.com/thumbs/unnamed_17_JPG_464x464.jpg");
        imgs.add("http://static.viagrupo.com/thumbs/unnamed_1_11_JPG_464x464.jpg");
        imgs.add("http://static.viagrupo.com/thumbs/unnamed_11_11_PNG_464x464.png");
        return imgs;
    }
}
