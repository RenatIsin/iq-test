import com.iqoptions.dto.ArrayMessageResponse;
import com.iqoptions.dto.Response;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by MiF on 24.06.2017.
 */
public class Registration {
    private String register = "/api/register";
    private String validEmail = "test@testemail.com";

    @BeforeClass
    public void init(){
        RestAssured.baseURI = "https://iqoption.com";
    }

    @Test
    public void registeredEmail(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("first_name", "ewrwe");
        params.put("last_name", "wqeqwe");
        params.put("email", "newshriek@gmail.com");
        params.put("password", "123sadQ");

        ArrayMessageResponse r = RestAssured.given().params(params).post(register).as(ArrayMessageResponse.class);
        assertThat(r.isSuccessful(), equalTo(false));
        assertThat(r.message(), equalTo("You have already registered"));
    }

    @Test
    public void shortPassword(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("first_name", "ewrwe");
        params.put("last_name", "wqeqwe");
        params.put("email", validEmail);
        params.put("password", "11");

        System.out.println(RestAssured.given().params(params).post(register).asString());

        Response r = RestAssured.given().params(params).post(register).as(Response.class);
        assertThat(r.isSuccessful(), equalTo(false));
        assertThat(r.getMessage().password(), equalTo("Invalid password length"));
    }

    @Test
    public void incorrectEmail(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("first_name", "ewrwe");
        params.put("last_name", "wqeqwe");
        params.put("email", "qwewqe@weqwe.");
        params.put("password", "123sadQ");

        Response r = RestAssured.given().params(params).post(register).as(Response.class);
        assertThat(r.isSuccessful(), equalTo(false));
        assertThat(r.getMessage().email(), equalTo("E-mail is not valid"));
    }

    @Test
    public void firstNameEmpty(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", validEmail);
        params.put("password", "123sadQasd");

        Response r = RestAssured.given().params(params).post(register).as(Response.class);
        assertThat(r.isSuccessful(), equalTo(false));
        assertThat(r.getMessage().firstName(), equalTo("First name is required"));
    }

    @Test
    public void lastNameEmpty(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", validEmail);
        params.put("first_name", "ewrwe");
        params.put("password", "123sadQasd");

        Response r = RestAssured.given().params(params).post(register).as(Response.class);
        assertThat(r.isSuccessful(), equalTo(false));
        assertThat(r.getMessage().lastName(), equalTo("Last name is required"));
    }


}
