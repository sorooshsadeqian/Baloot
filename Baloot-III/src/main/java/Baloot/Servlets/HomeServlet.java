package Baloot.Servlets;

import Baloot.Utils.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(Session.getInstance().userLoggedIn()) {
            HttpSession session = request.getSession();
            session.setAttribute("username", Session.getInstance().getLoggedInUsername());
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("/login");
        }
    }
}