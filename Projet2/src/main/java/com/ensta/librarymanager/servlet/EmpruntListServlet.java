package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;

public class EmpruntListServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        
        if (action.equals("/emprunt_list")) {
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            
            try {
                if (request.getParameter("show") != null && request.getParameter("show").contains("all")){
                    request.setAttribute("loanList", empruntService.getList());
                    request.setAttribute("show", "all");
                }else {
                    request.setAttribute("loanList", empruntService.getListCurrent());
                    request.setAttribute("show", "current");
                }
                    
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Submit gathered information th the appropriate .jsp:
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
            dispatcher.forward(request, response);

        }
    } 
}
