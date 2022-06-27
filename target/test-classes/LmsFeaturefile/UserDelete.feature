#UserApiLMS Delete request
@UserApiDelete
Feature: users API Delete method
Scenario Outline: Delete an existing User detail 
Given user is on the Endpoint url trying to delete the details
When Delete an existing User details fro sheet <rownum>
Then user should get the messageresponse Successfullydeleted
Examples:
|rownum|
|0|
 Scenario: User is trying to send delete request with non existing UserId
Given user is on Endpoint url delete method
When User delete the request using invalid Userid
Then user should get the error messageresponse