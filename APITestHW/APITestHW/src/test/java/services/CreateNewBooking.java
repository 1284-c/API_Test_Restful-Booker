package services;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;


public class CreateNewBooking  {


    @Test
    public String Post_Create_Booking(){


        RestAssured.baseURI ="https://restful-booker.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        String body_data =
                "{\n" +
                        "    \"firstname\" : \"cerenay\",\n" +
                        "    \"lastname\" : \"coskun\", \n" +
                        "    \"totalprice\" : 500,\n" +
                        "    \"depositpaid\" : true  ,\n" +
                        "    \"bookingdates\" :  { \n" +
                        "    \"checkin\" : \"2018-01-01\",\n" +
                        "    \"checkout\" : \"2019-01-01\"  } ,\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}";

        Response response = request.log().all().contentType(ContentType.JSON).header("Accept","application/json")
                .body(body_data  public String Post_Create_Booking(){


            RestAssured.baseURI ="https://restful-booker.herokuapp.com";
            RequestSpecification request = RestAssured.given();
            String body_data =
                    "{\n" +
                            "    \"firstname\" : \"cerenay\",\n" +
                            "    \"lastname\" : \"coskun\", \n" +
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
            response.then().statusCode(200)
)
                .when()
                .post("/booking");
                response.then().log().all();
        response.then().statusCode(200)
                .body("booking.firstname",equalTo("cerenay"))
                .body("booking.lastname",equalTo("coskun"))
                .body("booking.additionalneeds",startsWith("Bre"));


        String jsonString = response.getBody().asString();
        String Booking_id = JsonPath.from(jsonString).getString("bookingid");
        System.out.println("booking_id: " + Booking_id);

        return Booking_id;


    }
}
