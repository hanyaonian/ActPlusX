package com.example.dell.actplus;

import android.graphics.Bitmap;

/**
 * Created by DELL on 2017/4/19.
 */

public class ActItem {
    private Bitmap image;
    private String ActName, ActTime, ActPlace;
    private int ActId;
    public ActItem(String actName, String actTime, int actId, String actPlace) {
        ActName = actName;
        ActId = actId;
        ActTime = actTime;
        ActPlace = actPlace;
    }
    public void SetImage(Bitmap bitmap) {
        if (bitmap != null) {
            image = bitmap;
        }
    }
    public Bitmap getImage() {
        return image;
    }

    public int getId() {
        return ActId;
    }
    public String getTime() { return ActTime;}
    public String getTitle(){ return ActName;}
    public String getActPlace() {return ActPlace;}
}
