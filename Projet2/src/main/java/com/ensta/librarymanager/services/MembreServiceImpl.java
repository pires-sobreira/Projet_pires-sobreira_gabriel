package com.ensta.librarymanager.services;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.model.*;

public class MembreServiceImpl implements MembreService{
    private static MembreServiceImpl instance;
    private MembreServiceImpl(){};
    public static MembreServiceImpl getInstance(){
        if (instance == null)   instance = new MembreServiceImpl();
        return instance;
    }
    
    public List<Membre> getList() throws ServiceException{
        List<Membre> empruntAllList = new ArrayList<Membre>();
        MembreDaoImpl memberDao = MembreDaoImpl.getInstance();

        try {
            empruntAllList = memberDao.getList();

            System.out.println("\n\tTout membre list: " + empruntAllList);
        } catch (Exception e) {
            throw new ServiceException("Impossible d'obtenir la liste totale!\n", e);
        }

        return empruntAllList;
    };

    public List<Membre> getListMembreEmpruntPossible() throws ServiceException{
        List<Membre> membresEmpruntDispo = new ArrayList<Membre>();
        List<Membre> membres = new ArrayList<Membre>();
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        try {
            membres = membreDao.getList();
            for (int i = 0; i < membres.size(); i++){
                if (empruntService.isEmpruntPossible(membres.get(i))){
                    membresEmpruntDispo.add(membres.get(i));
                }
            }

            System.out.println("\n\ttous les emprunts possibles : " + membresEmpruntDispo);
        } catch (Exception e) {
            throw new ServiceException("Impossible d'obtenir des réservations possibles par membre!\n", e);
        }

        return membresEmpruntDispo;
    };

    public Membre getById(int id) throws ServiceException{
        Membre choisiUn = new Membre();
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();

        try {
            choisiUn = membreDao.getById(id);

            System.out.println("\n\tMembre choisi: " + choisiUn);
        } catch (Exception e) {
            throw new ServiceException("Impossible d'obtenir un membre individuel!\n", e);
        }

        return choisiUn;
    };

    public int create(String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) throws ServiceException{
        int id = -1;
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();

        try {
            if (nom == null || prenom == null || nom == "" || prenom == ""){
                throw new ServiceException("Les prénoms ou noms se vident! Impossible de créer ");
            } else{
                nom = nom.toUpperCase();
                prenom = prenom.toUpperCase();
                
                id = membreDao.create(nom, prenom, adresse, email, telephone, abonnement);
                System.out.println("\n\tNouvelle membre ID: " + id);
            }
        } catch (Exception e) {
            throw new ServiceException("ne peux pas créer!\n", e);
        }

        return id;
    };

    public void update(Membre membre) throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();

        try {
            if(membre.getPrenom() == null || membre.getNom() == null || membre.getPrenom() == "" || membre.getNom() == ""){
                throw new ServiceException("Les prénoms ou noms se vident! Impossible de mettre à jour");
            } else{
                membre.setNom(membre.getNom().toUpperCase());
                membreDao.update(membre);

                System.out.println("\n\tMembre " + membre + "mise à jour réussie!");
            }
        } catch (Exception e) {
            throw new ServiceException("Ne peut pas être mis à jour!\n", e);
        }
    };

    public void delete(int id) throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();

        try {
            membreDao.delete(id);
            System.out.println("\n\tMembre " + id + "mise à jour réussie!");
        } catch (Exception e) {
            throw new ServiceException("Ne peut pas être supprimé!\n", e);
        }
    };

    public int count() throws ServiceException{
        int totale = -1;
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();

        try {
            totale = membreDao.count();
            System.out.println("\n\tNombre totale de membres: " + totale);
        } catch (Exception e) {
            throw new ServiceException("ne peux pas tout compter!\n", e);
        }

        return totale;
    };
}
