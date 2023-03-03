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
    When Select test Test Control options
    When Select university as <campus> from campus dropdown
#    Then Validate availability of Test Record
    Examples:
      | campus                        |
      | Chitkara university, Chitkara |
      | Other, Other                  |
      | Rungta College, CSVTU         |
      | SRM, abc                      |


  Scenario: Test Question scenario
    When Click on Test Section
    When Select test Questions options
    When Click on test tab Questions
    Then Check Download Question Template
    Then Validate Import Question Button
    Then Add New Questions working

  Scenario Outline: Test Question scenario with Other Two Options
    When Click on Test Section
    When Select test Questions options
    When Click on test tab <option>
    Then Add New <option> working
    Examples:
      | option         |
      | Comprehensions |
      | Video Based    |

  Scenario:Validating Campus option
    Then Verify options are visible after toggle click
    When Click on Campus
    Then Validate Campus page is displayed

  Scenario: Validate navigation to Registration page in Campus List
    When Verify options are visible after toggle click
    And Click on Campus
    And Click on Register Campus
    Then Validate Registration page is displayed or not


  Scenario Outline: Registering new Campus in Campus List
    When Verify options are visible after toggle click
    And Click on Campus
    And Click on Register Campus
    And Enter all the mandatory fields as "<cName>" "<cEmail>" "<location>" "<university>" "<tponame>" "<tpomail>" "<tpocontact>" "<desc>"
    Then Validate Campus Registration
    Examples:
      | cName  | cEmail         | location | university | tponame | tpomail        | tpocontact | desc            |
      | Gemini | gem@gemini.com | Gurgaon  | Test       | Tushar  | tush@gmail.com | 9898989867 | Testing Purpose |

  Scenario:Validating Campus option
    Then Verify options are visible after toggle click
    When Click on Campus
    Then Check Download Campus Template

  Scenario:Validating Campus option
    Then Verify options are visible after toggle click
    When Click on Campus
    Then Check Bulk Campus Upload

  Scenario:Validating User Management option
    Then Verify options are visible after toggle click
    When Click on User Management

  Scenario:Validating Role Management option
    Then Verify options are visible after toggle click
    When Click on Role Management


