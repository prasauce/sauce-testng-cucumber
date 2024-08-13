Feature: Verify item price in the catalog

  Scenario: Launch app, view catalog, click on item, and verify price
    Given I open the Android application
    Then I should see the catalog page
    Then I click on Backpack
    Then I verify the item price is "$ 29.99"
