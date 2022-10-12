Feature: validate that the vehicle that the lessor wants to register has SOAT

  Scenario: As a lessor I want to register my vehicle to be able to offer the service.
    Given my soat is in order
    When click on the register button
    Then I will receive a notification indicating that my car is successfully registered
    Examples:
      | userId | car    | operation             |  notification Message      |
      | 73123  | CA-54 | createCar() | "Your car is successfully registered" |

  Scenario: Soat expired or reported
    Given I register my soat in the system
    When click on the register button
    Then I will receive an email indicating that my soat has expired or has been reported.
    Examples:
      | userId | car    | operation             |  notification Message      |
      | 73123  | CA-54 | registerCar() | "Your car was reported as past due" |


