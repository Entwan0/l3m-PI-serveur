package com.example;
import java.sql.Date;

public class Defi {
    public String id;
    public String titre;
    public Date dateDeCreation;
    public String description;
    public String loginAuteur;
    public String longitude;
    public String latitude;
    public String etape;
    public String indice;
    public String question;
    public String reponse;


    public Defi(){
    }

    public String getId(){
        return this.id;
    }
    public String getTitre(){
        return this.titre;
    }
    public Date getDateDeCreation(){
        return this.dateDeCreation;
    }
    public String getDescription(){
        return this.description;
    }
    public String getLoginAuteur(){
        return this.loginAuteur;
    }

    public String getLatitude(){
        return this.latitude;
    }

    public String getlongitude(){
        return this.longitude;
    }


    public String getEtape(){
        return this.etape;
    }


    public String getIndice(){
        return this.indice;
    }


    public String getQuestion(){
        return this.question;
    }

    public String getReponse(){
        return this.reponse;
    }
}