package com.example;

public class Arret {
    public String nomArret;
    public String code;
    public String latitude;
    public String longitude;
    public String nomVille;
    public String streetView;
    
    
    
    public Arret(){
        
    }

    public String getNomArret(){
        return this.nomArret;
    }

    public String getCod(){
        return this.code;
    }

    public String getLatitude(){
        return this.latitude;
    }
    public String getLongitude(){
        return this.longitude;
    }
    public String getNomVille(){
        return this.nomVille;
    }
    public String getStreetView(){
        return this.streetView;
    }
}
