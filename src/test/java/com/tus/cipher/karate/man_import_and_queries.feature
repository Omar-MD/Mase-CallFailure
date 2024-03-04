Feature: Manual Import
  Testing the manual import functionality

  Background: 
    * url baseUrl

  Scenario: Failed manual import
    Given path '/sysadmin/import'
    And request { filename: 'invalid_file.xls' }
    And header Content-Type = 'application/json'
    When method post
    Then response.statusCode == 400
    And match response.status == 'Error'
    And match response.error.errorMsg == 'Import failed'
    And match response.error.details == 'Invalid file selection!'
    * print '[x]  man_import: Invalid File'

  Scenario: Successful manual import
    Given path '/sysadmin/import'
    And request { filename: 'TUS_CallFailureData3A.xls' }
    And header Content-Type = 'application/json'
    When method post
    Then response.statusCode == 200
    And match response.status == 'Success'
    And match response.data == '#string'
    And assert responseTime < 120000
    * print '[x]  man_import: Import 30k rows < 2 minutes'

  Scenario: Query 1 reponse time check "query/imsi-failures/{imsi}"
    Given path "query/imsi-failures/344930000000011"
    And header Content-Type = 'application/json'
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
    * print '[x]  query_times: query/imsi-failures/{imsi}'

  Scenario: Query 2  "query/imsi-failure-count-time"
    * def query = {imsi: 344930000000011, startDate: "#(FileUtil.getDate(2015, 1, 27))", endDate: "#(FileUtil.getDate(2024, 2, 24))"}
    Given path "query/imsi-failure-count-time"
    And header Content-Type = 'application/json'
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
    * print '[x]  query_times: query/imsi-failure-count-time'

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

  Scenario: Query 4 "query/model-failure-count"
    * def query = {tac: 33000253, startDate: "#(FileUtil.getDate(2015, 1, 27))", endDate: "#(FileUtil.getDate(2024, 2, 24))"}
    Given path "query/model-failure-count"
    And header Content-Type = 'application/json'
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
    * print '[x]  query_times: query/model-failure-count'

  Scenario: Query 4.5 "/imsi-failures-class/{failureClass}"
    Given path "query/imsi-failures-class/1"
    And header Content-Type = 'application/json'
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
    * print '[x]  query_times: query/imsi-failures-class/{failureClass}'

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

  Scenario: Query 6 "query/imsi-failures-count-duration"
    * def query = {startDate: "#(FileUtil.getDate(2015, 1, 27))", endDate: "#(FileUtil.getDate(2024, 2, 24))"}
    Given path "query/imsi-failures-count-duration"
    And header Content-Type = 'application/json'
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
   * print '[x]  query_times: query/imsi-failures-count-duration'

  Scenario: Query 7  "query/top10-market-operator-cellid-combinations"
    * def query = {startDate: "#(TestUtil.getDate(2019, 1, 1))", endDate: "#(TestUtil.getDate(2024, 3, 8))"}
    Given path "query/top10-market-operator-cellid-combinations"
    And header Content-Type = 'application/json'
    And params query
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
    * print '[x]  query_times: query/top10-market-operator-cellid-combinations'

  Scenario: Query 8 "query/imsi-unique-failures/"
    Given path "query/imsi-unique-failures/344930000000011"
    And header Content-Type = 'application/json'
    When method GET
    Then response.statusCode == 200
    And match response.status == 'Success'
    And assert responseTime < 2000
    * print '[x]  query_times: query/imsi-unique-failures'

  Scenario: Query 9  "query/top10-imsi-failures-time"
  * def query = {startDate: "#(TestUtil.getDate(2019, 4, 4))", endDate: "#(TestUtil.getDate(2024, 1, 2))"}
  Given path "query/top10-imsi-failures-time"
  And header Content-Type = 'application/json'
  And params query
  When method GET
  Then response.statusCode == 200
  And match response.status == 'Success'
  And assert responseTime < 2000
  * print '[x]  query_times: query/top10-imsi-failures-time'

