package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.HistorialPojo;

/**
 * Created by Johnny Gil Mejia on 3/27/17.
 */

public interface OnHistoricoFinishListener {
    void historicalError();
    void loadSuccesfully(HistorialPojo pojo);
}
