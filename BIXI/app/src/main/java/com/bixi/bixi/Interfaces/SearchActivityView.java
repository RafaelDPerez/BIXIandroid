package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.Combos.ComboGeneralPojo;

/**
 * Created by Johnny Gil Mejia on 4/9/17.
 */

public interface SearchActivityView {

    void operacionExitosa(ComboGeneralPojo comboGeneralPojo);
    void error(String error);
}
