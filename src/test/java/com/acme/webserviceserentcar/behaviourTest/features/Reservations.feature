Feature: Reservations Service
  #TODO: Implement the following Reservation scenarios
  Scenario: Get All Reservations
    Given I am a User
    And Exist the following Reservation in the repository
      | id | names |
      | 1  | Rent 1 |
      | 2  | Rent 2 |
    When I get all Reservations
    Then I should get a list of Reservations with length 2
