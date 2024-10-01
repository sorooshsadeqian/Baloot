package Baloot.Servlets;

import Baloot.Api.API;
import Baloot.Api.APIFactory;
import Baloot.Api.Requests.AddCreditRequest;
import Baloot.Api.Requests.GetUserRequest;
import Baloot.Api.Requests.LoginUserRequest;
import Baloot.Api.Responses.AddCreditResponse;
import Baloot.Api.Responses.GetUserResponse;
import Baloot.Api.Responses.LoginUserResponse;
import Baloot.Utils.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CreditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = Session.getInstance().getLoggedInUsername();

        API api = APIFactory.getInstance();

        GetUserResponse getUserResponse = api.getUser(new GetUserRequest(username));

        request.setAttribute("credit", getUserResponse.credit);
        request.getRequestDispatcher("/credit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Float credit = Float.valueOf(request.getParameter("credit"));
        String username = Session.getInstance().getLoggedInUsername();

        API api = APIFactory.getInstance();

        AddCreditResponse addCreditResponse = api.addCredit(new AddCreditRequest(username, credit));
        if (addCreditResponse.success) {
            response.sendRedirect("/credit");
            return;
        }

        request.getSession().setAttribute("error", addCreditResponse.errorMessage);
        response.sendRedirect("/400.jsp");
    }
}