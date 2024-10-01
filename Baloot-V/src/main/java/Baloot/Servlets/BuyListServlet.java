package Baloot.Servlets;

import Baloot.Api.API;
import Baloot.Api.APIFactory;
import Baloot.Api.Requests.*;
import Baloot.Api.Responses.*;
import Baloot.Utils.Session;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class BuyListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = Session.getInstance().getLoggedInUsername();

        API api = APIFactory.getInstance();

        GetUserResponse getUserResponse = api.getUser(new GetUserRequest(username));
        GetBuyListResponse getBuyListResponse = api.getBuyList(new GetBuyListRequest(username));

        String getUserResponseJson = new Gson().toJson(getUserResponse);
        String getBuyListResponseJson = new Gson().toJson(getBuyListResponse);

        request.setAttribute("user", getUserResponseJson);
        request.setAttribute("buylist", getBuyListResponseJson);

        request.getRequestDispatcher("/buyList.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = Session.getInstance().getLoggedInUsername();

        API api = APIFactory.getInstance();

        String payAction = request.getParameter("payButton");
        String removeAction = request.getParameter("removeButton");
        String discountAction = request.getParameter("discountButton");
        String error = "";

        if (Objects.equals(payAction, "pay")) {
            PayBuyListResponse payBuyListResponse = api.payBuyList(new PayBuyListRequest(
                    username
            ));
            if (!payBuyListResponse.success) {
                error = payBuyListResponse.errorMessage;
            }
        }
        else if (Objects.equals(removeAction, "remove")){
            Integer commodityId = Integer.parseInt(request.getParameter("commodityId"));
            RemoveFromBuyListResponse removeFromBuyListResponse = api.removeFromBuyList(new RemoveFromBuyListRequest(
                    username,
                    commodityId
                    ));
            if (!removeFromBuyListResponse.success) {
                error = removeFromBuyListResponse.errorMessage;
            }
        }
        else if (Objects.equals(discountAction, "discount")) {
            String discountCode = request.getParameter("code");
            ApplyDiscountCodeResponse applyDiscountCodeResponse = api.applyDiscountCode(new ApplyDiscountCodeRequest(
                    username,
                    discountCode
            ));
            if (!applyDiscountCodeResponse.success) {
                error = applyDiscountCodeResponse.errorMessage;
            }
        }

        if (!error.isEmpty()) {
            request.getSession().setAttribute("error", error);
            response.sendRedirect("/400.jsp");
            return;
        }

        response.sendRedirect("/buyList");
    }

}