package services;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdateCreatedBooking {
    @BeforeTest
    public String postCrateToken(){
        RestAssured.baseURI ="https://restful-booker.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        String postData= "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";


        Response response =request.header("content-type", "application/json")
                .body(postData)
                .post("/auth");
        response.then().log().all();

        String jsonString = response.getBody().asString();
        String result= JsonPath.from(jsonString).getString("token");
        System.out.println("token_response: "+result);

        return result;

    }
    public int Create_Booking(){

        RestAssured.baseURI ="https://restful-booker.herokuapp.com";
        RequestSpecification request = given();
        String body_data =
                "{\n" +
                        "    \"firstname\" : \"test\",\n" +
                        "    \"lastname\" : \"automation\", \n" +
                        "    \"totalprice\" : 500,\n" +
                        "    \"depositpaid\" : true  ,\n" +
                        "    \"bookingdates\" :  { \n" +
                        "    \"checkin\" : \"2018-01-01\",\n" +
                        "    \"checkout\" : \"2019-01-01\"  } ,\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}";

        Response response = request.log().all().contentType(ContentType.JSON).header("Accept","application/json")
                .body(body_data)
                .when()
                .post("/booking");
        response.then().log().all();


        String jsonString = response.getBody().asString();
        int Booking_id = JsonPath.from(jsonString).get("bookingid");
        System.out.println("booking_id: " + Booking_id);

        return Booking_id;



    }

    @DataProvider(name = "dataProvider")
    public Object[][] dataProvider ()
    {
        return new Object[][]{
                {postCrateToken(), Create_Booking()}

        };
    }

    @Test(dataProvider = "dataProvider")
    public void Put_Update_Created_Booking(String Token, int booking_id){

        RestAssured.baseURI ="https://restful-booker.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        String body_data =
                "{\n" +
                        "    \"firstname\" : \"Cerenay\",\n" +
                        "    \"lastname\" : \"Coşkun\", \n" +
                        "    \"totalprice\" : 550,\n" +
                        "    \"depositpaid\" : true  ,\n" +
                        "    \"bookingdates\" :  { \n" +
                        "    \"checkin\" : \"2018-01-01\",\n" +
                        "    \"checkout\" : \"2019-01-01\"  } ,\n" +
                        "    \"additionalneeds\" : \"Breakfasst\"\n" +
                        "}";
        Response response =request.header("Accept", "application/json")
                .header("Cookie","token=" + Token)
                .header("Authorisation","Basic")
                .contentType(ContentType.JSON)
                .body(body_data)
                .when()
                .put("/booking/{bookingID}\n",booking_id);
        response.then().log().all();
        response.then().statusCode(200)
                .body("firstname",startsWith("Cer"))
                .body("totalprice",equalTo(550));
        attachment(request, RestAssured.baseURI, response);

    }
    public String attachment(RequestSpecification httpRequest, String baseURI, Response response) {
        String html = "Url = " + baseURI + "\n \n" +
                "Request Headers = " + ((RequestSpecificationImpl) httpRequest).getHeaders() + "\n \n" +
                "Request Body = " + ((RequestSpecificationImpl) httpRequest).getBody() + "\n \n" +
                "Response Body = " + response.getBody().asString();
        Allure.addAttachment("Request Detail", html);
        return html;
    }
}
