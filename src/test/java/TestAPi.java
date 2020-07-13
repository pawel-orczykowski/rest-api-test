import io.restassured.RestAssured;
import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestAPi {

    FileInputStream fileInputStream = new FileInputStream(new File("src/test/java/somefile.json"));

    public TestAPi() throws FileNotFoundException {
    }


    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.basePath = "/auth";
    }

    @Test
    public void getToken() {
        String resposne = RestAssured.given().
                contentType("application/json").
                body(fileInputStream).
                post().
                getBody().jsonPath().get("token").
                toString();

        System.out.println(resposne);

    }

}
