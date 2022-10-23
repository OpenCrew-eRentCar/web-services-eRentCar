Feature: Cars Service
  #TODO: Implement the following Car scenarios
  Scenario: Get All Cars
    Given I am registered user
    And Exist the following Cars in the repository
      | id | licensePlate |
      | 1  | AKR-079      |
      | 2  | R-079        |
    When I get all Cars
    Then I should get a list of Cars with length 2

  Scenario: Get Car by Id
    Given I am registered user
    And Exist the following Cars in the repository
      | id | licensePlate |
      | 1  | AKR-079      |
      | 2  | R-079        |
    When I get Car with id 1
    Then I should get a Car with id 1

  Scenario: Create Car
    Given I am registered user
    When I complete my car info in the system with data
      | id | licensePlate | carModelId  | address                          | seating | mileage | imagePath         | carValueInDollars | extraInformation | year |
      | 1  | AKR-079      | 1           | Cabo blanco con tres Marias #342 | 4       | 36      | https://image.com | 13000             | Lunas rotas      | 2007 |
    Then I should get a Car with id 1

  Scenario: Update Car
    Given I am registered user
    When I update my car info with
      | id | licensePlate | carModelId  | address                          | seating | mileage | imagePath         | carValueInDollars | extraInformation | year |
      | 1  | AKR-079      | 1           | Cabo blanco con tres Marias #342 | 4       | 36      | https://image.com | 13000             | Lunas rotas      | 2007 |
    Then I should get a Car with id 1 and year 2007

  #Scenario: Search Cars
  #  Given I am registered user
  #  When I search a car with year 2007
  #  Then I should get all cars with year 2007 if exist

  Scenario: Delete Car
    Given I am registered user
    And Exist the following Cars in the repository
      | id | licensePlate |
      | 1  | AKR-079      |
      | 2  | R-079        |
    When I delete my car
    Then I should not get a Car with id 1