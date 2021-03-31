package com.ensta.librarymanager.test;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;

import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.*;
import com.ensta.librarymanager.utils.FillDatabase;
import com.ensta.librarymanager.services.*;

public class ServiceTest {
    public static void main(String[] args) throws Exception{
        FillDatabase.main(args);


        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        livreService.getList();
        livreService.getListDispo();
        livreService.getById(10);
        livreService.create("Halloween", "auteur", "215151651");
        // livreService.create("", "auteur", "215151651");
        Livre livreTest = new Livre(2, "The art of programation", "Joseph", "555331");
        livreService.update(livreTest);
        // Livre livreTest2 = new Livre(2, "", "Joseph", "555331");
        // livreService.update(livreTest2);
        livreService.delete(10);
        livreService.count();


        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        membreService.getList();
        membreService.getListMembreEmpruntPossible();
        membreService.getById(6);
        membreService.create("sobreira", "gabriel", "1, allee des techniques avancees", "gggggg@ensta.fr", "1234-5678", Abonnement.VIP);
        // membreService.create("sobreira", "", "1, allee des techniques avancees", "gggggg@ensta.fr", "1234-5678", Abonnement.VIP);
        // membreService.create("", "gabriel", "1, allee des techniques avancees", "gggggg@ensta.fr", "1234-5678", Abonnement.VIP);
        Membre membreTest = new Membre(4, "sobreira", "gabriel", "1, allee des techniques avancees", "gggggg@ensta.fr", "1234-5678", Abonnement.VIP);
        membreService.update(membreTest);
        // Membre membreTest2 = new Member(4, "Vieira", "Maria Paula", "1,Allee des techniques avancees", "maria-paula.vieira@ensta-paris.fr", "+33000", Abonnement.PREMIUM);
        // membreService.update(membreTest2);
        membreService.delete(4);
        membreService.count();


        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        empruntService.getList();
        empruntService.getListCurrent();
        empruntService.getListCurrentByMembre(4);
        empruntService.getListCurrentByLivre(1);
        empruntService.getById(3);
        empruntService.create(3, 2, LocalDate.now());
        empruntService.returnBook(1);
        empruntService.count();
        empruntService.isLivreDispo(4);
        empruntService.isEmpruntPossible(membreTest);
    }
    
}
