Feature: Query 7
  Testing the query times

  Background: 
    * callonce read('classpath:com/tus/cipher/karate/import/man_import.feature')  
    * url baseUrl
    * def login = call read('classpath:com/tus/cipher/karate/auth.feature')
    * def authToken = login.token
    * def role = login.role
    * header Authorization = 'Bearer ' + authToken
    
  Scenario: Query 7  "query/top10-market-operator-cellid-combinations"
    * def query = {startDate: "#(fileUtil.getDate(2019, 1, 1))", endDate: "#(fileUtil.getDate(2024, 3, 8))"}
    Given path "query/top10-market-operator-cellid-combinations"
    And header Content-Type = 'application/json'
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
    * print '[x]  query_times: query/top10-market-operator-cellid-combinations'