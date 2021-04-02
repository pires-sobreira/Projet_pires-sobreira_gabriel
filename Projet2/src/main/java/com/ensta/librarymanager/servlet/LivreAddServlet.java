package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;

public class LivreAddServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/livre_add")){
            LivreServiceImpl livreService = LivreServiceImpl.getInstance();
            try {
                request.setAttribute("membreDisponibleList", livreService.getListDispo());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Submit gathered information th the appropriate .jsp:
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");
            dispatcher.forward(request, response);
        }
    }   
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        
        try {
            if (request.getParameter("titre") == null){
                throw new ServletException("Impossible de charger car le titre était vide ");
            }else{
                int id = livreService.create(request.getParameter("titre"), request.getParameter("auteur"), request.getParameter("isbn"));
                request.setAttribute("id", id);

                response.sendRedirect(request.getContextPath() + "/livre_details?id=" + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
