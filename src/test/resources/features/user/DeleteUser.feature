Feature: Get a user

  Scenario: Delete a valid user
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": true
    }
    """
    And the last created user is deleted by id
    Then last response should be returned with code 200
    And last response body should be empty


  Scenario: Delete a nonexistent user
    And a nonexistent user is deleted by id
    Then last response should be returned with code 400
    And last response should contain "demo_app.domain.ErrorDetails" entity with following data
    """
    {
    "message": "No class demo_app.domain.User entity with id 999999 exists!",
    "details": "uri=/api/v1/users/999999"
    }
    """

