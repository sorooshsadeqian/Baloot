package Baloot.Servlets;

import Baloot.Api.APIFactory;
import Baloot.Api.Requests.GetCommoditiesByCategoryRequest;
import Baloot.Api.Responses.GetCommoditiesByCategoryResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Baloot.Api.Requests.GetCommoditiesListRequest;
import Baloot.Api.Responses.GetCommoditiesListResponse;
import com.google.gson.Gson;

import java.io.IOException;

public class CommoditiesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GetCommoditiesListRequest getCommodityRequest = new GetCommoditiesListRequest("", "");
        GetCommoditiesListResponse CommodityList = APIFactory.getInstance().getCommoditiesList(getCommodityRequest);
        String commoditiesListJson = new Gson().toJson(CommodityList.commoditiesList);

        request.setAttribute("commoditiesList",commoditiesListJson);
        request.getRequestDispatcher("/commodities.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("search");
        String searchedWord = request.getParameter("sort");
        String search_button = request.getParameter("searchName");
        String category_button = request.getParameter("searchCat");
        String sort_button = request.getParameter("sortBy");
        if (search_button != null) {
            GetCommoditiesListRequest getCommoditiesListRequest = new GetCommoditiesListRequest(query, "");
            GetCommoditiesListResponse CommodityList = APIFactory.getInstance().getCommoditiesList(
                    getCommoditiesListRequest
            );

            String commoditiesListJson = new Gson().toJson(CommodityList.commoditiesList);
            request.setAttribute("commoditiesList",commoditiesListJson);
            request.getRequestDispatcher("/commodities.jsp").forward(request, response);

        } else if (category_button != null) {
            GetCommoditiesByCategoryRequest getCommoditiesByCategoryRequest = new GetCommoditiesByCategoryRequest(
                    query
            );
            GetCommoditiesByCategoryResponse CommodityList = APIFactory.getInstance().getCommoditiesByCategory(
                    getCommoditiesByCategoryRequest
            );
            String commoditiesListJson = new Gson().toJson(CommodityList.commoditiesListByCategory);
            request.setAttribute("commoditiesList",commoditiesListJson);
            request.getRequestDispatcher("/commodities.jsp").forward(request, response);

        } else if (sort_button != null) {
            GetCommoditiesListRequest getCommoditiesListRequest = new GetCommoditiesListRequest(
                    "", searchedWord
            );
            GetCommoditiesListResponse CommodityList = APIFactory.getInstance().getCommoditiesList(
                    getCommoditiesListRequest
            );

            String commoditiesListJson = new Gson().toJson(CommodityList.commoditiesList);
            request.setAttribute("commoditiesList",commoditiesListJson);
            request.getRequestDispatcher("/commodities.jsp").forward(request, response);

        }

    }

}
