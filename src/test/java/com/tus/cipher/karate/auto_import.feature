Feature: Automatic Import
  Testing the automatic import functionality

  Background: 
    * configure retry = {count: 15, interval: 2500}
    * url baseUrl

  Scenario: Auto import not triggered
    Given path 'sysadmin/auto-import-status'
    And header Content-Type = 'application/json'
    When method GET
    Then response.statusCode == 204
    And match response.data contains 'No automatic import triggered'
    * print '[x]  auto_import: NO_AUTO'

  Scenario: Auto import Failed
    * FileUtil.moveFileToDest('wrong.xls', 'call-failure-data' )
    * print '[*] Moved wrong.xls to  call-failure-data/'
    Given path 'sysadmin/auto-import-status'
    And header Content-Type = 'application/json'
    And retry until response.statusCode == 400
    When method GET
    Then match response.data contains 'Automatic import Failed'
    * print '[x] auto_import: AUTO_FAIL'

  Scenario: Auto import success
    * FileUtil.moveFileToDest('TUS_CallFailureData.xls', 'call-failure-data' )
    * print '[*] Moved TUS_CallFailureData.xls to call-failure-data/'
    Given path 'sysadmin/auto-import-status'
    And header Content-Type = 'application/json'
    And retry until response.statusCode == 200
    When method GET
    Then match response.data contains 'Automatic import triggered'
    * print '[x]  auto_import: AUTO_SUCCESS'
