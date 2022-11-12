  Feature: Rents Service
  #TODO: Implement the following Rent scenarios
  Scenario: Get All Rents
    Given I am registered user for get all rents
    And Exist the following Rents in the Repository
      | id | amount | carId |
      | 1  | 2000   | 1     |
      | 2  | 3000   | 2     |
    When I get all Rents
    Then I should get a list of Rents with length 2

  Scenario: Get Rent by Id
    Given I am registered user for get all rents
    And Exist the following Rents in the Repository
      | id | amount |
      | 1  | 2000   |
      | 2  | 3000   |
    When I get Rent with id 1
    Then I should get a Rent with id 1

  Scenario: Create Rent
    Given I am registered user for get all rents
    When I complete my rent info in the system with data
      | id | amount | rate |
      | 1  | 1000   | 1.0  |
      | 2  | 2000   | 2.0  |
    Then I should get a Rent with id 1

  Scenario: Update Rent
    Given I am registered user for get all rents
    When I update my Rent info with
      | id | amount |
      | 1  | 3000   |
    Then I should get a Rent with id 1 and amount 1000

  Scenario: Delete Rent
    Given I am registered user for get all rents
    And Exist the following Rents in the Repository
      | id | amount |
      | 1  | 1000   |
      | 2  | 2000   |
    When I delete my Rent
    Then I should not get a Rent with id 1