package com.bixi.bixi.Pojos;

/**
 * Created by Johnny Gil Mejia on 3/5/17.
 */

public class SimpleMenuPojo {

    private String label;
    private String labelIcon;

    public SimpleMenuPojo()
    {

    }

    public SimpleMenuPojo(String label, String labelIcon) {
        this.label = label;
        this.labelIcon = labelIcon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabelIcon() {
        return labelIcon;
    }

    public void setLabelIcon(String labelIcon) {
        this.labelIcon = labelIcon;
    }


}
