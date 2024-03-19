Feature: Customer Service Rep queries
  Testing the query times

  Background: 
    * url baseUrl
    * def customerToken = karate.get('customerToken') == null ? getAuthToken('customer', 'password', 'CSR') : karate.get('customerToken')
    * header Authorization = 'Bearer ' + customerToken

  Scenario: Query 1 "query/imsi-failures/{imsi}"
    Given path "query/imsi-failures/344930000000011"
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < maxQueryTime
    * print '[x]  query_times: query/imsi-failures/{imsi}'

  Scenario: Query 2 "query/imsi-failure-count-time"
    * def query = {imsi: 344930000000011, startDate: "#(fileUtil.getDate(2015, 1, 27))", endDate: "#(fileUtil.getDate(2024, 2, 24))"}
    Given path "query/imsi-failure-count-time"
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < maxQueryTime
    * print '[x]  query_times: query/imsi-failure-count-time'

  Scenario: Query 8 "query/imsi-unique-failures/"
    Given path "query/imsi-unique-failures/344930000000011"
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
    * print '[x]  query_times: query/imsi-unique-failures'