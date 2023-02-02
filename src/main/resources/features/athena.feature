Feature: Athena Automation

 Scenario Outline: Checking login through valid user
   Given Navigating to Athena portal
   When Entering username <user> and <pass> password
   Then Count <test> tests

   Examples:
     | user | pass | test|
     | susheel7@gmail.com | abc@1234 | Active|
     | susheel7@gmail.com | abc@1234 | Completed|

  Scenario Outline: Validate the navigating back to dashboard url
   Given Navigating to my profile after login <user> and <pass>
   When Capture the profile data
   Then Click on Explore test and validate url
    Examples:
      | user | pass |
      | susheel7@gmail.com | abc@1234 |



  Scenario Outline: Creating new password and validating successful login
    Given Login to Athena portal using username <user> and <pass> password
    When Navigating to change password <pass> and creating new password <newpass>
    Then Setting new password as <newpass> and checking <user> relogin
    Examples:
      |  user            |pass    | newpass  |
      |tushar1@gmail.com|abc@123| abc@1234 |


  Scenario Outline: Checking login through invalid user
    Given enter the invalid username as <username> and password as <password>
    When clicking on signin
    Then Validate login
    Examples:
      | username            | password  |
      | susheel7@gamil.com  | NULL      |
      | NULL                | aabc@123  |

    Scenario Outline: Signup and creating new user
      Given Clicking on signup
      Then Entering all details in fields as email<email> password<pass> confirmpass<cnfrmpass> firstname<firstname> lastname<lastname> contact<contact> experience<experience> expcount<expcount> and campus<campus>
      Then Validating signup
      Examples:
        | email              |pass    | cnfrmpass| firstname|lastname | contact   |experience | expcount     |campus |
        | tushar12@gmail.com |abc@123 | abc@123  | Tushar   |Chauhan  | 8796978697|Fresher    | NULL         |Chitkara university, Chitkara |
        | tushar21@gmail.com |abc@123 | abc@123  | Tushar   |Chauhan  | 8796970097|Experience | 5            |NULL|

    Scenario Outline: Forgot password
      Given Clicking on forgot password link
      When Entering the username <user>
      Then Validating reset mail sent or not
      Examples:
        | user           |
        |Hello1@gmail.com|
        |Hello@gamil.com |