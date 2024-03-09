Feature: Query 5
  Testing the query times

  Background: 
    * callonce read('classpath:com/tus/cipher/karate/import/man_import.feature')  
    * url baseUrl
    * def login = call read('classpath:com/tus/cipher/karate/auth.feature')
    * def authToken = login.token
    * def role = login.role
    * header Authorization = 'Bearer ' + authToken

  Scenario: Query 5 "query/model-failures/{tac}"
    * def query = {tac: 33000253}
    Given path "query/model-failures/33000253"
    And header Content-Type = 'application/json'
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
    * print '[x]  query_times: query/model-failures/{tac}'