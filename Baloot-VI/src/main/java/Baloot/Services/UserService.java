package Baloot.Services;

import Baloot.Api.API;
import Baloot.Api.APIFactory;
import Baloot.Api.Requests.*;
import Baloot.Api.Responses.*;
import Baloot.Utils.Session;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserService {

    @CrossOrigin
    @RequestMapping(value = "/addCredit", method = RequestMethod.POST)
    public AddCreditResponse AddCredit(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "credit") Float credit
    ) {
        API api = APIFactory.getInstance();

        AddCreditResponse addCreditResponse = api.addCredit(
                new AddCreditRequest(
                        username, credit
                )
        );
        return addCreditResponse;
    }

    @CrossOrigin
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public AddUserResponse addUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "birthDate") String birthDate,
            @RequestParam(value = "credit") Float credit

    ) {

        API api = APIFactory.getInstance();

        return api.addUser(
                new AddUserRequest(
                        username,
                        email,
                        password,
                        address,
                        birthDate,
                        credit
                ));
    }


    @CrossOrigin
    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public GetUserResponse addUser(
            @PathVariable(value = "username") String username
    ) {

        API api = APIFactory.getInstance();

        return api.getUser(
                new GetUserRequest(
                        username
                )
        );
    }

    @CrossOrigin

    @RequestMapping(path = "/buylist", method = RequestMethod.DELETE)
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
    @RequestMapping(path = "/buylist", method = RequestMethod.POST)
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
    @RequestMapping(path = "/buylist", method = RequestMethod.GET)
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
