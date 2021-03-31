package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;
import com.ensta.librarymanager.model.*;

public class MembreDeleteServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/membre_delete")){
            MembreServiceImpl membreService = MembreServiceImpl.getInstance();
            int id = -1;
            if (request.getParameter("id") != null){    
                id = Integer.parseInt(request.getParameter("id"));
            }

            try {
                if (id != -1){
                    request.setAttribute("membre", membreService.getById(id));
                    request.setAttribute("id", id);
                } else{

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/membre_delete.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

        try {
            membreService.delete(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/membre_list");
    }
}
