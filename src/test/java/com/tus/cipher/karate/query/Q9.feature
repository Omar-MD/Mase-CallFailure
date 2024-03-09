Feature: Query 9
  Testing the query times

  Background: 
    * callonce read('classpath:com/tus/cipher/karate/import/man_import.feature')  
    * url baseUrl
    * def login = call read('classpath:com/tus/cipher/karate/auth.feature')
    * def authToken = login.token
    * def role = login.role
    * header Authorization = 'Bearer ' + authToken
    
  Scenario: Query 9  "query/top10-imsi-failures-time"
  * def query = {startDate: "#(fileUtil.getDate(2019, 4, 4))", endDate: "#(fileUtil.getDate(2024, 1, 2))"}
  Given path "query/top10-imsi-failures-time"
  And header Content-Type = 'application/json'
  And params query
  When method GET
  Then response.statusCode == 200
  And match response.status == 'Success'
  And assert responseTime < 2000
  * print '[x]  query_times: query/top10-imsi-failures-time'