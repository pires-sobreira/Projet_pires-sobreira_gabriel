package com.ensta.librarymanager.services;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;

import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.model.*;

public class EmpruntServiceImpl {
    private static EmpruntServiceImpl instance;
    private EmpruntServiceImpl(){};
    public static EmpruntServiceImpl getInstance(){
        if (instance == null)   instance = new EmpruntServiceImpl();
        return instance;
    }

    public List<Emprunt> getList() throws ServiceException{
        List<Emprunt> toutEmprunt = new ArrayList<Emprunt>();
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

        try {
            toutEmprunt = empruntDao.getList();

            System.out.println("\n\tTout emprunt list: " + toutEmprunt);
        } catch (Exception e) {
            throw new ServiceException("Impossible d'obtenir la liste totale!\n", e);
        }

        return toutEmprunt;
    };

    public List<Emprunt> getListCurrent() throws ServiceException{
        List<Emprunt> empruntList = new ArrayList<Emprunt>();
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

        try {
            empruntList = empruntDao.getListCurrent();

            System.out.println("\n\tTous les prêts courants non encore remboursés: " + empruntList);
        } catch (Exception e) {
            throw new ServiceException("Impossible d'obtenir la liste actuelle!\n", e);
        }

        return empruntList;
    };

    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException{
        List<Emprunt> currentByMembreLists = new ArrayList<Emprunt>();
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

        try {
            currentByMembreLists = empruntDao.getListCurrentByMembre(idMembre);

            System.out.println("\n\tTous les prêts courants non encore remboursés par le membre : " + currentByMembreLists);
        } catch (Exception e) {
            throw new ServiceException("Impossible d'obtenir la liste actuelle par membre!\n", e);
        }

        return currentByMembreLists;
    };

    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException{
        List<Emprunt> empruntList = new ArrayList<Emprunt>();
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

        try {
            empruntList = empruntDao.getListCurrentByLivre(idLivre);

            System.out.println("\n\tTous les prêts courants non encore remboursés par livres: " + empruntList);
        } catch (Exception e) {
            throw new ServiceException("Impossible d'obtenir la liste actuelle par livre!\n", e);
        }

        return empruntList;
    };

    public Emprunt getById(int id) throws ServiceException{
        Emprunt choisiEmprunt = new Emprunt();
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

        try {
            choisiEmprunt = empruntDao.getById(id);

            System.out.println("\n\tObtenir par ID : " + choisiEmprunt);
        } catch (Exception e) {
            throw new ServiceException("Impossible d'obtenir cette réservation spécifique!\n", e);
        }
        return choisiEmprunt;
    };

    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException{
        
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

        try {
            empruntDao.create(idMembre, idLivre, dateEmprunt);

            System.out.println("\n\t\nCréer un nouveau prêt avec succès!\n");
        } catch (Exception e) {
            throw new ServiceException("Ne peut pas être créé!\n", e);
        }
    };

    public void returnBook(int id) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

        try {
            Emprunt update = empruntDao.getById(id);
            update.setDateRetour(LocalDate.now());
            empruntDao.update(update);

            System.out.println("\n\tEmprunt " + update + "mise à jour réussie! Livre retourné!");
        } catch (Exception e) {
            throw new ServiceException("Ne peut pas encore être retourné!\n", e);
        }
    };

    public int count() throws ServiceException{
        int totale = -1;
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

        try {
            totale = empruntDao.count();

            System.out.println("\n\tTotale emprunt: " + totale);
        } catch (Exception e) {
            throw new ServiceException("Ne peut pas être compté!\n", e);
        }
        return totale;
    };

    public boolean isLivreDispo(int idLivre) throws ServiceException{
        boolean disponible = false;
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

        try {
            disponible = empruntDao.getListCurrentByLivre(idLivre).isEmpty();
            System.out.println("\n\tStatut du livre choisi: " + disponible);

            return disponible;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return disponible;
    };

    public boolean isEmpruntPossible(Membre membre) throws ServiceException{
        boolean disponible = false;
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

        try {
            disponible = empruntDao.getListCurrentByMembre(membre.getId()).isEmpty();
            System.out.println("\n\tThe member can get another book?: " + disponible);

            return disponible;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return disponible;
    };
}
