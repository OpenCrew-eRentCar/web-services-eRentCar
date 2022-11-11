Feature: Reservations Service
  #TODO: Implement the following Reservation scenarios
  Scenario: Get All Reservations
    Given I am a User (Reservation)
    And Exist the following Reservation in the repository
      | id | names | startDate | finishDate| amount|
      | 1  | Rent 1 | 12/12/21 | 21/12/13  | 1000  |
      | 2  | Rent 2 | 11/11/11 | 11/11/23  | 2000  |
    When I get all Reservations
    Then I should get a list of Reservations with length 2
