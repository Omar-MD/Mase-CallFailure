Feature: Testing Query Response Times
  Testing to ensure each response time is less than 2 seconds

  Background: 
    * def result = karate.callSingle('prep_query.feature')
    * url baseUrl

  Scenario: Query 1 reponse time check "/imsi-failures/{imsi}"
    Given path "/imsi-failures/344930000000011"
    And header Content-Type = 'application/json'
    When method post
    Then response.statusCode == 200
    * print response
    And match response.status == 'Success'
    And assert responseTime < 500
  
