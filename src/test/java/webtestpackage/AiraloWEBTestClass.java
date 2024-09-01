package webtestpackage;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Home;
import pages.Japan;

public class AiraloWEBTestClass {
	private SHAFT.GUI.WebDriver driver;
	private SHAFT.TestData.JSON testData;
	private Home home;
	private Japan japanPg;

	@BeforeClass(description = "Setup Test Data and Browser instance.")
	public void beforeClass() {
		// Initialize Test Data
		testData = new SHAFT.TestData.JSON("testData.json");

		// Initialize WebDriver
		driver = new SHAFT.GUI.WebDriver();

		// Initialize Page Objects
		home = new Home(driver);
		japanPg = new Japan(driver);

		// Navigate to the Home Page
		home.navigate();
	}

	@BeforeMethod(description = "Setup before each test method.")
	public void beforeMethod() {
		// Any pre-test setup that should run before each test method can go here
	}

	@Epic("Airalo Web Tests")
	@Story("Home Page Validations")
	@TmsLink("TC-001")
	@Description("Verify that the browser title on the Home page is correct.")
	@Test(priority = 1)
	public void checkHomePageTitleIsCorrect() {
		// Validate Home Page Title
		home.verifyBrowserTitleIsCorrect();
	}

	@Epic("Airalo Web Tests")
	@Story("Home Page Search Functionality")
	@TmsLink("TC-002")
	@Description("Verify that searching for 'Japan' on the Home page loads the correct new page.")
	@Test(priority = 2)
	public void verifyThatNewPageHasBeenLoaded() {
		// Perform search and select option
		home.search().selectOption();
		home.verifyNewPageTitleIsCorrect();
	}

	@Epic("Airalo Web Tests")
	@Story("Japan eSIM Package Validations")
	@TmsLink("TC-003")
	@Description("Verify that the Japan eSIM package details (title, coverage, data, validity, and price) are correctly displayed.")
	@Test(priority = 3)
	public void verifyPackageDetails() {
		japanPg.selectFirstPackage().verifyPageTitleIsCorrect().verifyPackageTitle().verifyPackageAllDetails();
	}

	@AfterMethod(description = "Cleanup after each test method.")
	public void afterMethod() {
		// Any cleanup that should run after each test method can go here
	}

	@AfterClass(description = "Teardown Browser instance.")
	public void afterClass() {
		// Quit the WebDriver instance to close the browser
		if (driver != null) {
			driver.quit();
		}
	}
}
