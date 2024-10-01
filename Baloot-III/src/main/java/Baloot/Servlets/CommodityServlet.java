package Baloot.Servlets;

import Baloot.Api.API;
import Baloot.Api.APIFactory;
import Baloot.Api.Requests.*;
import Baloot.Api.Responses.*;
import Baloot.Utils.Errors;
import Baloot.Utils.Session;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class CommodityServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String commodityId = pathParts[1];

        API api = APIFactory.getInstance();

        GetCommodityByIdResponse getCommodityResponse = api.getCommodityById(
                new GetCommodityByIdRequest(Integer.parseInt(commodityId))
        );

        String commodityJson = new Gson().toJson(getCommodityResponse);

        request.setAttribute("commodity", commodityJson);
        request.getRequestDispatcher("/commodity.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String commodityId = pathParts[1];

        API api = APIFactory.getInstance();

        String voteAction = request.getParameter("voteButton");
        String commentAction = request.getParameter("commentButton");
        String rateAction = request.getParameter("rateButton");
        String buyListAction = request.getParameter("buyListButton");
        String error = "";

        if (voteAction != null) {
            String commentEmail = request.getParameter("comment_email");
            Boolean like = voteAction.equals("like");
            VoteCommentResponse voteCommentResponse = api.voteComment(new VoteCommentRequest(
                    commentEmail,
                    Session.getInstance().getLoggedInUsername(),
                    Integer.parseInt(commodityId),
                    like
            ));
            if (!voteCommentResponse.success) {
                error = voteCommentResponse.errorMessage;
            }
        }
        else {
            GetUserResponse user = api.getUser(new GetUserRequest(Session.getInstance().getLoggedInUsername()));
            if (Objects.equals(commentAction, "comment")) {
                String comment = request.getParameter("comment");
                AddCommentResponse addCommentResponse = api.addComment(new AddCommentRequest(
                        user.email,
                        Integer.parseInt(commodityId),
                        comment,
                        DateTimeFormatter.ISO_DATE.format(LocalDate.now())
                ));
                if (!addCommentResponse.success) {
                    error = addCommentResponse.errorMessage;
                }
            }
            else if (Objects.equals(rateAction, "rate")) {
                int rate = Integer.parseInt(request.getParameter("rate"));
                RateCommodityResponse rateCommodityResponse = api.rateCommodity(new RateCommodityRequest(
                        Session.getInstance().getLoggedInUsername(),
                        Integer.parseInt(commodityId),
                        rate
                ));
                if (!rateCommodityResponse.success) {
                    error = rateCommodityResponse.errorMessage;
                }
            }
            else if (Objects.equals(buyListAction, "buyList")) {
                AddToBuyListResponse addToBuyListResponse = api.addToBuyList(new AddToBuyListRequest(
                        Session.getInstance().getLoggedInUsername(),
                        Integer.parseInt(commodityId)
                ));
                if (!addToBuyListResponse.success) {
                    error = addToBuyListResponse.errorMessage;
                }
            }
        }

        if (!error.isEmpty()) {
            request.getSession().setAttribute("error", error);
            response.sendRedirect("/400.jsp");
            return;
        }


        GetCommodityByIdResponse getCommodityResponse = api.getCommodityById(
                new GetCommodityByIdRequest(Integer.parseInt(commodityId))
        );

        String commodityJson = new Gson().toJson(getCommodityResponse);

        request.setAttribute("commodity", commodityJson);
        request.getRequestDispatcher("/commodity.jsp").forward(request, response);
    }

}