package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;

public class EmpruntAddServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        
        if (action.equals("/emprunt_add")){
            MembreServiceImpl membreService = MembreServiceImpl.getInstance();
            try {
                request.setAttribute("membreDisponibleList", membreService.getListMembreEmpruntPossible());
            } catch (Exception e) {
                e.printStackTrace();
            }

            LivreServiceImpl livreService = LivreServiceImpl.getInstance();
            try {
                request.setAttribute("livreDisponibleList", livreService.getListDispo());
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        try {
            if (request.getParameter("idMembre") == null || request.getParameter("idLivre") == null){
                throw new ServletException("Impossible d'ajouter un nouveau prêt, certaines données n'ont pas été reçues ");
            } else{
                empruntService.create(Integer.parseInt(request.getParameter("idMembre")), Integer.parseInt(request.getParameter("idLivre")), LocalDate.now());
						
				request.setAttribute("empruntList", empruntService.getListCurrent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        MembreServiceImpl membreService = MembreServiceImpl.getInstance();        
        try {
            request.setAttribute("membreDisponibleList", membreService.getListMembreEmpruntPossible());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LivreServiceImpl livreService = LivreServiceImpl.getInstance();     
        try {
            request.setAttribute("livreDisponibleList", livreService.getListDispo());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/emprunt_list");
    }
}
