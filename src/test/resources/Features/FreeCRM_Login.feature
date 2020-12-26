Feature: FreeCRM_LogIn
  To test Free CRM Log In Functionality

  #Scenario 1
  Scenario Outline: Logging into Free CRM site
    Given Free CRM site is already open in browser
    When I click on Login button in landing page
    Then Log in page appears
    When I enter <Email> and <Password> and click on Login button
    Then I am successfully logged into the Free CRM application and able to see the <LoggedinUser> name

    Examples: 
      | Email                        | Password    | LoggedinUser     |
      | pganguly4@gmail.com          | BIGsmall123 | Prasanta Ganguly |
      | ganguly.prasanta@outlook.com | BIGsmall123 | Ganguly Prasanta |
