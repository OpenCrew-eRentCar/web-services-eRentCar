Feature: Favourites Service
  Scenario: Add favourite
    Given I am a Client
      | id | names |
      | 1  | Bob  |
    And Exists a Car with data
      | id | clientId | clientNames |
      | 1  | 2        | Juan        |
    When I add that car to my Favourites
    Then I should see that car in my Favourites

  Scenario: Get Favourites
    Given I am a Client
      | id | names |
      | 1  | Bob  |
    And Have a Favourites Cars
      | id | carId |
      | 1  | 1     |
      | 2  | 2     |
    When I get my Favourites
    Then I should get my Favourites with length 2

  Scenario: Get Favourite by id
    Given I am a Client
      | id | names |
      | 1  | Bob  |
    And Have a Favourites Cars
      | id | carId |
      | 1  | 1     |
      | 2  | 2     |
    When I get my Favourites by id 1
    Then I should get the Favourite Car with id 1

  Scenario: Delete Favourite by id
    Given I am a Client
      | id | names |
      | 1  | Bob  |
    And Have a Favourites Cars
      | id | carId |
      | 1  | 1     |
      | 2  | 2     |
    When I delete my Favourite by id 1
    Then I should get my Favourites with length 1