	#LmsApi UserApiPost request
@LmsUserApiPost
Feature: To Check if able to create new User

Scenario Outline: User is on POST method with end point url
Given User is on the Endpointurl with post method
When user sends post request with endpoint inputs data from excel <RowNumber>
Then shows a message as sucessfully created user id and updates id in excel <RowNumber>

Examples:
|RowNumber|
|0|
|1|

Scenario: To check if User able to access with invalid url
  Given User is on the postrequest  by giving invalid url
    When User sends the postMethod with incorrect url
   Then Error message with statuscode is displayed   