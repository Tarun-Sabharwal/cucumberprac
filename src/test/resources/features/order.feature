Feature: Order Management

  Scenario: Create and process orders
    Given following orders exist
      | order_id | product      | quantity |
      | 201      | Laptop       | 20       |
      | 202      | Mobile       | 10       |
      | 203      | Mouse        | 30       |
      | 204      | Monitor      | 50       |
      | 205      | CPU          | 15       |
      | 206      | Keyboard     | 0      |
    And user is authorized
    When I process the orders
    Then orders should be processed successfully