Feature: Network Engineer Queries
  Testing the query times

  Background: 
    * url baseUrl
    * def engineerToken = getAuthToken('engineer', 'password', 'NE')
    * header Authorization = 'Bearer ' + engineerToken

  Scenario: Query "query/imsi-failures-count-duration"
    * def query = {startDate: "#(fileUtil.getDate(2015, 1, 27))", endDate: "#(fileUtil.getDate(2024, 2, 24))"}
    Given path "query/imsi-failures-count-duration"
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < maxQueryTime
    * print '[x]  query_times: query/imsi-failures-count-duration'

  Scenario: Query "query/model-failures/{tac}"
    * def query = {tac: 33000253}
    Given path "query/model-failures/" + query.tac
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < maxQueryTime
    * print '[x]  query_times: query/model-failures/{tac}'

  Scenario: Inherited Support Engineer queries
    * call read('support_queries.feature') { 'supportToken': '#(engineerToken)' }
