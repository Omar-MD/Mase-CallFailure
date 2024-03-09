Feature: Authenticate
  To test the login

Background: Setup the Base path
   * url baseUrl
 
Scenario: Successful Login
    Given path '/authenticate'
    And request { username: '#(username)', password: '#(password)' }
    And header Content-Type = 'application/json'
    When method post
    Then status 200
    And def authToken = response.data.token
    
    
   
   
  