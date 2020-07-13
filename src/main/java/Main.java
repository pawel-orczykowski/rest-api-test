import io.restassured.RestAssured;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File("src/test/java/somefile.json"));
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.basePath = "/auth";

        String resposne = RestAssured.given().
                contentType("application/json").
                body(fileInputStream).
                post().
                getBody().jsonPath().get("token").
                toString();

        System.out.println(resposne);
    }

}
