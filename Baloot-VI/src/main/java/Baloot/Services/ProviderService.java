package Baloot.Services;

import Baloot.Api.API;
import Baloot.Api.APIFactory;
import Baloot.Api.Requests.GetProviderRequest;
import Baloot.Api.Responses.GetProviderResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProviderService {

    @CrossOrigin
    @RequestMapping(value = "provider/{providerId}", method = RequestMethod.GET)
    public GetProviderResponse getProvider(
            @PathVariable(value = "providerId") Integer providerId
    ) {
        API api = APIFactory.getInstance();
        return api.getProvider(new GetProviderRequest(
                        providerId
                )
        );
    }
}
