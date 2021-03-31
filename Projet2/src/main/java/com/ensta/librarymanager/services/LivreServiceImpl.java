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

public class LivreServiceImpl implements LivreService{
    private static LivreServiceImpl instance;
    private LivreServiceImpl(){};
    public static LivreServiceImpl getInstance(){
        if (instance == null) instance = new LivreServiceImpl();
        return instance;
    }

    public List<Livre> getList() throws ServiceException{
        List<Livre> listComplet  = new ArrayList<Livre>();
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();

        try {
            listComplet = livreDao.getList();

            System.out.println("\n\tList complet de livres: " + listComplet);
        } catch (Exception e) {
            throw new ServiceException("Pas possible d'avoir la liste complet!\n", e);
        }

        return listComplet;
    };
    public List<Livre> getListDispo() throws ServiceException{
        List<Livre> livresDispo  = new ArrayList<Livre>();
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        
        try {
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            List<Livre> tousLivres  = new ArrayList<Livre>();
            tousLivres = livreDao.getList();
            
            for (int i = 0; i < tousLivres.size(); i++) {
                if (empruntService.isLivreDispo(tousLivres.get(i).getId()))
                    livresDispo.add(tousLivres.get(i));
            }
            
            System.out.println("\n\tTous les livres disponibles pour emprunt: " + livresDispo);
        } catch (Exception e) {
            throw new ServiceException("Can't get disponible list!\n", e);
        }

        return livresDispo;
    };

    public Livre getById(int id) throws ServiceException{
        Livre livreChoisi = new Livre();
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();

        try {
            livreChoisi = livreDao.getById(id);

            System.out.println("\n\tLe livre qui a été choisi est: " + livreChoisi);
        } catch (Exception e) {
            throw new ServiceException("Pas possible trouver le livre choisi!\n", e);
        }

        return livreChoisi;
    };

    public int create(String titre, String auteur, String isbn) throws ServiceException{
        int id = -1;
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();

        try {
            if (titre == null || titre == ""){
                throw new ServiceException("il n'y a pas de titre! pas possible crée");
            } else{
                id = livreDao.create(titre, auteur, isbn);
                System.out.println("\n\tNouvelle livre crée, id: " + id);
            }

        } catch (Exception e) {
            throw new ServiceException("Pas possible cree le livre!\n", e);
        }

        return id;
    };

    public void update(Livre livre) throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();

        try {
            if (livre.getTitre() == null || livre.getTitre() == ""){
                throw new ServiceException("il n'y a pas de titre! pas possible mise à jour");
            } else{
                livreDao.update(livre);
                System.out.println("\n\tLe livre " + livre + " a été crée!");
            }
            
        } catch (Exception e) {
            throw new ServiceException("Pas possible faire le mise a jour!\n", e);
        }
    };

    public void delete(int id) throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();

        try {
            livreDao.delete(id);

            System.out.println("\n\tLe livre avec id: " + id + " a été delete!");
        } catch (Exception e) {
            throw new ServiceException("Can't be deleted!\n", e);
        }
    };

    public int count() throws ServiceException{
        int totale = -1;
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();

        try {
            totale = livreDao.count();

            System.out.println("\n\tNombre de livres dans la base de données: " + totale);
        } catch (Exception e) {
            throw new ServiceException("Ne peut pas être compté!\n", e);
        }

        return totale;
    };
}
