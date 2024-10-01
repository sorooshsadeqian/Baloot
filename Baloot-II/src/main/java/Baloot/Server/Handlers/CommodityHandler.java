package Baloot.Server.Handlers;

import Baloot.Api.API;
import Baloot.Api.Requests.GetCommoditiesListRequest;
import Baloot.Api.Requests.GetCommodityByIdRequest;
import Baloot.Api.Responses.GetCommoditiesListResponse;
import Baloot.Api.Responses.GetCommodityByIdResponse;
import Baloot.Domain.Comment;
import Baloot.Utils.Consts;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CommodityHandler extends BaseHandler {
    public CommodityHandler(API api) {
        super(api);
    }

    public void handle(@NotNull Context context) throws Exception {
        GetCommodityByIdRequest request = new GetCommodityByIdRequest(Integer.parseInt(context.pathParam("commodity_id")));
        GetCommodityByIdResponse response = api.getCommodityById(request);
        context.html(buildHtml(response));
    }

    String buildHtml(GetCommodityByIdResponse commodity) throws IOException {
        File template = new File(Consts.TEMPLATE_DIR + "Commodity.html");
        Document doc = Jsoup.parse(template, "UTF-8", "");
        Element body = doc.select("body").first();
        Element ul = body.selectFirst("ul");
        ul.append(
                "        <li id=\"id\"> Id: " + commodity.id + "</li>\n" +
                "        <li id=\"name\"> Name: " + commodity.name + "</li> \n" +
                "        <li id=\"providerId\"> Provider Id: " + commodity.providerId + "</li>\n" +
                "        <li id=\"price\"> Price: " + commodity.price + "</li>\n" +
                "        <li id=\"categories\"> Categories: " + commodity.categories + "</li>\n" +
                "        <li id=\"rating\"> Rating: " + commodity.rating + "</li>\n" +
                "        <li id=\"inStock\"> In Stock: " + commodity.inStock + "</li>\n");

        Element table = body.selectFirst("table");
        for (Comment comment: commodity.comments) {
            table.append("<tr>\n" +
                    "        <td>" + comment.getUserEmail() + "</td>\n" +
                    "        <td>" + comment.getText() + "</td>\n" +
                    "        <td>" + comment.getDate() + "</td>\n" +
                    "        <td>\n" +
                    "          <form action=\"\" method=\"POST\">\n" +
                    "            <label for=\"\">2</label>\n" +
                    "            <input\n" +
                    "              id=\"form_comment_id\"\n" +
                    "              type=\"hidden\"\n" +
                    "              name=\"comment_id\"\n" +
                    "              value=\"01\"\n" +
                    "            />\n" +
                    "            <button type=\"submit\">like</button>\n" +
                    "          </form>\n" +
                    "        </td>\n" +
                    "        <td>\n" +
                    "          <form action=\"\" method=\"POST\">\n" +
                    "            <label for=\"\">1</label>\n" +
                    "            <input\n" +
                    "              id=\"form_comment_id\"\n" +
                    "              type=\"hidden\"\n" +
                    "              name=\"comment_id\"\n" +
                    "              value=\"01\"\n" +
                    "            />\n" +
                    "            <button type=\"submit\">dislike</button>\n" +
                    "          </form>\n" +
                    "        </td>\n" +
                    "      </tr>");
        }

        return doc.html();
    }
}
