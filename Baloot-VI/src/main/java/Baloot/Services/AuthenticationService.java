package Baloot.Services;

import Baloot.Api.API;
import Baloot.Api.APIFactory;
import Baloot.Api.Requests.LoginUserRequest;
import Baloot.Api.Responses.LoginUserResponse;
import Baloot.Utils.Session;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationService {
    @CrossOrigin
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public LoginUserResponse logIn(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {

        API api = APIFactory.getInstance();

        LoginUserResponse loginUserResponse = api.loginUser(new LoginUserRequest(username, password));
        if (loginUserResponse.success) {
            Session.getInstance().loginUser(username);
        }

        return loginUserResponse;

    }

    @CrossOrigin
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logOut() {
        Session.getInstance().logoutUser();
    }
}
