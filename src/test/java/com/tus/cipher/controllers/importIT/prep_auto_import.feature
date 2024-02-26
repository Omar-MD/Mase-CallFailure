Feature: Prepare Auto Import
  Prepare the environment for auto import functionality

  Background: 
    * def TestUtil = Java.type('com.tus.cipher.TestUtil')
    * def result = TestUtil.moveFilesToDest('call-failure-data', '.', '.')
    * assert result != null && result != ''

  Scenario: Ensure Auto Import is Prepared Successfully
    * match result == 'success'
