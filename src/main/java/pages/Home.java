package pages;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class Home {
	private SHAFT.GUI.WebDriver driver;
	private SHAFT.TestData.JSON testData;
	private String url;
	private String title;
	private String japan;
	private By searchBox = By.cssSelector("input[placeholder='Search data packs for 200+ countries and regions']");
	private By clickJapan = By.cssSelector(".countries-list > li:nth-child(2)");

	public Home(SHAFT.GUI.WebDriver driver) {
		this.driver = driver;
		driver.getDriver().manage().deleteAllCookies();

		// Initialize test data
		this.testData = new SHAFT.TestData.JSON("testData.json");

		// Load URL from system properties and other test data from JSON
		this.url = System.getProperty("baseURL"); // Load base URL from system property
		this.title = testData.getTestData("homePageTitle"); // Load title from JSON
		this.japan = testData.getTestData("searchQuery"); // Load search query from JSON
	}

	@Step("When I navigate to the Home page.")
	public Home navigate() {
		driver.browser().navigateToURL("https://www.airalo.com/");
		return this;
	}

	@Step("Then the browser title should contain 'Airalo'.")
	public Home verifyBrowserTitleIsCorrect() {
		driver.verifyThat().browser().title().contains(title).perform();
		return this;
	}

	@Step("Then the browser title should contain the search query.")
	public Home verifyNewPageTitleIsCorrect() {
		driver.verifyThat().browser().title().contains(japan).perform();
		return this;
	}

	@Step("And I search for Japan.")
	public Home search() {
		driver.element().click(searchBox);
		driver.element().type(searchBox, japan);
		return this;
	}

	@Step("Then I select Japan")
	public Japan selectOption() {
		driver.element().click(clickJapan);
		return new Japan(driver);
	}
}
