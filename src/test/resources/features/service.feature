Feature: Service Management with Object Mapping

  Scenario: Add and process services using objects
    Given following services exist
      | service_id | name           |
      | 101        | LoginService   |
      | 102        | PaymentService |
    And system is ready
    When I process services
    Then services should be available