package com.example;
import java.sql.Date;

public class Visites{
    public String idVisite;
    public String idDefis;
    public String nomVisiteur;
    public Date dateVisite;
    public String mode;
    public String score;
    public String temps;
    public String status;

    public Visites(){
    }

    public String getIdVisite(){
        return this.idVisite;
    }
    public String getIdDefis(){
        return this.idDefis;
    }
    public String getNomVisiteur(){
        return this.nomVisiteur;
    }
    public Date getDateVisite(){
        return this.dateVisite;
    }
    public String getMode(){
        return this.mode;
    }
    public String getScore(){
        return this.score;
    }
    public String getTemps(){
        return this.temps;
    }
    public String getStatus(){
        return this.status;
    }
}