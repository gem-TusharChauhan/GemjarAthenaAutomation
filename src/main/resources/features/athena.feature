Feature: Athena Portal Automation

 Scenario Outline: Athena-Validate Active and Completed tests
   Given Navigating to Athena portal
   When Entering username <user> and <pass> password
   Then Verify <test> tests

   Examples:
     | user               | pass     | test|
     | susheel7@gmail.com | abc@1234 | Active|
     | susheel7@gmail.com | abc@1234 | Completed|
     | susheel7@gmail.com | abc@1234 | Comp|

  Scenario Outline: Validate My Profile data and Explore test
   Given Navigate to my profile after login <user> and <pass>
   When  Validating User
   Then Click on Explore test and validate URL
    Examples:
      | user               | pass |
      | susheel7@gmail.com | abc@1234 |


  Scenario Outline: Change Password
    Given Login to Athena portal using username <user> and <pass> password
    When Navigating to change password <pass> and creating new password <newpass>
    Then Setting new password as <newpass> and checking <user> relogin
    Examples:
      |  user           |pass    | newpass |
      |tushar1@gmail.com|abc@1234| abc@123 |


  Scenario Outline: Unsuccessful login
    Given Enter username as <username> and password as <password>
    When Click on signin
    Then Validate login
    Examples:
      | username            | password  |
      | susheel7@gamil.com  | NULL      |
      | NULL                | aabc@123  |


    Scenario Outline: User Creation
      Given Click on signup
      Then Entering all details in fields as email<email> password<pass> confirmpass<cnfrmpass> firstname<firstname> lastname<lastname> contact<contact> experience<experience> expcount<expcount> and campus<campus>
      Then Validating signup
      Examples:
        | email              |pass    | cnfrmpass| firstname|lastname | contact   |experience | expcount     |campus |
        | tushar39@gmail.com |abc@123 | abc@123  | Tushar   |Chauhan  | 8796978697|Fresher    | NULL         |Chitkara university, Chitkara |
        | tushar35@gmail.com |abc@123 | abc@123  | Tushar   |Chauhan  | 8796970097|Experience | 5            |NULL|

    Scenario Outline: Forgot Password Validations
      Given Click on forgot password link
      When Entering the username <user>
      Then Validating reset mail sent or not to <user>
      Examples:
        | user           |
        |Hello1@gmail.com|
        |Hell@gamil.com  |