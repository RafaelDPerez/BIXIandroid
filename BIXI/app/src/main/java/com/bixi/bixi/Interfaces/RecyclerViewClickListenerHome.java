package com.bixi.bixi.Interfaces;

import android.view.View;

import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsLikerItJson;

/**
 * Created by Johnny Gil Mejia on 3/7/17.
 */

public interface RecyclerViewClickListenerHome {
    void recyclerViewListClicked(View v, int position, ResultProductsJson resultProductsJson);
    void recyclerViewClicked(View v, int position);
    void recyclerViewRemoveItem(View v, int position);
    void recyclerViewLiketItem(View v, int position, ResultProductsJson  resultProductsJson);
}
