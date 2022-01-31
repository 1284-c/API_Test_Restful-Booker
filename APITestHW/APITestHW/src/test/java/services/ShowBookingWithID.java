package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ShowBookingWithID  {

    @Test
    public void ShowBooking(String bookingID){
        RestAssured.baseURI ="https://restful-booker.herokuapp.com";
        RequestSpecification request = RestAssured.given();

        Response response = request.log().all().header("Accept","application/json")
                .get("https://restful-booker.herokuapp.com/booking/{bookingID}", bookingID);

        int statusCode = response.getStatusCode();
        if(statusCode==200){
            response.then().log().all();
        }
        else {
            System.out.println("This booking cannot found");
        }

    }
}
