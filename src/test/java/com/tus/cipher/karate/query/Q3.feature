Feature: Query 3
  Testing the query times

  Background: 
    * callonce read('classpath:com/tus/cipher/karate/import/man_import.feature')  
    * url baseUrl
    * def login = call read('classpath:com/tus/cipher/karate/auth.feature')
    * def authToken = login.token
    * def role = login.role
    * header Authorization = 'Bearer ' + authToken

  Scenario: Query 3  "query/imsi-failures-time"
    * def query = {startDate: "#(FileUtil.getDate(2015, 1, 27))", endDate: "#(FileUtil.getDate(2024, 2, 24))"}
    Given path "query/imsi-failures-time"
    And header Content-Type = 'application/json'
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
    * print '[x]  query_times: query/imsi-failures-time'