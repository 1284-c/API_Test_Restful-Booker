package services;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetBookingAll_Ids {

    @Test
    public void Get_All_Bookings(){
        RestAssured.baseURI ="https://restful-booker.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        Response response= request.log().all().
               when()
               .get("https://restful-booker.herokuapp.com/booking\n");
              response.then().log().all().statusCode(200);
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
