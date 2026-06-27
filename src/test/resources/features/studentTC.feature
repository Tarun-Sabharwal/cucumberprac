
Feature: Student DB Testing with TestContainers

  Scenario: Validate and store students using PostgreSQL
    Given following student records
      | id | name  | score |
      | 1  | Tarun | 80    |
      | 2  | Ankit | 60    |
      | 3  | Priya | 110   |
      | 4  | Rahul | -10   |
      | 5  | Neha  | 75    |
    When valid students are inserted into postgres
    Then only valid students should exist in database
