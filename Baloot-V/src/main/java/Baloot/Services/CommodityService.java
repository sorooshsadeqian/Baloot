package Baloot.Services;

import Baloot.Api.API;
import Baloot.Api.APIFactory;
import Baloot.Api.Requests.*;
import Baloot.Api.Responses.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


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
    @RequestMapping(path = "/BuyList", method = RequestMethod.DELETE)
    public RemoveFromBuyListResponse deleteFromBuylist(
            @RequestParam(value = "commodityId") String commodityId,
            @RequestParam(value = "username") String username
    ) {
        API api = APIFactory.getInstance();

        return api.removeFromBuyList(new RemoveFromBuyListRequest(
                username,
                Integer.parseInt(commodityId)
        ));
    }

    @CrossOrigin
    @RequestMapping(path = "/BuyList", method = RequestMethod.POST)
    public AddToBuyListResponse addToBuyList(
            @RequestParam(value = "commodityId") String commodityId,
            @RequestParam(value = "username") String username
    ) {
        API api = APIFactory.getInstance();

        return api.addToBuyList(
                new AddToBuyListRequest(
                        username,
                        Integer.parseInt(commodityId)
                )
        );
    }

    @CrossOrigin
    @RequestMapping(path = "/BuyList", method = RequestMethod.GET)
    public GetBuyListResponse getBuyList(
            @RequestParam(value = "username") String username
    ) {
        API api = APIFactory.getInstance();

        return api.getBuyList(
                new GetBuyListRequest(
                        username
                )
        );
    }
    @CrossOrigin
    @RequestMapping(path = "/commodities", method = RequestMethod.GET)
    public GetCommoditiesListResponse getCommodities(
            @RequestParam(value = "commodityName", defaultValue = "") String commodityName,
            @RequestParam(value = "sortKey", defaultValue = "") String sortKey
    ) {
        API api = APIFactory.getInstance();

        if (!Objects.equals(commodityName, "")) {
            return api.getCommoditiesList(
                    new GetCommoditiesListRequest(
                            commodityName, ""
                    )
            );
        } else if (!Objects.equals(sortKey, "")) {
            return api.getCommoditiesList(
                    new GetCommoditiesListRequest(
                            "", sortKey
                    )
            );
        } else {
            return api.getCommoditiesList(
                    new GetCommoditiesListRequest(
                            "", ""
                    )
            );
        }
    }
    @CrossOrigin
    @RequestMapping(path = "/rateCommodity/{commodityId}", method = RequestMethod.POST)
    public void rateCommodity(
            @PathVariable(value = "commodityId") String commodityId,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "score") Float score
    ) {
        API api = APIFactory.getInstance();
        api.rateCommodity(
                new RateCommodityRequest(
                        username, Integer.parseInt(commodityId), score
                )
        );
    }
    @CrossOrigin
    @RequestMapping(path = "/payBuyList", method = RequestMethod.POST)
    public PayBuyListResponse rateCommodity(
            @RequestParam(value = "username") String username
    ) {
        API api = APIFactory.getInstance();
        return api.payBuyList(
                new PayBuyListRequest(
                        username
                )
        );
    }
    @CrossOrigin
    @RequestMapping(path = "/applyDiscount", method = RequestMethod.POST)
    public ApplyDiscountCodeResponse applyDiscountCode(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "discountCode") String discountCode

    ) {
        API api = APIFactory.getInstance();
        return api.applyDiscountCode(
                new ApplyDiscountCodeRequest(
                        username,
                        discountCode
                )
        );
    }
}
