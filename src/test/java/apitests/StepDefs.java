package apitests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;

public class StepDefs {
    private TrelloBoardApiTests trelloBoardApiTests = new TrelloBoardApiTests();
    private String boardId;
    private String listId;
    private String labelId;
    private Response labelCreateResponse;

    @Given("Request to API to creates {string} Board {string}")
    public void createBoard(String color, String boardName)throws JsonPathException{
        boardId = trelloBoardApiTests.createTableApiTest(color, boardName).jsonPath().get("id").toString();
    }

    @When("Request to API to add to the Board new list named {string}")
    public void addListToBoard(String listName)throws JsonPathException{
        listId = trelloBoardApiTests.addListToTableApiTest(boardId, listName).jsonPath().get("id").toString();
    }

    @When("Request to API to add to the Board label {string} which color is {string}")
    public void addLabelToBoard(String labelName, String color)throws JsonPathException{
        labelId = trelloBoardApiTests.addLabelsToListApiTest(boardId, labelName, color).jsonPath().get("id").toString();
    }

    @When("Request to API to add to the list Card with name {string} with message {string} and created label")
    public void addCardToList(String cardName, String message)throws JsonPathException{
        labelCreateResponse = trelloBoardApiTests.addCardToListApiTest(labelId, listId, cardName, message);
    }

    @Then("Add Card to the list response is {string}")
    public void assertAddCardRespons(String responseCode){
        trelloBoardApiTests.assertResponseCode(labelCreateResponse, 200);
    }

    @Then("Add Card to list response body is correct")
    public void addCardToListResponseIsCorrect(){
        List<String> labelIdList = Arrays.asList(labelId);
        trelloBoardApiTests.assertDataInResponseBody(labelCreateResponse, "To jest test", boardId, listId, labelIdList, "Zadania na dzisiaj");
    }

    @Then("Remove created board")
    public void removeBoard() {
        trelloBoardApiTests.deleteBoard(boardId).then().statusCode(200);
    }
}
