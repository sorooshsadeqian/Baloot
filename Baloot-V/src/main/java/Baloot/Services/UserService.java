package Baloot.Services;

import Baloot.Api.API;
import Baloot.Api.APIFactory;
import Baloot.Api.Requests.AddCreditRequest;
import Baloot.Api.Requests.AddUserRequest;
import Baloot.Api.Requests.GetUserRequest;
import Baloot.Api.Requests.VoteCommentRequest;
import Baloot.Api.Responses.AddCreditResponse;
import Baloot.Api.Responses.AddUserResponse;
import Baloot.Api.Responses.GetUserResponse;
import Baloot.Api.Responses.VoteCommentResponse;
import Baloot.Utils.Session;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserService {

    @CrossOrigin
    @RequestMapping(value = "addCredit/", method = RequestMethod.POST)
    public void AddCredit(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "credit") Float credit
    ) {
        API api = APIFactory.getInstance();

        AddCreditResponse addCreditResponse = api.addCredit(
                new AddCreditRequest(
                        username, credit
                )
        );
        if (addCreditResponse.success) {

        } else {

        }
    }

    @CrossOrigin
    @RequestMapping(value = "voteComment/", method = RequestMethod.POST)
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

    @CrossOrigin
    @RequestMapping(value = "user/", method = RequestMethod.POST)
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
    @RequestMapping(value = "user/{username}", method = RequestMethod.GET)
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
}
