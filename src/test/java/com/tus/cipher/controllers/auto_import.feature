Feature: Automatic Import
  Testing the automatic import functionality

  Background: 
    * callonce read('prep_auto_import.feature')
    * def FileUtil = Java.type('com.tus.cipher.controllers.FileUtil')
    * def sleep = function(pause){ java.lang.Thread.sleep(pause*1000) }
    * configure retry = { count: 10, interval: 5000 }
    * url baseUrl

  Scenario: Successful automatic import
    * def result = FileUtil.moveFileToDest('TUS_CallFailureData.xls', 'call-failure-data' )
    * match result == 'success'
    * karate.sleep(10000)
	     Given path 'sysadmin/auto-import-status'
	     And retry until responseStatus == 200 && response.id > 3
	     When method GET
	     Then status 200
	     And match $ contains 'Automatic import triggered Successfully!'

 # Scenario: Failed automatic import
   # * def result = FileUtil.moveFileToDest('wrong.xls', 'call-failure-data' )
    #* match result == 'success'
    #* retry until Given path baseUrl + '/sysadmin/auto-import-status' And method get  Then status 200 And response contains 'Automatic import triggered Successfully!'
