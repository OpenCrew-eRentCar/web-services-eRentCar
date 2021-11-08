Feature: Category Adding
  As a Developer
  I want to add Category through API
  So that It can be available to applications

  Background:
    Given The Endpoint "http://localhost:8080/api/v1/categories" is available for categories

  @category-adding
  Scenario: Add Category
    When A Category Request is sent with values "Economic"
    Then A Response with Status 200 is received for the category
    And A Category Resource with values "Economic"