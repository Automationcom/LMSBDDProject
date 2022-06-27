#Background: Test users API Get request

@LmsUserApiGetrequest

Feature: Authorization is set as user has already created an Account

  Scenario: To Get all users details
    Given user enters detials with Endpoint/url/Users
    When user sends the get request
    Then should gets list of  of all users

  @Testone
  Scenario: To Get User details for specific user Id
    Given user is on get method with Endpoint url
    When user sends request for specific UserID
    Then User recieves status code 200 and should gets the details of specific userId

  Scenario: To check  with incorrect endpoint url
    Given user sends the request  method with incorrect url
    When user is on get method invalid url
    Then user recieves 404 error message

  Scenario: To check if user able to access with valid Basic Auth details
    Given User is on Get Method with Basic Authdetails
    When User sends get request by giving valid Basic Authdetails
    Then User is able to acess theApi with valid credentials
