package com.bixi.bixi.Interfaces;

import android.view.View;

import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsJson;

/**
 * Created by Johnny Gil Mejia on 3/7/17.
 */

public interface RecyclerViewClickListenerHome {
    public void recyclerViewListClicked(View v, int position, ResultProductsJson resultProductsJson);
}
