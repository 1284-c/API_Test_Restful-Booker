package services;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ShowBookingWithID  {


    @BeforeClass
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
                {Create_Booking(),200}

        };
    }

    @Test(dataProvider = "dataProvider")
    public void ShowBooking(int bookingID, int StatusCode){
        RestAssured.baseURI ="https://restful-booker.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        System.out.println("Get Booking for booking id: " +bookingID);

        Response response = request.log().all().header("Accept","application/json")
                .get("https://restful-booker.herokuapp.com/booking/{bookingID}", bookingID);
        response.then().log().all();
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
