package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;

public class DashboardServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet (final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String action = request.getServletPath();

        if (action.equals("/dashboard")) {
            final MembreServiceImpl membreService = MembreServiceImpl.getInstance();
            try {
                request.setAttribute("numberOfMembres", membreService.count());
            } catch (final Exception e) {
                e.printStackTrace();
            }

            final LivreServiceImpl livreService = LivreServiceImpl.getInstance();
            try {
                request.setAttribute("numberOfBooks", livreService.count());
            } catch (final Exception e) {
                e.printStackTrace();
            }

            final EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            try {
                request.setAttribute("numberOfLoans", empruntService.count());
                request.setAttribute("currentLoans", empruntService.getListCurrent());
            } catch (final Exception e) {
                e.printStackTrace();
            }

            final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
            dispatcher.forward(request, response);
            
        }


    }
}
