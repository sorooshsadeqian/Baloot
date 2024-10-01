package Baloot.Servlets;

import Baloot.Api.API;
import Baloot.Api.APIFactory;
import Baloot.Api.Requests.LoginUserRequest;
import Baloot.Api.Responses.LoginUserResponse;
import Baloot.Utils.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session.getInstance().logoutUser();
        response.sendRedirect("/login");
    }
}