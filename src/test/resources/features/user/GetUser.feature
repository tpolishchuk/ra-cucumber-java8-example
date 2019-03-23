Feature: Get a user

  Scenario: Get a valid user
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": true
    }
    """
    And the last created user is got by id
    Then last response should be returned with code 200
    And last response should contain "demo_app.domain.User" entity similar to requested for creation


  Scenario: Get a nonexistent user
    When a nonexistent user is got by id
    Then last response should be returned with code 404
    And last response should contain "demo_app.domain.ErrorDetails" entity with following data
    """
    {
    "message": "No value present",
    "details": "uri=/api/v1/users/999999"
    }
    """
