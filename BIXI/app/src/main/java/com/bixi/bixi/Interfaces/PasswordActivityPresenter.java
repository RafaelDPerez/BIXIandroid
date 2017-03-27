package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.OffersPointsClaim;

/**
 * Created by Johnny Gil Mejia on 3/27/17.
 */

public interface PasswordActivityPresenter {
    void reclaimOffer(String token, OffersPointsClaim obj);
    void addPointsOffer(String token, OffersPointsClaim onj);
}
