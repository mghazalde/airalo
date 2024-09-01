package apitestpages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OrderPage {
    String apiUrl = System.getProperty("apiBaseURL");
    String orderEndpoint = System.getProperty("orderEndpoint");

    public Response createOrder(String accessToken, String quantity, String packageId, String type, String description, String brandSettingsName) {
        if (apiUrl == null || orderEndpoint == null) {
            throw new IllegalArgumentException("API URL or Order Endpoint is missing.");
        }

        RestAssured.baseURI = apiUrl;
        
        // Sending request with form parameters
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .multiPart("quantity", quantity) // using multiPart for form data
                .multiPart("package_id", packageId)
                .multiPart("type", type)
                .multiPart("description", description)
                .multiPart("brand_settings_name", brandSettingsName)
                .post(orderEndpoint);
        
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        return response;
    }
}
