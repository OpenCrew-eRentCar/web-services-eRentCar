Feature: Plans Service

  Scenario: Get All Plans
    Given I'm User Administrator
    And the following plans exist in the repository
      | id | name         |
      | 1  | BASIC        |
      | 2  | PREMIUM      |
      | 3  | GOLD         |
    When I get all Plans
    Then I should get a list of Plans with length 3

  Scenario: Get Plan by Id
    Given I'm User Administrator
    And the following plans exist in the repository
      | id | name         |
      | 1  | BASIC        |
      | 2  | PREMIUM      |
      | 3  | GOLD         |
    When I get a Plan with id 1
    Then I should get a Plan with id 1

  Scenario: Update Plan
    Given I'm User Administrator
    When I update my Plan info with
      | id | name    | price |
      | 1  | PREMIUM | 30    |
    Then I should get a Plan with id 1 and price 30

  Scenario: Delete Plan
    Given I'm User Administrator
    And the following plans exist in the repository
      | id | name         |
      | 1  | BASIC        |
      | 2  | PREMIUM      |
    When I delete my Plan
    Then I should not get a Plan with id 1
