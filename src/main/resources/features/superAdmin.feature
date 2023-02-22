Feature:SuperAdmin User Scenarios

  Background:Login with SuperAdmin
    Given Login with username and password

  Scenario:Super admin login validation and Validating Dashboard Tables
    Then Validate Login
    Then Validate data tables on Dashboard


  Scenario:Validating Toggle button option
    Then Verify options are visible after toggle click
    When Click on Manage Sections
    When Create New Manage Section
    Then Validate section is created

  Scenario Outline: Validate Tests options
    When Click on Test Section
    When Select test "Test Control" options
    When Select university as <campus> from campus dropdown
#    Then Validate availability of Test Record
    Examples:
      | campus                        |
      | Chitkara university, Chitkara |
      | Other, Other                  |
      | Rungta College, CSVTU         |
      | SRM, abc                      |


  Scenario: Validate Tests options
    When Click on Test Section
    When Select test "Questions" options



