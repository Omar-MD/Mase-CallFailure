Feature: Support Engineer Queries
  Testing the query times

  Background: 
    * url baseUrl
    * def supportToken = karate.get('supportToken') == null ? getAuthToken('support', 'password', 'SE') : karate.get('supportToken')
    * header Authorization = 'Bearer ' + supportToken

  Scenario: Query "query/imsi-failures-time"
    * def query = {startDate: "#(fileUtil.getDate(2015, 1, 27))", endDate: "#(fileUtil.getDate(2024, 2, 24))"}
    Given path "query/imsi-failures-time"
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < maxQueryTime
    * print '[x]  query_times: query/imsi-failures-time'

  Scenario: Query "query/model-failure-count"
    * def query = {tac: 33000253, startDate: "#(fileUtil.getDate(2015, 1, 27))", endDate: "#(fileUtil.getDate(2024, 2, 24))"}
    Given path "query/model-failure-count"
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < maxQueryTime
    * print '[x]  query_times: query/model-failure-count'

  Scenario: Inherited Customer Service Rep queries
    * call read('customer_queries.feature') { 'customerToken': '#(supportToken)' }
