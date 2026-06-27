Feature: Login Functionality

  Scenario: Successful login with valid credentials
    Given user is on login page
    When user enters username "admin" and password "admin123"
    Then user should be logged in successfully

  Scenario: Failed login with invalid credentials
    Given user is on login page
    When user enters username "user" and password "wrong"
    Then login should fail





