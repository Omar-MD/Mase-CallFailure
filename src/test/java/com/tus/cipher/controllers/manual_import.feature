Feature: Manual Import  
  Testing the manual import functionality

  Background:
    * callonce read('prep_manual_import.feature')
    * url baseUrl

  Scenario: Successful manual import
    Given path '/sysadmin/import'
	  And request { filename: 'TUS_CallFailureData.xls' }
	  And header Content-Type = 'application/json'
	  When method post
	  Then response.statusCode == 200
	  And match response.status == 'Success'
	  And match response.data == '#string'

	Scenario: Failed manual import
	  Given path '/sysadmin/import'
	  And request { filename: 'invalid_file.xls' }
	  And header Content-Type = 'application/json'
	  When method post
	  Then response.statusCode == 400
	  And match response.status == 'Error'
	  And match response.error.errorMsg == 'Import failed'
	  And match response.error.details == 'Invalid file selection!'