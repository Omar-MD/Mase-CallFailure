Feature: Import Base Data

  Background: 
    * url baseUrl
    * header Authorization = 'Bearer ' + adminToken

  Scenario: Successful manual import
    Given path '/sysadmin/import'
    And request { filename: 'TUS_CallFailureData.xls' }
    When method post
    Then response.statusCode == 200
    And match response.status == 'Success'
    And match response.data == '#string'
    And assert responseTime < maxImportTime
    * print '[x] imported base data: TUS_CallFailureData.xls'