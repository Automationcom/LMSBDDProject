#UserApi Put Request
@UserApiPut
Feature: User is tries to modify details
Scenario Outline: User updates the existing details
Given User is on the PutMethod with the Endpoint url
When user is on the PutMethod gives details to update from excel <rownum>
Then message response should be sucessfully updated
Examples:
|rownum|
|0|
Scenario: User tries to update the non existing User details
Given  User is on the Endpointurl  with giving non existing UserId
When User is on put method to update the details
Then No User found should be shown 