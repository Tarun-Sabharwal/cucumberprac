Feature: Access Management System

 Scenario: is User Authorized
   Given Following user exists
   |id | name | access|
   | 1 | John | true  |
   | 2 | Tone | false |
   | 3 | Bohn | true  |
   | 4 | Sohn | false |
   | 5 | Pohn | true  |

   When access is true
   Then only authorized users allowed





