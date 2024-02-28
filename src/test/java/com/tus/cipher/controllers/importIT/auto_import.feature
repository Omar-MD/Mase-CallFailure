Feature: Automatic Import
  Testing the automatic import functionality

  Background: 
    * configure retry = { count: 10, interval: 5000 }
    * call read('prep_auto_import.feature')
    * url baseUrl

 Scenario: Auto import not triggered
    Given path 'sysadmin/auto-import-status'
    And header Content-Type = 'application/json'
    When method GET
    Then response.statusCode == 204
    And match response.data contains 'No automatic import triggered!'
    
    
  Scenario: Auto import Failed
    * def TestUtil = Java.type('com.tus.cipher.TestUtil')
    * def result = TestUtil.moveFileToDest('wrong.xls', 'call-failure-data' )
    * match result == 'success'
    Given path 'sysadmin/auto-import-status'
    And header Content-Type = 'application/json'
    And retry until response.statusCode == 400
    When method GET
    And match response.data contains 'Automatic import Failed!'
    
    
  Scenario: Auto import success
    * def TestUtil = Java.type('com.tus.cipher.TestUtil')
    * def result = TestUtil.moveFileToDest('TUS_CallFailureData.xls', 'call-failure-data' )
    * match result == 'success'
    Given path 'sysadmin/auto-import-status'
    And header Content-Type = 'application/json'
    And retry until response.statusCode == 200
    When method GET
    * print response
    And match response.data contains 'Automatic import triggered Successfully!'