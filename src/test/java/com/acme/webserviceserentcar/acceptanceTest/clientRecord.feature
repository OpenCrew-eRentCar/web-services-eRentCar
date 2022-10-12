Feature: Tenant validate record

  Scenario: As an Tenant I want to validate my driver's license record to reserve a vehicle.
    Given I want to reserve a vehicle available in the vehicle section
    And the minimum <record> is satisfied.
    And fill out the reservation form
    When click on send reservation request
    Then I will receive an email confirming that my reservation has been sent.
    Examples:
      | userId | car    | operation             | email notification Message      |
      | 73123  | CA-312 | sendReservationForm() | "Reservation successfully sent" |


  Scenario: Tenant's license record to reserve a vehicle does not meet the requirements.
    Given I want to reserve a vehicle available in the vehicle section
    And the minimum <record> is not satisfied.
    And fill out the reservation form
    When click on send reservation request
    Then I will receive a notification of reservation denied.
    Examples:
      | userId | car    | operation             | notification Message  |
      | 73123  | CA-312 | sendReservationForm() | "Reservation denied" |