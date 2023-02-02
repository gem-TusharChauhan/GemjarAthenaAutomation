Feature: Athena Automation

  Scenario Outline: Checking login through invalid user
    Given enter the invalid username as <username> and password as <password>
    Then click on signin and capture error
    Then Click on forgot password
    Then Click back to login
    Then Move to signup and enter details
    Examples:
      | username  | password  |
      | tushar@g.com  | jg5gyh |
      | tusharch@gemi.com  | jgB66 |
