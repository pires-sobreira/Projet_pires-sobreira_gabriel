package com.ensta.librarymanager.model;

public class Livre {
    private static int ID = 0;
    private int id;
    private String titre;
    private String auteur;
    private String isbn;

    //Constructeurs
    public Livre(int id, String titre, String auteur, String isbn){
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
    }
    public Livre(String titre, String auteur, String isbn){
        ID++;
        this.id = ID;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
    }
    public Livre(){
        this.id = 0;
        this.titre = "";
        this.auteur = "";
        this.isbn = "";
    }

    //Getters
    public int getId(){
        return id;
    }
    public String getTitre(){
        return titre;
    }
    public String getAuteur(){
        return auteur;
    }
    public String getIsbn(){
        return isbn;
    }

    //Setters
    public void setId(int id){
        this.id = id;
    }
    public void setTitre(String titre){
        this.titre = titre;
    }
    public void setAuteur(String auteur){
        this.auteur = auteur;
    }
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    //tostring
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return ("------------------------------------" + "\n" +
                "Livre info" + "\n" +
                "id: " + id + "\n" +
                "titre: " + titre + "\n"+
                "auteur: " + auteur + "\n" +
                "isbn: " + isbn + "\n" +
                "------------------------------------" + "\n");
    }
}
