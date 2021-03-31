package com.ensta.librarymanager.model;

import java.util.Date;
import java.time.LocalDate;

public class Emprunt {
    private static int ID = 0;
    private int id;
    private int idMembre;
    private int idLivre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    //Constructeur
    public Emprunt(int id, int idMembre, int idLivre, LocalDate dateEmprunt, LocalDate dateRetour){
        this.id = id;
        this.idMembre = idMembre;
        this.idLivre = idLivre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public Emprunt(int idMembre, int idLivre, LocalDate dateEmprunt, LocalDate dateRetour){
        ID++;
        this.id = ID;
        this.idMembre = idMembre;
        this.idLivre = idLivre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public Emprunt(){}

    //Getters
    public int getId(){
        return id;
    }
    public int getIdMembre(){
        return idMembre;
    }
    public int getIdLivre(){
        return idLivre;
    }
    public LocalDate getDateEmprunt(){
        return dateEmprunt;
    }
    public LocalDate getDateRetour(){
        return dateRetour;
    }

    //Setters
    public void setId(int id){
        this.id = id;
    }
    public void setIdMembre(int idMembre){
        this.idMembre = idMembre;
    }
    public void setIdLivre(int idLivre){
        this.idLivre = idLivre;
    }
    public void setDateEmprunt(LocalDate dateEmprunt){
        this.dateEmprunt = dateEmprunt;
    }
    public void setDateRetour(LocalDate dateRetour){
        this.dateRetour = dateRetour;
    }

    //tostring
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return ("------------------------------------" + "\n" +
                "Emprunt info" + "\n" +
                "id: " + id + "\n" +
                "idMembre: " + idMembre + "\n"+
                "idLivre: " + idLivre + "\n" +
                "dateEmprunt: " + dateEmprunt + "\n" +
                "dateRetour: " + dateRetour + "\n" +
                "------------------------------------" + "\n");
    }
}
