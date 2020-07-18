Feature: Board Create via API

  Scenario: Board can be created using multiple API calls
    Given Request to API to creates "green" Board "Moje zadania"
    When Request to API to add to the Board new list named "Ważne"
    And Request to API to add to the Board label "Do zrobienia" which color is "red"
    And Request to API to add to the list Card with name "Zadania na dzisiaj" with message "To jest test" and created label
    Then Add Card to the list response is 200
    And Add Card to list response body is correct
    And Remove created board

