package apitestpackage;

import apitestpages.TokenManager;
import apitestpages.OrderPage;
import apitestpages.SimsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.shaft.driver.SHAFT;

public class AiraloAPITest {
    private SHAFT.TestData.JSON testData;
    private TokenManager tokenManager;
    private OrderPage orderPage;
    private SimsPage simsPage;
    private String iccid;   // To store the ICCID from the response of order creation
    private String orderId; // To store the order ID from the response of order creation

    @BeforeClass
    public void setup() {
        // Initialize Test Data
        testData = new SHAFT.TestData.JSON("testData.json");

        // Initialize Page Objects
        tokenManager = new TokenManager();
        orderPage = new OrderPage();
        simsPage = new SimsPage();
    }

    @Epic("Airalo API Tests")
    @Story("Order eSIM Packages")
    @TmsLink("API-TC-001")
    @Description("Verify that an order for 6 'merhaba-7days-1gb' eSIMs can be successfully created.")
    @Test(priority = 1)
    public void testCreateOrder() {
        String accessToken = tokenManager.getAccessToken();

        Response response = orderPage.createOrder(
            accessToken,
            testData.getTestData("quantity"),
            testData.getTestData("packageId"),
            testData.getTestData("type"),
            testData.getTestData("description"),
            testData.getTestData("brandSettingsName")
        );

        // Log the response for debugging
        if (response.getStatusCode() != 200) {
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asString());
        }

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Expected status code 200 for order creation");

        // Capture the ICCID from the first SIM object in the response
        iccid = response.jsonPath().getString("data.sims[0].iccid");
        Assert.assertNotNull(iccid, "ICCID should not be null");
        System.out.println("Captured ICCID: " + iccid);

        // Capture the Order ID from the response
        orderId = response.jsonPath().getString("data.id");
        Assert.assertNotNull(orderId, "Order ID should not be null");
        System.out.println("Captured Order ID: " + orderId);

        String packageId = response.jsonPath().getString("data.package_id");
        Assert.assertEquals(packageId, testData.getTestData("packageId"), "Expected package ID does not match");

        String responseMessage = response.jsonPath().getString("meta.message");
        Assert.assertEquals(responseMessage, testData.getTestData("expectedMessage"), "Expected message does not match");
    }

    @Epic("Airalo API Tests")
    @Story("Get eSIM List")
    @TmsLink("API-TC-002")
    @Description("Verify that the list of eSIMs includes the 6 eSIMs ordered with package ID 'merhaba-7days-1gb'.")
    @Test(priority = 2, dependsOnMethods = {"testCreateOrder"})
    public void testGetEsimList() {
        String accessToken = tokenManager.getAccessToken();

        // Use the captured ICCID and Order ID in the request
        Response response = simsPage.getEsimList(accessToken, iccid, "order,order.status,order.user,share", "6", orderId);

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Expected status code 200 for fetching eSIM list");

        // Step 1: Assert that the list contains 6 eSIMs
        int esimCount = response.jsonPath().getList("data").size();
        Assert.assertEquals(esimCount, 6, "Expected 6 eSIMs in the list");

        // Step 2: Assert that all eSIMs have the correct order ID
        response.jsonPath().getList("data.order_id").forEach(id ->
            Assert.assertEquals(id, orderId, "Expected order ID does not match")
        );

        // Step 3: Assert that all eSIMs have the expected package ID from testData
        String expectedPackageSlug = testData.getTestData("expectedPackageSlug");
        response.jsonPath().getList("data.package_id").forEach(packageId ->
            Assert.assertEquals(packageId, expectedPackageSlug, "Expected package slug does not match")
        );
    }
}
