package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;
import com.ensta.librarymanager.model.*;

public class MembreDetailsServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/membre_details")){
            MembreServiceImpl membreService = MembreServiceImpl.getInstance();
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

            try {
                request.setAttribute("member", membreService.getById(Integer.parseInt(request.getParameter("id"))));
            } catch (Exception e) {
                new ServletException("Cant get the chosen member", e);
            }

            try {
                request.setAttribute("currentByMember", empruntService.getListCurrentByMembre(Integer.parseInt(request.getParameter("id"))));
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/membre_details.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        try {
            if (request.getParameter("prenom") == "" || request.getParameter("prenom") == null || request.getParameter("nom") == "" || request.getParameter("nom") == null){
                throw new ServletException("First or Last names are empties!");
            } else{
                Membre nouvelleMembre = membreService.getById(Integer.parseInt(request.getParameter("id")));
                nouvelleMembre.setPrenom(request.getParameter("prenom"));
                nouvelleMembre.setNom(request.getParameter("nom"));
                nouvelleMembre.setEmail(request.getParameter("email"));
                nouvelleMembre.setTelephone(request.getParameter("telephone"));
                if (request.getParameter("abonnement").equals("BASIC")){
                    nouvelleMembre.setAbonnement(Abonnement.BASIC);
                } else if (request.getParameter("abonnement").equals("PREMIUM")){
                    nouvelleMembre.setAbonnement(Abonnement.PREMIUM);
                } else if (request.getParameter("abonnement").equals("VIP")){
                    nouvelleMembre.setAbonnement(Abonnement.VIP);
                } 
                membreService.update(nouvelleMembre);
                request.setAttribute("id", nouvelleMembre.getId());
                request.setAttribute("currentByMember", empruntService.getListCurrentByMembre(nouvelleMembre.getId()));
                
                
                response.sendRedirect(request.getContextPath() + "/membre_details?id=" + nouvelleMembre.getId());
            }
        } catch (Exception e) {
            new ServletException("Erreur lors de l'envoi de la mise à jour!", e);
            request.setAttribute("errorMessage", "Paramètre vide ");
        }

    }
}
