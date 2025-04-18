Feature: Authentication Service

  Scenario Outline: User logs in successfully
    Given a registered user with username "<username>" and password "<password>"
    When the user attempts to log in with username "<username>" and password "<password>"
    Then the login attempt should be successful
    And a valid JWT token should be returned

    Examples:
      | username   | password    |
      | testuser   | password123 |

  Scenario Outline: User fails to log in with invalid credentials
    Given a registered user exists
    When the user attempts to log in with username "<username>" and password "<password>"
    Then the login attempt should fail with status code 401
    And the response message should indicate "Invalid username or password"

    Examples:
      | username     | password       | description         |
      | wronguser    | password123    | Invalid username    |
      | testuser     | wrongpassword  | Invalid password    |

  Scenario Outline: New user registers successfully
    Given the user wants to register with username "<username>", email "<email>", and password "<password>"
    When the user attempts to register
    Then the registration should be successful with status code 200
    And the response should contain the registered user details excluding the password

    Examples:
      | username        | email                 | password    | role  |
      | newuser_success | success@example.com   | password123 | USER  |

  Scenario Outline: User fails to register with an existing username
    Given a user is already registered with username "existinguser"
    And the user wants to register with username "existinguser", email "<email>", and password "<password>"
    When the user attempts to register
    Then the registration should fail with status code 409
    And the response message should indicate that the username "existinguser" is already taken

    Examples:
      | email                | password    |
      | unique@example.com   | password123 |

  Scenario Outline: User fails to register with an existing email
    Given a user is already registered with email "existing@example.com"
    And the user wants to register with username "<username>", email "existing@example.com", and password "<password>"
    When the user attempts to register
    Then the registration should fail with status code 409
    And the response message should indicate that the email "existing@example.com" is already registered

    Examples:
      | username        | password    |
      | uniqueuser      | password123 |
