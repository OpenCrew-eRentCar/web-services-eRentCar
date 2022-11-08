Feature: CarModels Service

  Scenario: Get All Car Models
    Given I'm Administrator User
    And Exist the following Car Models in the repository
      | id | name  |
      | 1  | Versa |
      | 2  | Urban |
    When I get all Car Models
    Then I should get a list of Car Models with length 2

  Scenario: Get Car Model by Id
    Given I'm Administrator User
    And Exist the following Car Models in the repository
      | id | name  |
      | 1  | Versa |
      | 2  | Urban |
    When I get Car Model with id 1
    Then I should get a Car Model with id 1

  Scenario: Create Car Model
    Given I'm Administrator User
    When I complete my Car Model info in the system with data
      | id | name  |
      | 1  | Versa |
    Then I should get a Car Model with id 1

  Scenario: Update Car Model
    Given I'm Administrator User
    When I update my Car Model info with
      | id | name  |
      | 1  | Urban |
    Then I should get a Car Model with id 1

  Scenario: Delete Car Model
    Given I'm Administrator User
    And Exist the following Car Models in the repository
      | id | name  |
      | 1  | Versa |
      | 2  | Urban |
    When I delete my Car Model
    Then I should not get a Car Model with id 1
