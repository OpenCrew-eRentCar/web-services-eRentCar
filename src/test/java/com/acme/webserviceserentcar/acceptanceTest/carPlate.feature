Feature: validate that the license plate number doesn't exist in the db

  Scenario: As a lessor I want my car not to be able to register in the system twice to avoid generating an invoce.
    Given I forget that my car was already registered in the system.
    When click on register my car
    Then I will receive a notification  indicating that my car is already registered
    Examples:
      | userId | car    | operation             |  notification Message      |
      | 73123  | CA-54 | createCar() | "Your car is already registered" |

