Feature: Prepare Query
  Prepare the environment for Queries

  Background: 
    * def TestUtil = Java.type('com.tus.cipher.TestUtil')
    * def result = TestUtil.moveFilesToDest('.', 'call-failure-data', 'TUS_CallFailureData')
    * assert result != null && result != ''
    * url baseUrl

  Scenario: Import Data to be used by Query
    * match result == 'success'
    Given path '/sysadmin/import'
    And request { filename: 'TUS_CallFailureData.xls' }
    And header Content-Type = 'application/json'
    When method post
    Then response.statusCode == 200
    And match response.status == 'Success'
    And match response.data == '#string'
    
     Given path '/sysadmin/import'
    And request { filename: 'TUS_CallFailureData3A.xls' }
    And header Content-Type = 'application/json'
    When method post
    Then response.statusCode == 200
    And match response.status == 'Success'
    And match response.data == '#string'
    
     Given path '/sysadmin/import'
    And request { filename: 'TUS_CallFailureData3B.xls' }
    And header Content-Type = 'application/json'
    When method post
    Then response.statusCode == 200
    And match response.status == 'Success'
    And match response.data == '#string'
    
    * karate.set('result', 'success')
