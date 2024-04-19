import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

public class DemoBlazeTests {

    private static final String LOGIN_ENDPOINT = "https://api.demoblaze.com/login";
    private static final String SIGNUP_ENDPOINT = "https://api.demoblaze.com/signup";
    //The first test case checks that a user can successfully log in with valid credentials
    @Test(description = "checks that a user can successfully log in with valid credentials")
    public void testSuccessfulLogin() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "abdotester");
        requestBody.put("password", "testpassword");

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(LOGIN_ENDPOINT);

        Assert.assertEquals(200, response.getStatusCode());

    }

    @Test(description = "checks that the login fails with invalid credentials")
    public void testFailedLogin() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "invaliduser");
        requestBody.put("password", "invalidpassword");

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(LOGIN_ENDPOINT);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("message"), null);
    }

    @Test(description = "checks that a user can successfully sign up with valid data")
    public void testSuccessfulSignup() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "newuser");
        requestBody.put("password", "newpassword");

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(SIGNUP_ENDPOINT);

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(response.jsonPath().getString("message"), null);
    }

    @Test(description = "checks that the signup fails if the username already exists")
    public void testFailedSignup() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testuser");
        requestBody.put("password", "newpassword");

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(SIGNUP_ENDPOINT);

        Assert.assertEquals(400, response.getStatusCode());
        Assert.assertEquals("Username already exists", response.jsonPath().getString("message"));
    }
}