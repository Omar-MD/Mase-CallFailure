@parallel=false
Feature: Automatic Import
  Testing the automatic import functionality

  Background: 
    * url baseUrl
    * header Authorization = 'Bearer ' + getAuthToken('admin', 'password', 'ADMIN')
    * configure retry = {count: 15, interval: 2000}

  Scenario: Auto import not triggered
    Given path 'sysadmin/auto-import-status'
    When method GET
    Then response.statusCode == 204
    And match response.data contains 'No automatic import triggered'
    * print '[x]  auto_import: NO_AUTO'

  Scenario: Auto import Failed
    * fileUtil.moveFileToDest('wrong.xls', 'call-failure-data' )
    * print '[*] Moved wrong.xls to  call-failure-data/'
    Given path 'sysadmin/auto-import-status'
    And retry until response.statusCode == 400
    When method GET
    Then match response.data contains 'Automatic import Failed'
    * print '[x] auto_import: AUTO_FAIL'

  Scenario: Auto import success
    * fileUtil.moveFileToDest('TUS_CallFailureData3B.xls', 'call-failure-data' )
    * print '[*] Moved TUS_CallFailureData3B.xls to call-failure-data/'
    Given path 'sysadmin/auto-import-status'
    And retry until response.statusCode == 200
    When method GET
    Then match response.data contains 'Automatic import triggered'
    * print '[x]  auto_import: AUTO_SUCCESS'
