package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;
import com.ensta.librarymanager.model.*;

public class LivreDetailsServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/livre_details")){
            LivreServiceImpl livreService = LivreServiceImpl.getInstance();
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

            try {
                request.setAttribute("livre", livreService.getById(Integer.parseInt(request.getParameter("id"))));
            } catch (Exception e) {
                new ServletException("ne peux pas obtenir le livre choisi", e);
            }

            try {
                request.setAttribute("currentEmprunts", empruntService.getListCurrentByLivre(Integer.parseInt(request.getParameter("id"))));
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_details.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        try {
            if (request.getParameter("titre") == "" || request.getParameter("titre") == null){
                throw new ServletException("Le titre est vide!");
            } else{
                Livre nouvelleLivre = livreService.getById(Integer.parseInt(request.getParameter("id")));
                nouvelleLivre.setAuteur(request.getParameter("auteur"));
                nouvelleLivre.setTitre(request.getParameter("titre"));
                nouvelleLivre.setIsbn(request.getParameter("isbn"));
                livreService.update(nouvelleLivre);
                request.setAttribute("id", nouvelleLivre.getId());
                request.setAttribute("currentBookings", empruntService.getListCurrentByLivre(nouvelleLivre.getId()));
        
                response.sendRedirect(request.getContextPath() + "/livre_details?id=" + nouvelleLivre.getId());
            }
        } catch (Exception e) {
            new ServletException("Erreur lors de l'envoi de la mise à jour! Le titre est vide! ", e);
            request.setAttribute("errorMessage", "Le titre est vide!");
        }
    }
}
