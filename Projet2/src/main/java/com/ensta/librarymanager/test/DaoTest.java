package com.ensta.librarymanager.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.*;

import com.ensta.librarymanager.utils.FillDatabase;

public class DaoTest {
    public static void main(String[] args) throws Exception{
        FillDatabase.main(args);


        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();

        List<Membre> tousMembre = membreDao.getList();
        System.out.println("Tous les membres dans la base de données: " + tousMembre);

        Membre membreById = membreDao.getById(3);
        System.out.println("Membre avec Id "+ 3 + "la base de données est: " + membreById);

        int idMembre = membreDao.create("sobreira", "gabriel", "1, allee des techniques avancees", "gggggg@ensta.fr", "1234-5678", Abonnement.VIP);
        System.out.println("\n\tNouvelle id: " + idMembre);

        // Membre membreUpdate = new Membre(12, "Vieira", "Maria Paula", "maria-paula.vieira@ensta-paris.fr", "+33000", "1,Allee des techniques avancees", Abonnement.PREMIUM);
        // membreDao.update(membreUpdate);
        // List<Membre> tousMembreMJ = membreDao.getList();
        // System.out.println("\n\tmise a jour list: " + membreUpdate);

        membreDao.delete(4);
        List<Membre> tousMembreD = membreDao.getList();
        System.out.println("\n\tmise a jour list deleted: " + tousMembreD);

        int nombreMembres = membreDao.count();
        System.out.println("\n\tNombre de membres dans la base de données: " + nombreMembres);




        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();

        List<Livre> livreList = new ArrayList<Livre>();
        livreList = livreDao.getList();
        System.out.println("Livre list complet: " + livreList);

        Livre LivreByID = livreDao.getById(3);
        System.out.println("\n\tLivre By ID: " + LivreByID);

        int idNouveau = livreDao.create("The art of programation", "Joseph", "555331");
        System.out.println("\n\tNouvelle livre id: " + idNouveau);

        Livre livreMJ = new Livre(4, "The art of programation", "Joseph", "555331");
        livreDao.update(livreMJ);
        livreList = livreDao.getList();
        System.out.println("\n\tLivre list mise à jour: " + livreList);

        livreDao.delete(1);
        livreList = livreDao.getList();
        System.out.println("\n\tListe livre delete: " + livreList);

        int totalCurrentLivres = livreDao.count();
        System.out.println("\n\tTotal books in DB: " + totalCurrentLivres);




        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

        List<Emprunt> empruntList = new ArrayList<Emprunt>();
        empruntList = empruntDao.getList();
        System.out.println("\n\tlist emprunt total: " + empruntList);

        empruntList = empruntDao.getListCurrent();
        System.out.println("\n\temprunt current list: " + empruntList);

        empruntList = empruntDao.getListCurrentByMembre(5);
        System.out.println("\n\temprunt Current list par membre: " + empruntList);

        empruntList = empruntDao.getListCurrentByLivre(2);
        System.out.println("\n\temprunt current list par livre: " + empruntList);

        Emprunt empruntById = empruntDao.getById(6);
        System.out.println("\n\temprunt par id: " + empruntById);

        empruntDao.create(1, 4, LocalDate.now());
        empruntList = empruntDao.getList();
        System.out.println("\n\tlist emprunt total create: " + empruntList);

        Livre LivreTest = new Livre("The art of programation", "Joseph", "555331");
        Membre membreTest = new Membre("Vellone", "Fabricio", "fabricio.vellone@ensta-paris.fr", "+330766625959", "Allée des techniques avancées", Abonnement.VIP);
        Emprunt empruntTest = new Emprunt(2, membreTest.getId(), LivreTest.getId(), LocalDate.now(), LocalDate.now().plusDays(7));
        empruntDao.update(empruntTest);
        empruntList = empruntDao.getList();
        System.out.println("\n\tlist emprunt update: " + empruntList);

        int total = empruntDao.count();
        System.out.println("\n\tnombre d'emprunts dans la base des données: " + total);
    }
}
