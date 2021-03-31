package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;

public class LivreDeleteServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/livre_delete")){
            LivreServiceImpl livreService = LivreServiceImpl.getInstance();
            int id = -1;
            if (request.getParameter("id") != null){
                id = Integer.parseInt(request.getParameter("id"));
            }

            try {
                if (id != -1){
                    request.setAttribute("book", livreService.getById(id));
                    request.setAttribute("id", id);
                } else{

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_delete.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();

        try {
            livreService.delete(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/livre_list");
    }
}
