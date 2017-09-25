Feature: the list of movie can be retrieved
  Scenario: client makes call to GET /movies
    When the client calls /movies
    Then the client receives status code of 200