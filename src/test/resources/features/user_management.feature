Feature: User Management Service

  Background: User is authenticated
    Given the user "testadmin" is registered and logged in with password "password123"

  Scenario: Get all users successfully
    When the user attempts to get all users
    Then the request should be successful with status code 200
    And the response should contain a list of users

  Scenario Outline: Get user by ID
    Given a user exists with ID <userId>
    When the user attempts to get the user by ID <userId>
    Then the request should be successful with status code 200
    And the response should contain the details of user <userId>

    Examples:
      | userId |
      | 1      |

  Scenario Outline: Get user by non-existent ID
    When the user attempts to get the user by ID <userId>
    Then the request should fail with status code 404
    And the response message should indicate that the user with ID <userId> was not found

    Examples:
      | userId |
      | 9999   |

  Scenario Outline: Create a new user successfully
    Given the user wants to create a user with username "<username>", email "<email>", password "<password>", and role "<role>"
    When the user attempts to create the user
    Then the request should be successful with status code 200
    And the response should contain the created user details excluding the password

    Examples:
      | username      | email             | password    | role  |
      | createduser1  | created1@test.com | password123 | USER  |

  Scenario Outline: Update an existing user successfully
    Given a user exists with ID <userId>
    And the user wants to update user <userId> with username "<new_username>", email "<new_email>", and role "<new_role>"
    When the user attempts to update the user
    Then the request should be successful with status code 200
    And the response should contain the updated user details for <userId>

    Examples:
      | userId | new_username | new_email        | new_role |
      | 1      | updateduser1 | updated1@test.com | ADMIN    |


  Scenario Outline: Delete an existing user successfully
    Given a user exists with ID <userId>
    When the user attempts to delete the user by ID <userId>
    Then the request should be successful with status code 200

    Examples:
      | userId |
      | 2      |

  Scenario Outline: Delete a non-existent user
    When the user attempts to delete the user by ID <userId>
    Then the request should fail with status code 404
    And the response message should indicate that the user with ID <userId> was not found

    Examples:
      | userId |
      | 9998   |

  Scenario: Attempt to access user management without authentication
    Given the user is not authenticated
    When the user attempts to get all users
    Then the request should fail with status code 401 or 403
