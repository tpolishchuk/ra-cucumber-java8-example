Feature: Update a user

  Scenario: Update a user with valid data
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": true
    }
    """
    And the last created user is updated with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>-UPDATED",
      "active": false
    }
    """
    Then last response should be returned with code 200
    And last response should contain "demo_app.domain.User" entity similar to requested for update


  Scenario: Update a user with valid nickname only
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": true
    }
    """
    And the last created user is updated with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>-UPDATED"
    }
    """
    Then last response should be returned with code 200
    And last response should contain a user with amended nickname as requested
    And last response should contain a user with active flag equal to true
    And last response should contain a user with not changed id


  Scenario: Update a user with valid active flag only
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": true
    }
    """
    And the last created user is updated with following request body
    """
    {
      "active": false
    }
    """
    Then last response should be returned with code 200
    And last response should contain a user with active flag equal to false
    And last response should contain a user with not changed id
    And last response should contain a user with not changed nickname


  Scenario: Update a user with empty request body
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": true
    }
    """
    And the last created user is updated with following request body
    """
    {
    }
    """
    Then last response should be returned with code 200
    And last response should contain a user with active flag equal to true
    And last response should contain a user with not changed id
    And last response should contain a user with not changed nickname


  Scenario: Update a user with a whitespaced nickname
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": true
    }
    """
    And the last created user is updated with following request body
    """
    {
      "nickname":" "
    }
    """
    Then last response should be returned with code 400
    And last response should contain "demo_app.domain.ErrorDetails" entity with following data
    """
    {
    "message": "Message body did not pass validation rules: {user: Only word characters [a-zA-Z_0-9-] are allowed.}",
    "details": "uri=/api/v1/users/<LAST_CREATED_USER_ID>"
    }
    """

  Scenario: Update a user with an empty nickname
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": true
    }
    """
    And the last created user is updated with following request body
    """
    {
      "nickname":""
    }
    """
    Then last response should be returned with code 400
    And last response should contain "demo_app.domain.ErrorDetails" entity with following data
    """
    {
    "message": "Message body did not pass validation rules: {user: Only word characters [a-zA-Z_0-9-] are allowed.} {user: Path length should be between 1 and 255 characters.}",
    "details": "uri=/api/v1/users/<LAST_CREATED_USER_ID>"
    }
    """


  Scenario: Update a user with an invalid status flag
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": true
    }
    """
    And the last created user is updated with following request body
    """
    {
      "active":"2"
    }
    """
    Then last response should be returned with code 400
