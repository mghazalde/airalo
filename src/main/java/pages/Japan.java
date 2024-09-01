package pages;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class Japan {
	private SHAFT.GUI.WebDriver driver;
	private SHAFT.TestData.JSON testData;

	private By buyNow = By.xpath("(//button[@type='button'][normalize-space()='BUY NOW'])[1]");
	private By packageTitle = By.cssSelector("div[class='sim-detail-operator'] p");
	private By simDetails = By.xpath("//div[@class='sim-detail-header']");

	public Japan(SHAFT.GUI.WebDriver driver) {
		this.driver = driver;
		driver.getDriver().manage().deleteAllCookies();

		// Initialize test data
		this.testData = new SHAFT.TestData.JSON("testData.json");
	}

	@Step("Select the first eSIM Package.")
	public Japan selectFirstPackage() {
		driver.element().click(buyNow);
		return this;
	}

	@Step("Verify the page title is correct for the Japan eSIM package.")
	public Japan verifyPageTitleIsCorrect() {
		String expectedTitle = testData.getTestData("japanPageTitle");
		driver.verifyThat().browser().title().contains(expectedTitle).perform();
		return this;
	}

	@Step("Verify the eSIM Package title is 'Moshi Moshi'.")
	public Japan verifyPackageTitle() {
		String expectedPackageTitle = testData.getTestData("packageTitle");
		driver.assertThat().element(packageTitle).text().contains(expectedPackageTitle).perform();
		return this;
	}

	@Step("Verify all details of the eSIM Package.")
	public Japan verifyPackageAllDetails() {
		String expectedItems = testData.getTestData("expectedItem");

		driver.assertThat().element(simDetails).text().contains(expectedItems)
				.withCustomReportMessage("Check that all expected items are present in the container text.").perform();

		return this;
	}
}
