package services;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetBookingAll_Ids {

    @Test
    public void Get_All_Bookings(){

       given().log().all().
               when()
               .get("https://restful-booker.herokuapp.com/booking\n")
               .then().log().all().statusCode(200);
    }
}
