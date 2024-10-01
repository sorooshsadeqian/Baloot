package Baloot.Services;

import Baloot.Api.API;
import Baloot.Api.APIFactory;
import Baloot.Api.Requests.*;
import Baloot.Api.Responses.*;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;



@RestController
public class CommodityService {

    public CommodityService() {
    }

    @CrossOrigin

    @RequestMapping(path = "/commodity/{commodityId}", method = RequestMethod.GET)
    public GetCommodityByIdResponse getCommodityById(@PathVariable(value = "commodityId") String commodityId) {

        API api = APIFactory.getInstance();

        return api.getCommodityById(
                new GetCommodityByIdRequest(
                        Integer.parseInt(commodityId)
                )
        );
    }

    @CrossOrigin
    @RequestMapping(path = "/commodities", method = RequestMethod.GET)
    public GetCommoditiesListResponse getCommodities(
            @RequestParam(value = "query", defaultValue = "") String query,
            @RequestParam(value = "searchField", defaultValue = "") String searchField,
            @RequestParam(value = "sortKey", defaultValue = "") String sortKey
    ) {
        API api = APIFactory.getInstance();

        return api.getCommoditiesList(
                new GetCommoditiesListRequest(
                        query, searchField, sortKey
                )
        );
    }
    @CrossOrigin
    @RequestMapping(path = "/rateCommodity/{commodityId}", method = RequestMethod.POST)
    public RateCommodityResponse rateCommodity(
            @PathVariable(value = "commodityId") String commodityId,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "score") Float score
    ) {
        API api = APIFactory.getInstance();
        return api.rateCommodity(
                new RateCommodityRequest(
                        username, Integer.parseInt(commodityId), score
                )
        );
    }

    @CrossOrigin
    @RequestMapping(value = "/voteComment", method = RequestMethod.POST)
    public VoteCommentResponse voteComment(
            @RequestParam(value = "like") Boolean like,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "commodityId") Integer commodityId,
            @RequestParam(value = "commentEmail") String commentEmail
    ) {

        API api = APIFactory.getInstance();

        return api.voteComment(
                new VoteCommentRequest(
                        commentEmail,
                        username,
                        commodityId,
                        like
                ));
    }
}
