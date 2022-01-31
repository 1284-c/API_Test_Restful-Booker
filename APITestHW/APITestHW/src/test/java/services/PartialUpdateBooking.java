package services;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PartialUpdateBooking {
    String Booking_id;

    public PartialUpdateBooking(String booking_id) {
         this.Booking_id= "Bookingid";
    }

    @BeforeClass
    public void Create_Booking(){

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
        Booking_id = JsonPath.from(jsonString).getString("bookingid");
        System.out.println("booking_id: " + Booking_id);

        Booking_id= this.Booking_id;


    }
    @Test
    public void Show_Partial_Updated_Booking(){


        given().log().all().header("Accept","application/json")
                .get("https://restful-booker.herokuapp.com/booking/{bookingID}", Booking_id)
                .then().log().all();


    }

    @AfterClass
    public void Partial_Update_Booking(){
        String Token= "998c15d9577831f";
        String data ="{\n" +
                "    \"firstname\": \"update\",\n" +
                "    \"lastname\" : \"booking\" \n" +
                "}";
        given().log().all()
                .header("Accept", "application/json")
                .header("Cookie","token=" + Token)
                .header("Authorisation","Basic")
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .patch("https://restful-booker.herokuapp.com/booking/{bookingID}",Booking_id)
                .then().log().all();





    }
}
