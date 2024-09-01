package apitestpages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SimsPage {
    private String apiUrl = System.getProperty("apiBaseURL");
    private String simsEndpoint = System.getProperty("simsEndpoint");

    public Response getEsimList(String accessToken, String iccid, String includeParams, String limit, String orderId) {
        if (apiUrl == null || simsEndpoint == null) {
            throw new IllegalArgumentException("API URL or Sims Endpoint is missing.");
        }

        RestAssured.baseURI = apiUrl;

        // Log the request details
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .multiPart("filter[iccid]", iccid)  // Include the iccid in the request
                .multiPart("filter[order_id]", orderId) // Include the order ID in the request
                .multiPart("include", includeParams)
                .multiPart("limit", limit)
                .log().all()  // Logs the request details (headers, params, body, etc.)
                .get(simsEndpoint);

        // Log the response details
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("ICCID being used in the request of test case 2: " + iccid);
        System.out.println("Order ID being used in the request of test case 2: " + orderId);

        return response;
    }
}
