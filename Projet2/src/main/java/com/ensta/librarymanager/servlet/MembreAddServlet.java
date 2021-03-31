package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;
import com.ensta.librarymanager.model.*;

public class MembreAddServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/membre_add")){
            MembreServiceImpl membreService = MembreServiceImpl.getInstance();
            try {
                request.setAttribute("availableMemberList", membreService.getList());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Submit gathered information th the appropriate .jsp:
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
            dispatcher.forward(request, response);
        }
    }   
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        
        try {
            if (request.getParameter("nom") == null || request.getParameter("prenom") == null || request.getParameter("nom") == "" || request.getParameter("prenom") == "" ){
                throw new ServletException("Cant load because FIrst or Last were empties");
            }else{
                int id = membreService.create(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("adresse"), request.getParameter("email"), request.getParameter("telephone"), Abonnement.BASIC);
                request.setAttribute("id", id);
                request.setAttribute("loanList", empruntService.getListCurrentByMembre(id));
                response.sendRedirect(request.getContextPath() + "/membre_details?id=" + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
