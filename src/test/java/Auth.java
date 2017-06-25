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
public class Auth {
    private String login = "/api/login/v2";
    private String validEmail = "newshriek@gmail.com";
    private String validPassword = "#435HJ2d1!dgAA";

    @BeforeClass
    public void init(){
        RestAssured.baseURI = "https://iqoption.com";
    }

    @Test
    public void correctAuth(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", validEmail);
        params.put("password", validPassword);

        ArrayMessageResponse r = RestAssured.given().params(params).post(login).as(ArrayMessageResponse.class);
        assertThat(r.isSuccessful(), equalTo(true));
    }

    @Test
    public void incorrectCredentials(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", validEmail);
        params.put("password", "asd1123123");

        ArrayMessageResponse r = RestAssured.given().params(params).post(login).as(ArrayMessageResponse.class);
        assertThat(r.isSuccessful(), equalTo(false));
        assertThat(r.message(), equalTo("Invalid login or password"));
    }

    @Test
    public void incorrectEmailAuth(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "asd2sadas");
        params.put("password", validPassword);

        Response r = RestAssured.given().params(params).post(login).as(Response.class);
        assertThat(r.isSuccessful(), equalTo(false));
        assertThat(r.getMessage().email(), equalTo("E-mail \"" + params.get("email")+ "\" is not valid"));
    }

    @Test
    public void incorrectPasswordAuth(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", validEmail);
        params.put("password", "as");

        Response r = RestAssured.given().params(params).post(login).as(Response.class);
        assertThat(r.isSuccessful(), equalTo(false));
        assertThat(r.getMessage().password(), equalTo("Invalid password length"));
    }

}
