package com.example.plantilla.ui.tab.models;

public class AndroidFlavor {
    public String versionName;
    public String versionNumber;
    public int image; // drawable reference id

    public AndroidFlavor(String vName, String vNumber, int image)
    {
        this.versionName = vName;
        this.versionNumber = vNumber;
        this.image = image;
    }

}