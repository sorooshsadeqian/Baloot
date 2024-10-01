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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        API api = APIFactory.getInstance();
        LoginUserResponse loginUserResponse = api.loginUser(new LoginUserRequest(username, password));
        if (loginUserResponse.success) {
            Session.getInstance().loginUser(username);
            response.sendRedirect("/home");
            return;
        }

        request.getSession().setAttribute("error", loginUserResponse.errorMessage);
        response.sendRedirect("/400.jsp");
    }
}