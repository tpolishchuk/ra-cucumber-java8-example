Feature: Create a user

  Scenario: Create a valid user
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": true
    }
    """
    Then last response should be returned with code 200
    And last response should contain "demo_app.domain.User" entity similar to requested for creation


  Scenario: Create a valid user with long nickname
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_STRING_LENGTH_251>",
      "active": false
    }
    """
    Then last response should be returned with code 200
    And last response should contain "demo_app.domain.User" entity similar to requested for creation


  Scenario: Create a user with an invalid name
    When user is created with following request body
    """
    {
      "nickname":"USR*<RANDOM_INT>",
      "active": true
    }
    """
    Then last response should be returned with code 400
    And last response should contain "demo_app.domain.ErrorDetails" entity with following data
    """
    {
    "message": "Message body did not pass validation rules: {user: Only word characters [a-zA-Z_0-9-] are allowed.}",
    "details": "uri=/api/v1/users"
    }
    """


  Scenario: Create a user with a name equals to whitespace
    When user is created with following request body
    """
    {
      "nickname":" ",
      "active": true
    }
    """
    Then last response should be returned with code 400
    And last response should contain "demo_app.domain.ErrorDetails" entity with following data
    """
    {
    "message": "Message body did not pass validation rules: {user: Only word characters [a-zA-Z_0-9-] are allowed.} {user: Path value should not be blank.}",
    "details": "uri=/api/v1/users"
    }
    """


  Scenario: Create a user with an empty name
    When user is created with following request body
    """
    {
      "nickname":"",
      "active": true
    }
    """
    Then last response should be returned with code 400
    And last response should contain "demo_app.domain.ErrorDetails" entity with following data
    """
    {
    "message": "Message body did not pass validation rules: {user: Only word characters [a-zA-Z_0-9-] are allowed.} {user: Path length should be between 1 and 255 characters.} {user: Path value should not be blank.}",
    "details": "uri=/api/v1/users"
    }
    """


  Scenario: Create a user without a name
    When user is created with following request body
    """
    {
      "active": true
    }
    """
    Then last response should be returned with code 400
    And last response should contain "demo_app.domain.ErrorDetails" entity with following data
    """
    {
    "message": "Message body did not pass validation rules: {user: Path value should not be blank.} {user: User name is mandatory.}",
    "details": "uri=/api/v1/users"
    }
    """


  Scenario: Create a user with a name equal to null
    When user is created with following request body
    """
    {
      "nickname":null,
      "active": true
    }
    """
    Then last response should be returned with code 400
    And last response should contain "demo_app.domain.ErrorDetails" entity with following data
    """
    {
    "message": "Message body did not pass validation rules: {user: Path value should not be blank.} {user: User name is mandatory.}",
    "details": "uri=/api/v1/users"
    }
    """


  Scenario: Create a user with too long nickname
    When user is created with following request body
    """
    {
      "nickname":"<RANDOM_STRING_LENGTH_256>",
      "active": true
    }
    """
    Then last response should be returned with code 400
    And last response should contain "demo_app.domain.ErrorDetails" entity with following data
    """
    {
    "message": "Message body did not pass validation rules: {user: Path length should be between 1 and 255 characters.}",
    "details": "uri=/api/v1/users"
    }
    """


  Scenario: Create a user without a status flag
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>"
    }
    """
    Then last response should be returned with code 400
    And last response should contain "demo_app.domain.ErrorDetails" entity with following data
    """
    {
    "message": "Message body did not pass validation rules: {user: User active flag is mandatory.}",
    "details": "uri=/api/v1/users"
    }
    """

  Scenario: Create a user with null as a status flag
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": null
    }
    """
    Then last response should be returned with code 400
    And last response should contain "demo_app.domain.ErrorDetails" entity with following data
    """
    {
    "message": "Message body did not pass validation rules: {user: User active flag is mandatory.}",
    "details": "uri=/api/v1/users"
    }
    """

  Scenario: Create a user with an invalid status flag
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": "2"
    }
    """
    Then last response should be returned with code 400


  Scenario: Create a user with non-unique name
    When user is created with following request body
    """
    {
      "nickname":"USR-<RANDOM_INT>",
      "active": true
    }
    """
    And user is created with the same request as before
    Then last response should be returned with code 400
