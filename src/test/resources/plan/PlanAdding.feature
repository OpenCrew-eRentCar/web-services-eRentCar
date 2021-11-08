Feature: Plan Adding
  As a Developer
  I want to add Plan through API
  So that It can be available to applications

  Background:
    Given The Endpoint "http://localhost:8080/api/v1/plans" is available for plans

  @plan-adding
  Scenario: Add Plan
    When A Plan Request is sent with values "Regular", "One car", 20
    Then A Response with Status 200 is received for the plan
    And A Plan Resource with values "Regular", "One car", 20