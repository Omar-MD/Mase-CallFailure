Feature: Automatic Import
  Testing the automatic import functionality

  Background: 
    * configure retry = {count: 15, interval: 1150}
    * url baseUrl

  Scenario: Auto import not triggered
    Given path 'sysadmin/auto-import-status' 
    And header Content-Type = 'application/json'
    When method GET
    Then response.statusCode == 204 
    And match response.data contains 'No automatic import triggered'

  Scenario: Auto import Failed
    * FileUtil.moveFileToDest('wrong.xls', 'call-failure-data' )
    * print 'moved wrong.xls to call-failure-data/'
    Given path 'sysadmin/auto-import-status' 
    And header Content-Type = 'application/json' 
    And retry until response.statusCode == 400
    When method GET
    Then match response.data contains 'Automatic import Failed'

  Scenario: Auto import success
    * FileUtil.moveFileToDest('TUS_CallFailureData.xls', 'call-failure-data' )
    * print 'moved TUS_CallFailureData.xls to call-failure-data/'
    Given path 'sysadmin/auto-import-status' 
    And header Content-Type = 'application/json' 
    And retry until response.statusCode == 200
    When method GET
    Then match response.data contains 'Automatic import triggered'
