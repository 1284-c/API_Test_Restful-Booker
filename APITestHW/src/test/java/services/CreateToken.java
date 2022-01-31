package services;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

public class CreateToken {
    @Test
    public void postCrateToken(){
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



    }
}