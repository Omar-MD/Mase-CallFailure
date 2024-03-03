Feature: Orchestrate the running Integration Tests

  Scenario: Run Auto Import Tests
    * call read('auto_import.feature')
    * print '[x]  auto_import.feature'

  Scenario: Run Manual Import Tests
    * call read('man_import.feature')
    * print '[x]  man_import.feature'

  Scenario: Run Query Tests
    * call read('query_times.feature')
    * print '[x]  query_times.feature'
