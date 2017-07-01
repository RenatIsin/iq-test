import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqoptions.dto.*;
import com.iqoptions.utils.WSClient;
import com.jayway.awaitility.Awaitility;
import com.neovisionaries.ws.client.WebSocketException;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by MiF on 24.06.2017.
 */
public class Socket {
    private String profile = "/api/getprofile";
    private String validEmail = "newshriek@gmail.com";
    private String validPassword = "#435HJ2d1!dgAA";
    private String baseUri = "wss://iqoption.com/echo/websocket";
    private String ssid = "b49eb46188ecd96494681f239cd52e35";

    @BeforeClass
    public void init(){
        RestAssured.baseURI = "https://iqoption.com";
    }

    @Test
    public void correctAuth() throws URISyntaxException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, WebSocketException, IOException {
        RequestSpecification req = RestAssured.given().cookie(new Cookie.Builder("ssid", ssid).build());
        String fromRest = req.get(profile).asString();
        Response m = new ObjectMapper().readValue(fromRest, StringMessageResponse.class);

        WSClient client = new WSClient(baseUri).send(SocketData.ssid(ssid));
        Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> (client.messages().stream().filter(item -> (item.startsWith("{\"name\":\"profile\""))).count() > 0));
        String fromSocket = client.messages().stream().filter(item -> (item.startsWith("{\"name\":\"profile\""))).findFirst().orElseThrow(AssertionError::new);
        String actual = new ObjectMapper().readValue(fromSocket, SocketData.class).getMsg().toString();
        String expected = m.getResult().toString();
        expected = expected.substring(0, expected.length()-2);
        assertThat(actual, startsWith(expected));
    }

    @Test
    public void incorrectAuth() throws URISyntaxException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, WebSocketException, IOException {
        String incorrectSsid = "b49eb46188ecd96494681f239cd52e1111";
        RequestSpecification req = RestAssured.given().cookie(new Cookie.Builder("ssid", ssid).build());
        String fromRest = req.get(profile).asString();
        Response m = new ObjectMapper().readValue(fromRest, StringMessageResponse.class);

        WSClient client = new WSClient(baseUri).send(SocketData.ssid(incorrectSsid));
        Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> (client.messages().stream().filter(item -> (item.startsWith("{\"name\":\"profile\""))).count() > 0));
        String fromSocket = client.messages().stream().filter(item -> (item.startsWith("{\"name\":\"profile\""))).findFirst().orElseThrow(AssertionError::new);
        String actual = new ObjectMapper().readValue(fromSocket, SocketData.class).getMsg().toString();
        String expected = m.getResult().toString();
        expected = expected.substring(0, expected.length()-2);
        assertThat(actual, not(startsWith(expected)));
    }




}
