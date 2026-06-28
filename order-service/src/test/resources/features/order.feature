Feature: Order creation

  Scenario: Place an order when stock is sufficient
    Given inventory has stock for "Camera" with quantity 50
    When I place an order for "Camera" with quantity 5
    Then the order should be created with status "PENDING"
