package services;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Delete_Created_Booking {
    public void deleteBooking( String Token, String booking){
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Cookie","token=" + Token)
                .header("Authorisationoptional","Basic")
                .when()
                .delete("https://restful-booker.herokuapp.com/booking/{bookingID}\n",booking)
                .then()
                .log().all()
                .statusCode(201);




    }
}
