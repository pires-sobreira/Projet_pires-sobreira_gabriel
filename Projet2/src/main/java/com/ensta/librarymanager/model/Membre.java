package com.ensta.librarymanager.model;

public class Membre {
    private static int ID = 0;
    private int id;
    private String nom;
    private String prenom;
    private String adress;
    private String email;
    private String telephone;
    private Abonnement abonnement;
    private int i;

    //Constructors
    public Membre(int id, String nom, String prenom, String adress, String email, String telephone, Abonnement abonnement){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adress = adress;
        this.email = email;
        this.telephone = telephone;
        this.abonnement = abonnement;
    }
    public Membre(String nom, String prenom, String adress, String email, String telephone, Abonnement abonnement){
        i = ID++;
        this.id = i;
        this.nom = nom;
        this.prenom = prenom;
        this.adress = adress;
        this.email = email;
        this.telephone = telephone;
        this.abonnement = abonnement;
    }
    public Membre(){
        this.id = 0;
        this.nom = "";
        this.prenom = "";
        this.adress = "";
        this.email = "";
        this.telephone = "";
        this.abonnement = null;
    }

    //Getters
    public int getId(){
        return id;
    }
    public String getNom(){
        return nom;
    }
    public String getPrenom(){
        return prenom;
    }
    public String getAdress(){
        return adress;
    }
    public String getEmail(){
        return email;
    }
    public String getTelephone(){
        return telephone;
    }
    public Abonnement getAbonement(){
        return abonnement;
    }

    //Setters
    public void setId(int newId){
        this.id = newId;
    }
    public void setNom(String newNom){
        this.nom = newNom;
    }
    public void setPrenom(String newPrenom){
        this.prenom = newPrenom;
    }
    public void setAdress(String newAdress){
        this.adress = newAdress;
    }
    public void setEmail(String newEmail){
        this.email = newEmail;
    }
    public void setTelephone(String newTelephone){
        this.telephone = newTelephone;
    }
    public void setAbonnement(Abonnement newAbonnement){
        this.abonnement = newAbonnement;
    }

    //Tostring
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return ("------------------------------------" + "\n" +
                "Membre info" + "\n" +
                "id: " + id + "\n" +
                "nom: " + nom + "\n"+
                "prenom: " + prenom + "\n" +
                "adress: " + adress + "\n" +
                "email: " + email + "\n" +
                "telephone: " + telephone + "\n" +
                "Abonnement: " + abonnement + "\n" +
                "------------------------------------" + "\n");
    }
}
