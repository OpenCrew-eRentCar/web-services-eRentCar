@system
Feature: User Authentication
  Scenario: User Login Success
    Given I am on the home page
    And I click on the Login link
    And I enter my username and password
      | username | password |
      | sebas1   | sebas123 |
    When I submit the login form
    Then I should see my names "Sebastian" on the page

  Scenario: User Login Failure
    Given I am on the home page
    And I click on the Login link
    And I enter my username and password
      | username | password |
      | sebas1   | sebas    |
    When I submit the login form
    Then I stay on the login page
