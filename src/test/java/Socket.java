import com.iqoptions.utils.WSClient;
import com.jayway.awaitility.Awaitility;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

//3 Сервис предоставляет websocket сессию
//3.1 Предлагается написать 2 простых теста на сервис wss://iqoption.com/echo/websocket:
//            - успешная авторизация в WS
//- не успешная авторизация в WS
//
//4 Кратко описать причину выбора фрэймворка и используемых в тестовом примере библиотек

    @BeforeClass
    public void init(){
        RestAssured.baseURI = "https://iqoption.com";
    }


    String a = "red_test_ab=%7B%22random_id%22%3A%22C2035915-E93B-30E6-5735-0EC2A5FC8119%22%2C%22group%22%3A1%7D; _ym_uid=1498313845905182435; _ym_isad=2; nonreg_popup_shown=1; not_eu_redirect=1; __ar_v4=DOHZDUV2RZAQDDD4CSJAXU%3A20170624%3A4%7CMZT3MWYWWZDR3CLURTSTBN%3A20170624%3A4%7C24XOIFROC5GDRFNQZU34WC%3A20170624%3A4; reg_popup_shown=1; eu_redirect=1; ssid=a889a61c3b6f29ae53c220bff253336b; login_before=1; features=901b317a53bee6be508178c0d25fd0b9c6039c30361328c25f6ec88c5e5f64928b2bd53bbb4cefd0f32ab591465630e1b2e56b2fd823368cf32ab591465630e11056c1ceab425c2fa346267b57b4f9016e16118fe82574959c4cf1cb32f9194c5f08a881b0924fb7469b05492a54da6c5aa6b2076783cfbae6be07e14bce9d111c5cddc10bdd47539505511e28d5d33bb69fc38db1ad24c8cfc816f53852819e5cca4c30e1d6fb63; chatButtons=true; grpexp=new-main-video; lang=en_US; landing=iqoption.com; _ga_cid=2093095738.1498313844; is_regulated=1; __ar_v4=DOHZDUV2RZAQDDD4CSJAXU%3A20170624%3A5%7CMZT3MWYWWZDR3CLURTSTBN%3A20170624%3A4%7C24XOIFROC5GDRFNQZU34WC%3A20170624%3A4; _ga=GA1.2.2093095738.1498313844; _gid=GA1.2.835226858.1498313844; _gat=1; _gat_tracker=1; _ym_visorc_22669009=b; lang=en_US; platform=9; platform_version=738.11.0921; __uat=c9c64e071d2853bc7906c017a231ad1cc46ab630";

    @Test
    public void correctAuth() throws URISyntaxException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, WebSocketException {
        String ssid = "b49eb46188ecd96494681f239cd52e35";
        Header header = new Header("Cookie", "ssid:" + ssid);
        Cookie cookie = new Cookie.Builder("ssid", ssid).build();
        RequestSpecification req = RestAssured.given().cookie(cookie).header(header);

        System.out.println(req.get(profile).asString());


        WSClient client = new WSClient(baseUri, cookie);
        client.socket().connect().sendPing();
        Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> (client.messages().size() > 10));
        System.out.println(client.messages().get(0));

//        Map<String, String> params = new HashMap<String, String>();
//        params.put("email", validEmail);
//        params.put("password", validPassword);
//
//        ArrayMessageResponse r = RestAssured.given().params(params).post(login).as(ArrayMessageResponse.class);
//        assertThat(r.isSuccessful(), equalTo(true));
    }




}
