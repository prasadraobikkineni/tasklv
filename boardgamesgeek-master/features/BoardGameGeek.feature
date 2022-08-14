Feature: BoardGameGeek Language Dependence test

Scenario: Verify Language Dependence displayed for the top game in Hotness matches the top voted option by the API

Given I am at the BoardGamesGeek home page
When I accept the Privacy Policy
And I click the first game in the Hotness section
Then Language Dependence displayed on the page will be the same as the one retrieved from the REST API