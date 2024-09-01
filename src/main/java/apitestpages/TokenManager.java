package apitestpages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TokenManager {
    private static String accessToken;
    private static long tokenExpiryTime;
    String apiUrl = System.getProperty("apiBaseURL");
    String tokenEndpoint = System.getProperty("tokenEndpoint");
    String clientId = System.getProperty("clientId");
    String clientSecret = System.getProperty("clientSecret");
    String grantType = System.getProperty("grantType", "client_credentials"); // Default to client_credentials if not set

    public String getAccessToken() {
        if (accessToken == null || System.currentTimeMillis() > tokenExpiryTime) {
            generateNewToken();
        }
        return accessToken;
    }

    private void generateNewToken() {
        if (apiUrl == null || tokenEndpoint == null || clientId == null || clientSecret == null) {
            throw new IllegalArgumentException("API URL, Token Endpoint, Client ID, or Client Secret is missing.");
        }

        RestAssured.baseURI = apiUrl;
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .formParam("grant_type", grantType)
                .post(tokenEndpoint);

        if (response.getStatusCode() == 200) {
            accessToken = response.jsonPath().getString("data.access_token");
            int expiresIn = response.jsonPath().getInt("data.expires_in");
            tokenExpiryTime = System.currentTimeMillis() + (expiresIn * 1000);
        } else {
            throw new RuntimeException("Failed to fetch access token: " + response.getStatusLine());
        }
    }
}
