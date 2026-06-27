Feature: Student Validation and DB Testing

  Scenario: Validate and store student data
    Given following students exist
      | id | name  | score |
      | 1  | Tarun | 80    |
      | 2  | Ankit | 60    |
      | 3  | Priya | 110   |
      | 4  | Rahul | -10   |
      | 5  | Neha  | 75    |
      | 6  | Sima  | 0     |
      | 5  | Divya  | 100    |
    When valid students are stored in database
    Then only valid students should be stored
    And high scoring students should be validated