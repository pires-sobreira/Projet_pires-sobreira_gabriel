package com.ensta.librarymanager.test;

import com.ensta.librarymanager.persistence.*;
import com.ensta.librarymanager.model.*;
import com.ensta.librarymanager.utils.*;

import java.time.LocalDate;

public class ModeleTest {
    public static void main(String[] args) {
        Membre m1 = new Membre(1, "sobreira", "gabriel", "1, allee des techniques avancees", "gggggg@ensta.fr", "1234-5678", Abonnement.VIP);
        Livre l1 = new Livre(1, "Titre", "Auteur", "isbn");
        Emprunt e1 = new Emprunt(1, m1.getId(), l1.getId(), LocalDate.now(), LocalDate.now().plusDays(7));

        System.out.println(m1.toString());
        System.out.println(l1.toString());
        System.out.println(e1.toString());
    }
}
