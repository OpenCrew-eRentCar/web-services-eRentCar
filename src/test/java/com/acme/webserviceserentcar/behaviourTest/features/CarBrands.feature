Feature: CarBrands Service

  Scenario: Get All Car Brands
    Given I am Admin Register User
    And Exist the following Car Brands in the repository
      | id | name  |
      | 1  | Onda  |
      | 2  | Kia   |
    When I get all Car Brands
    Then I should get a list of Car Brands with length 2

  Scenario: Get Car Brand by Id
    Given I am Admin Register User
    And Exist the following Car Brands in the repository
      | id | name |
      | 1  | Onda |
      | 2  | Kia  |
    When I get Car Brand with id 1
    Then I should get a Car Brand with id 1

  Scenario: Create Car Brand
    Given I am a User
    And I want to register a brand
    When I complete the register in the system with data
      | id | name |
      | 1  | Onda |
    Then I should get a Car Brand with id 1

  Scenario: Update Car Brand
    Given I am a User
    And I have a Car Brand
      | id | name |
      | 1  | Onda |
    When I update the data with
      | id | name |
      | 1  | Kia  |
    Then I should get a Car Brand with id 1 and names "Kia"

  Scenario: Delete Car Brand
    Given I am a User
    And I have these Car Brand
      | id | name |
      | 1  | Onda  |
      | 2  | Kia  |
    When I delete a Car Brand
      | id | name |
      | 1  | Onda  |
    Then I should not get a Car Brand with id 1