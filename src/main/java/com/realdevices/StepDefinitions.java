package com.realdevices;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class StepDefinitions {
    private AppiumDriver driver;

    @Given("I open the Android application")
    public void iOpenTheAndroidApplication() {
        // The app is already opened in the setUp method.
    }

    @Then("I should see the catalog page")
    public void iShouldSeeCatalogPage() {
        By productsScreenLocator = By.id("com.saucelabs.mydemoapp.android:id/productTV");
        waitForElementToBeVisible(productsScreenLocator);
        assertTrue(isDisplayed(productsScreenLocator), "Home page is not displayed.");
    }

    @Then("I click on Backpack")
    public void iShouldClickBackpack() {
        By backpackLocator = By.id("com.saucelabs.mydemoapp.android:id/productIV");
        waitForElementToBeVisible(backpackLocator);
        TestRunner.getDriver().findElement(backpackLocator).click();
    }

    @Then("I verify the item price is {string}")
    public void i_verify_the_item_price_is(String expectedPrice) {
        By priceLocator = By.id("com.saucelabs.mydemoapp.android:id/priceTV");
        waitForElementToBeVisible(priceLocator);
        assertEquals(TestRunner.getDriver().findElement(priceLocator).getText(), expectedPrice);
    }

    private boolean waitForElementToBeVisible(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(TestRunner.getDriver(), 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            throw new AssertionError("Element not found within the specified time: " + e);
        }
    }
    private boolean isDisplayed(By locator) {
        try {
            return TestRunner.getDriver().findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
