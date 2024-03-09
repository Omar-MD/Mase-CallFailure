Feature: Authenticate
  To test the login

  Scenario: Successful Login
    Given url  baseUrl + '/authenticate'
    And request { username: '#(username)', password: '#(password)' }
    And header Content-Type = 'application/json'
    When method post
    Then status 200
    And def authToken = response.data.token
