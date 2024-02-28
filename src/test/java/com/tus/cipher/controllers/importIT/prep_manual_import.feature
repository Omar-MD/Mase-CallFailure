Feature: Prepare Manual Import
  Prepare the environment for manual import functionality

  Background: 
    * def TestUtil = Java.type('com.tus.cipher.TestUtil')
    * def result = TestUtil.moveFilesToDest('.', 'call-failure-data', 'TUS_CallFailureData')
    * assert result != null && result != ''

  Scenario: Ensure Auto Import is Prepared Successfully
    * match result == 'success'