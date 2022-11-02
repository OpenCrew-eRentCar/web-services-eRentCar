Feature: CarBrands Service

  Scenario: Get All Car Brands
    Given I am Manager User
    And Exist the following Car Brands in the repository
      | id | name |
      | 1  | Onda |
      | 2  | Kia  |
    When I get all Car Brands
    Then I should get a list of Car Brands with length 2

  Scenario: Get Car Brand by Id
    Given I am Manager User
    And Exist the following Car Brands in the repository
      | id | name |
      | 1  | Onda |
      | 2  | Kia  |
    When I get Car Brand with id 1
    Then I should get a Car Brand with id 1

  Scenario: Create Car Brand
    Given I am Manager User
    When I complete my Car Brand info in the system with data
      | id | name | imagePath         |
      | 1  | Onda | https://image.com |
    Then I should get a Car Brand with id 1

  Scenario: Update Car Brand
    Given I am Manager User
    When I update my Car Brand info with
      | id | name |
      | 1  | Kia  |
    Then I should get a Car Brand with id 1 and name "Kia"

  Scenario: Delete Car Brand
    Given I am Manager User
    And Exist the following Car Brands in the repository
      | id | name |
      | 1  | Onda |
      | 2  | Kia  |
    When I delete my Car Brand
    Then I should not get a Car Brand with id 1
