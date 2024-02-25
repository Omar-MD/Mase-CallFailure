Feature: Prepare Auto Import
  Prepare the environment for auto import functionality

  Background: 
    * def FileUtil = Java.type('com.tus.cipher.controllers.FileUtil')
    * def result = FileUtil.moveFilesToDest('call-failure-data', '.', '.')
    * assert result != null && result != ''

  Scenario: Ensure Auto Import is Prepared Successfully
    * match result == 'success'
