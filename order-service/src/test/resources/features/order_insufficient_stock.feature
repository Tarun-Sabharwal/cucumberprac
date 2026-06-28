Feature: Order rejected due to insufficient stock

  Scenario: Reject order when requested quantity exceeds available stock
    Given inventory has limited stock for "Mouse" with quantity 2
    When I attempt to place an order for "Mouse" with quantity 5
    Then the order request should be rejected with status code 400
