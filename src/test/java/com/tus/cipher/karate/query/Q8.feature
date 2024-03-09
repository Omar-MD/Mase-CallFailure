Feature: Query 8
  Testing the query times

  Background: 
    * callonce read('classpath:com/tus/cipher/karate/import/man_import.feature')  
    * url baseUrl
    * def login = call read('classpath:com/tus/cipher/karate/auth.feature')
    * def authToken = login.token
    * def role = login.role
    * header Authorization = 'Bearer ' + authToken
    
  Scenario: Query 8 "query/imsi-unique-failures/"
    Given path "query/imsi-unique-failures/344930000000011"
    And header Content-Type = 'application/json'
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
    * print '[x]  query_times: query/imsi-unique-failures'