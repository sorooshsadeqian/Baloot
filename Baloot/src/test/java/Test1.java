import Baloot.Api.API;
import Baloot.Api.APIImpl;
import Baloot.Api.Requests.*;
import Baloot.Api.Responses.*;
import Baloot.Database.Database;
import Baloot.Database.InMemoryDatabase;
import Baloot.Domain.Commodity;
import Baloot.Domain.Provider;
import Baloot.Domain.User;
import Baloot.Exceptions.NotFoundException;
import Baloot.Utils.Errors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Test1 {

    Database database;
    API api;

    @Before
    public void setUp() {
        database = new InMemoryDatabase();
        api = new APIImpl(database);
    }

    @After
    public void tearDown() {
        database.flush();
    }

    @Test
    public void testAddUserSuccessful() throws NotFoundException {
        AddUserRequest request = new AddUserRequest("user", "email",
                "password", "address", "2023", 1000);
        AddUserResponse response = api.addUser(request);
        Assert.assertTrue(response.success);

        User user = database.getUserByUsername("user");
        Assert.assertEquals("user", user.getUsername());
        Assert.assertEquals("email", user.getEmail());
        Assert.assertEquals("password", user.getPassword());
        Assert.assertEquals("address", user.getAddress());
        Assert.assertEquals("2023", user.getBirthDate());
        Assert.assertEquals(1000, user.getCredit(), 0.01);
    }

    @Test
    public void testAddUserInvalidUsername() {
        AddUserRequest request = new AddUserRequest("@user", "email",
                "password", "address", "2023", 1000);
        AddUserResponse response = api.addUser(request);
        Assert.assertFalse(response.success);
        Assert.assertEquals(Errors.INVALID_USERNAME, response.errorMessage);

        request = new AddUserRequest("u ser", "email",
                "password", "address", "2032", 1000);
        response = api.addUser(request);
        Assert.assertFalse(response.success);
        Assert.assertEquals(Errors.INVALID_USERNAME, response.errorMessage);

    }

    @Test
    public void testAddProviderSuccess() throws NotFoundException {
        AddProviderRequest request = new AddProviderRequest(1, "name", "2023");
        AddProviderResponse response = api.addProvider(request);

        Assert.assertTrue(response.success);

        Provider provider = database.getProviderById(1);
        Assert.assertEquals(1, provider.getId());
        Assert.assertEquals("name", provider.getName());
        Assert.assertEquals("2023", provider.getRegistryDate());
    }

    @Test
    public void testAddCommoditySuccess() throws NotFoundException {
        database.addProvider(new Provider(1, "name", "2023"));
        AddCommodityRequest request = new AddCommodityRequest(1, "name", 1, 1000,
                new ArrayList<>() { {add("cat");}}, 5, 10);

        AddCommodityResponse response = api.addCommodity(request);
        Assert.assertTrue(response.success);

        Commodity commodity = database.getCommodityById(request.id);
        Assert.assertEquals(1, commodity.getId());
        Assert.assertEquals("name", commodity.getName());
        Assert.assertEquals(1, commodity.getProviderId());
        Assert.assertEquals(1000, commodity.getPrice(), 0.01);
        Assert.assertEquals(new ArrayList<>() { {add("cat");}}, commodity.getCategories());
        Assert.assertEquals(5, commodity.getRating(), 0.01);
        Assert.assertEquals(10, commodity.getInStock());

    }

    @Test
    public void testAddCommodityProviderNotFound() {
        AddCommodityRequest request = new AddCommodityRequest(1, "name", 1, 1000,
                new ArrayList<>() { {add("cat");}}, 5, 10);

        AddCommodityResponse response = api.addCommodity(request);
        Assert.assertFalse(response.success);
        Assert.assertEquals(Errors.PROVIDER_NOT_FOUND, response.errorMessage);
    }

    @Test
    public void testAddRatingCommodityNotFound() {
       AddUserRequest addUserRequest = new AddUserRequest("user", "email",
                "password", "address", "2023", 1000);
       api.addUser(addUserRequest);
       RateCommodityRequest request = new RateCommodityRequest("user", 1,  2);
       RateCommodityResponse rateCommodityResponse = api.rateCommodity(request);
       System.out.println(rateCommodityResponse.errorMessage);
       Assert.assertFalse(rateCommodityResponse.success);
    }

    @Test
    public void testAddRatingSuccess() throws NotFoundException {
        database.addProvider(new Provider(1, "name", "2023"));
        database.addCommodity(new Commodity(1, "name", 1, 1000,new ArrayList<>() { {add("cat");}}, 5, 10));
        RateCommodityRequest rateCommodityRequest = new RateCommodityRequest("user", 1,  2);
        RateCommodityResponse rateCommodityResponse = api.rateCommodity(rateCommodityRequest);
        System.out.println(rateCommodityResponse.success);
    }

    @Test
    public void testGetCommoditiesByCategorySuccess() throws NotFoundException {
        database.addProvider(new Provider(1, "name", "2023"));
        database.addCommodity(new Commodity(1, "commodityOne", 1, 300,new ArrayList<>() { {add("cat");}}, 5, 10));
        database.addCommodity(new Commodity(2, "commodityTwo", 1, 1000,new ArrayList<>() { {add("cat");}}, 5, 10));

        GetCommoditiesByCategoryRequest request = new GetCommoditiesByCategoryRequest("cat");
        GetCommoditiesByCategoryResponse response = api.getCommoditiesByCategory(request);
        Assert.assertTrue(response.success);
    }

    @Test
    public void testGetCommoditiesByCategoryEmptyListSuccess() {
        GetCommoditiesByCategoryRequest request = new GetCommoditiesByCategoryRequest("cat");
        GetCommoditiesByCategoryResponse response = api.getCommoditiesByCategory(request);
        List<GetCommoditiesByCategoryResponse.Commodity> resCommodities = new ArrayList<>();
        Assert.assertTrue(response.success);
        Assert.assertEquals(resCommodities, response.commoditiesListByCategory);

    }

    @Test
    public void testGetCommodityByIdSuccess() throws NotFoundException {
        database.addProvider(new Provider(10, "name", "2023"));
        database.addCommodity(new Commodity(1, "commodityOne", 10, 300,new ArrayList<>() { {add("cat");}}, 5, 10));

        GetCommodityByIdRequest request = new GetCommodityByIdRequest(1);
        GetCommodityByIdResponse response = api.getCommodityById(request);
        Assert.assertTrue(response.success);
        Assert.assertEquals(response.name, "commodityOne");
    }

    @Test
    public void testGetCommodityByIdCommodityNotFound() throws NotFoundException {
        database.addProvider(new Provider(10, "name", "2023"));
        database.addCommodity(new Commodity(1, "commodityOne", 10, 300,new ArrayList<>() { {add("cat");}}, 5, 10));

        GetCommodityByIdRequest request = new GetCommodityByIdRequest(4);
        GetCommodityByIdResponse response = api.getCommodityById(request);
        Assert.assertFalse(response.success);
        Assert.assertEquals(response.errorMessage, "commodity not found");
    }

}
