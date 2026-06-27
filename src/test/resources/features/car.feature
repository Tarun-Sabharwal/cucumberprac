@tc
Feature: Car DB Testing using TestContainers

  Scenario: Validate and store cars
    Given following car records
      | id | name  | company | price |
      | 1  | Nexon | Tata    | 12000 |
      | 2  | i20   | Hyundai | 9000  |
      | 3  | Swift | Suzuki  | -5000 |
      | 4  | ""    | Honda   | 15000 |
      | 5  | Creta | Hyundai | 18000 |
    When valid cars are inserted
    Then only valid cars should exist in database