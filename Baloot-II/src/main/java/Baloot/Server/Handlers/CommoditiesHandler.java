package Baloot.Server.Handlers;

import Baloot.Api.API;
import Baloot.Api.Requests.GetCommoditiesListRequest;
import Baloot.Api.Responses.GetCommoditiesListResponse;
import Baloot.Utils.Consts;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CommoditiesHandler extends BaseHandler {
    public CommoditiesHandler(API api) {
        super(api);
    }

    public void handle(@NotNull Context context) throws Exception {
        GetCommoditiesListResponse response = api.getCommoditiesList(new GetCommoditiesListRequest());
        context.html(buildHtml(response.commoditiesList));
    }

    String buildHtml(List<GetCommoditiesListResponse.Commodity> commodities) throws IOException {
        File template = new File(Consts.TEMPLATE_DIR + "Commodities.html");
        Document doc = Jsoup.parse(template, "UTF-8", "");
        Element table = doc.select("body").select("table").first();
        for (GetCommoditiesListResponse.Commodity commodity: commodities) {
            table.append("<tr>\n" +
                    "        <td>" + commodity.id + "</td>\n" +
                    "        <td>" + commodity.name + "</td> \n" +
                    "        <td>" + commodity.providerId + "</td>\n" +
                    "        <td>" + commodity.price + "</td>\n" +
                    "        <td>" + commodity.categories + "</td>\n" +
                    "        <td>" + commodity.rating + "</td>\n" +
                    "        <td>" + commodity.inStock + "</td>\n" +
                    "        <td><a href=\"/commodities/" + commodity.id + "\">Link</a></td>\n" +
                    "     </tr>");
        }
        return doc.html();
    }
}
