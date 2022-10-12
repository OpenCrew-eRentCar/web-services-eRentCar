Feature: Clients Service

  Scenario: Get All Clients
    Given I am Admin User
    And Exist the following Clients in the repository
      | id | names |
      | 1  | John  |
      | 2  | Jane  |
    When I get all Clients
    Then I should get a list of Clients with length 2

  Scenario: Get Client by Id
    Given I am Admin User
    And Exist the following Clients in the repository
      | id | names |
      | 1  | John  |
      | 2  | Jane  |
    When I get Client with id 1
    Then I should get a Client with id 1

  Scenario: Create Client
    Given I am a User
      | id | email          |
      | 1  | john@email.com |
    When I complete my register in the system with data
      | id | names | firstName | lastName | dni      |
      | 1  | John  | Juan      | Suarez   | 87654321 |
    Then I should get a Client with id 1

  Scenario: Update Client
    Given I am a Client (Client)
      | id | names |
      | 1  | John  |
    When I update my data with
      | id | names |
      | 1  | Juan  |
    Then I should get a Client with id 1 and names "Juan"

  Scenario: Update Client Plan
    Given I am a Client without plan
      | id | names | planId |
      | 1  | John  | null   |
    And Exist the following Plans in the repository
      | id | planName |
      | 1  | PREMIUM  |
    When I update my plan with id 1
    Then I should get a Client with id 1 and planId 1

  Scenario: Delete Client
    Given I am a Client (Client)
      | id | names |
      | 1  | John  |
    And Exist the following Clients in the repository
      | id | names |
      | 1  | John  |
      | 2  | Jane  |
    When I delete my account
    Then I should not get a Client with id 1
