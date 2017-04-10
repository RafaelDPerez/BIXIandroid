package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.Combos.ComboGeneralPojo;

/**
 * Created by Johnny Gil Mejia on 4/9/17.
 */

public interface OnComboFinishListener {
    void typesCommerceLoadSuccesfullyFromServer(ComboGeneralPojo comboGeneralPojo);
    void typesCommerceLoadUnsuccedfullyFromServer(String error);
}
