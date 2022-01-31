package services;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UpdateCreatedBooking {

    @Test
    public void Put_Update_Created_Booking(String Token, String booking_id){

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





    }
}
