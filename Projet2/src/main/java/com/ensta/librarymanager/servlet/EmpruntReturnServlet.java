package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;

public class EmpruntReturnServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if(action.equals("/emprunt_return")){
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            int id = -1;

            if (request.getParameter("id") != null){
                id = Integer.parseInt(request.getParameter("id"));
            } 

            try {
                if (id != -1){
                    request.setAttribute("id", id);
                    request.setAttribute("currentLoan", empruntService.getListCurrent());
                } else{
                    request.setAttribute("currentLoan", empruntService.getListCurrent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        try {
            if (request.getParameter("id") == null){
                throw new ServletException("impossible d'obtenir le numéro d'identification!");
            } else{
                empruntService.returnBook(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("currentLoan", empruntService.getListCurrent());
            }
        } catch (Exception e) {
            throw new ServletException("ne peux pas envoyer!", e);
        }

        response.sendRedirect(request.getContextPath() + "/emprunt_list");
    }
}
