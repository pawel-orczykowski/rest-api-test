package apitests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class TrelloBoardApiTests {


    String getCredentials (String propName) {
        Properties loadPropFile = new Properties();
        Map<String,String> map = new HashMap<>();
        try {
            loadPropFile.load(new FileInputStream("src/test/resources/config.properties"));
            map.put("key", loadPropFile.getProperty("key"));
            map.put("token", loadPropFile.getProperty("token"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map.get(propName);
    }

    public TrelloBoardApiTests() {
        setUp();
    }

    public RequestSpecification getCommonSpec() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addQueryParam("key", getCredentials("key"));
        builder.addQueryParam("token", getCredentials("token"));
        builder.setContentType(ContentType.JSON);
        return builder.build();
    }

    public void setUp() {
        RestAssured.baseURI = "https://api.trello.com";
        RestAssured.basePath = "/1";
    }

    public Response createTableApiTest(String color, String name) {
        return
                given().
                        spec(getCommonSpec()).
                        queryParam("name", name).
                        queryParam("refs_background", color).
                        when().
                        post("/boards").
                        then().statusCode(200).
                        extract().response();
    }

    public Response addListToTableApiTest(String boardId, String name) {
        return
                given().
                        spec(getCommonSpec()).
                        pathParam("id", boardId).
                        queryParam("name", name).
                        when().
                        post("/boards/{id}/lists").
                        then().
                        extract().response();
    }

    public Response addLabelsToListApiTest(String boardId, String name, String color) {
        return
                given().
                        spec(getCommonSpec()).
                        pathParam("id", boardId).
                        queryParam("name", name).
                        queryParam("color", color).
                        when().
                        post("/boards/{id}/labels").
                        then().
                        extract().response();
    }

    public Response addCardToListApiTest(String labelId, String listId, String name, String description) {
        return
                given().
                        spec(getCommonSpec()).
                        queryParam("name", name).
                        queryParam("desc", description).
                        queryParam("idLabels", labelId).
                        queryParam("idList", listId).
                        when().
                        post("/cards").
                        then().
                        extract().response();
    }

    public Response deleteBoard(String boardId) {
        return
                given().
                        spec(getCommonSpec()).
                        pathParam("id", boardId).
                        when().
                        delete("/boards/{id}").
                        then().
                        extract().response();
    }

    public ValidatableResponse assertResponseCode(Response response, int responseCode) {
        return response.then().statusCode(responseCode);
    }

    public void assertBody(Response response, String actual, String expected) {
        assertThat(response.getBody().jsonPath().get(actual), equalTo(expected));
    }

    public void assertBody(Response response, String actual, List<String> expected) {
        assertThat(response.getBody().jsonPath().get(actual), equalTo(expected));
    }

    public void assertDataInResponseBody(Response response, String description, String boardId, String listId, List<String> labelIdList, String name) {
        assertBody(response, "desc", description);
        assertBody(response, "idBoard", boardId);
        assertBody(response, "idList", listId);
        assertBody(response, "idLabels", labelIdList);
        assertBody(response, "name", name);
    }
}