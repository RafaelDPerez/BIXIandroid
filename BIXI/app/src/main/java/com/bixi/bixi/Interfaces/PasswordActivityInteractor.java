package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.OffersPointsClaim;

/**
 * Created by Johnny Gil Mejia on 3/27/17.
 */

public interface PasswordActivityInteractor {
    void reclaimOffersFromServer(String token, OffersPointsClaim obj,OnFinishListener listener);
    void addPointOffersToServer(String token, OffersPointsClaim obj,OnFinishListener listener);
}
