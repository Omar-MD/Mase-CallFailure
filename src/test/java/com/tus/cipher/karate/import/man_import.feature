Feature: Manual Import
  Testing the manual import functionality

  Background: 
    * url baseUrl
    * def adminResponse = karate.callSingle('classpath:com/tus/cipher/karate/auth.feature?admin', {'username': 'admin', 'password': 'password' })
    * header Authorization = 'Bearer ' + adminResponse.authToken

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