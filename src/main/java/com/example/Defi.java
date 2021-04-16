package com.example;
import java.sql.Date;

public class Defi {
    public String id;
    public String titre;
    public Date dateDeCreation;
    public String description;
    public String loginAuteur;
    public String latitude;
    public String longitude;

    public Defi(){
    }

    public String getId(){
        return id;
    }
    public String getTitre(){
        return titre;
    }
    public Date getDateDeCreation(){
        return dateDeCreation;
    }
    public String getDescription(){
        return description;
    }
    public String getLoginAuteur(){
        return loginAuteur;
    }

    public String getLatitude(){
        return latitude;
    }

    public String getlongitude(){
        return longitude;
    }
}