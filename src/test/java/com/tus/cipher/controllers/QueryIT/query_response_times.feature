Feature: Testing Query Response Times
  Testing to ensure each response time is less than 2 seconds

  Background: 
    * def result = karate.callSingle('prep_query.feature')
    * def TestUtil = Java.type('com.tus.cipher.TestUtil')
    * url baseUrl

  Scenario: Query 1 reponse time check "query/imsi-failures/{imsi}"
    Given path "query/imsi-failures/344930000000011"
    And header Content-Type = 'application/json'
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000

  Scenario: Query 2  "query/imsi-failure-count-time"
    * def query = {imsi: 344930000000011, startDate: "#(TestUtil.getDate(2015, 1, 27))", endDate: "#(TestUtil.getDate(2024, 2, 24))"}
    Given path "query/imsi-failure-count-time"
    And header Content-Type = 'application/json'
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000

  Scenario: Query 3  "query/imsi-failures-time"
    * def query = {startDate: "#(TestUtil.getDate(2015, 1, 27))", endDate: "#(TestUtil.getDate(2024, 2, 24))"}
    Given path "query/imsi-failures-time"
    And header Content-Type = 'application/json'
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000

  Scenario: Query 4 "query/model-failure-count"
    * def query = {tac: 33000253, startDate: "#(TestUtil.getDate(2015, 1, 27))", endDate: "#(TestUtil.getDate(2024, 2, 24))"}
    Given path "query/model-failure-count"
    And header Content-Type = 'application/json'
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000

  Scenario: Query 4.5 "/imsi-failures-class/{failureClass}"
    Given path "query/imsi-failures-class/1"
    And header Content-Type = 'application/json'
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000

  Scenario: Query 5 "query/model-failures/{tac}"
    * def query = {tac: 33000253}
    Given path "query/model-failures/33000253"
    And header Content-Type = 'application/json'
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000

  Scenario: Query 6 "query/imsi-failures-count-duration"
    * def query = {startDate: "#(TestUtil.getDate(2015, 1, 27))", endDate: "#(TestUtil.getDate(2024, 2, 24))"}
    Given path "query/imsi-failures-count-duration"
    And header Content-Type = 'application/json'
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000

  Scenario: Query 7 "query/imsi-unique-failures/"
    Given path "query/imsi-unique-failures/344930000000011"
    And header Content-Type = 'application/json'
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
