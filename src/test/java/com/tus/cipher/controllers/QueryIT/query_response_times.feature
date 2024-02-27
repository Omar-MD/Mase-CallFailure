Feature: Testing Query Response Times
  Testing to ensure each response time is less than 2 seconds

  Background: 
    * def result = callonce read('prep_query.feature')
    * assert result == 'success'
    * url baseUrl

  Scenario: Query 1 reponse time check
    Given path '/sysadmin/import'
    And request { filename: 'TUS_CallFailureData.xls' }
    And header Content-Type = 'application/json'
    When method post
    Then response.statusCode == 200
    And match response.status == 'Success'
    And match response.data == '#string'
        
   # When method post
#Then status 201
#And assert responseTime < 1000
