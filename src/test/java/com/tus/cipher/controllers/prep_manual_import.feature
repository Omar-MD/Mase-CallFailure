Feature: Prepare Manual Import
  Prepare the environment for manual import functionality

  Background: 
    * def FileUtil = Java.type('com.tus.cipher.controllers.FileUtil')
    * def result = FileUtil.moveFilesToDest('.', 'call-failure-data', 'TUS_CallFailureData')
    * assert result != null && result != ''

  Scenario: Ensure Auto Import is Prepared Successfully
    * match result == 'success'