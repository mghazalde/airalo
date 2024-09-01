# Airalo Test Automation Project

## Overview

This project provides automated test cases for both the Airalo Web UI and API functionalities using the [SHAFT Engine](https://shafthq.github.io/docs/Getting_Started/first_steps) framework. The tests are written in Java and make use of the REST Assured library for API testing and Selenium WebDriver for UI testing.

## Project Structure

The project is organized into the following directories:

- **`src/main/java`**: Contains the main Java source files, including page object models for both the Web and API tests.
  - **`pages`**: Contains classes representing the web pages (e.g., `Home`, `Japan`).
  - **`apitestpages`**: Contains classes for API testing (e.g., `TokenManager`, `OrderPage`, `SimsPage`).

- **`src/test/java`**: Contains the test classes for both the Web and API tests.
  - **`webtestpackage`**: Contains the UI test class `AiraloWEBTestClass`.
  - **`apitestpackage`**: Contains the API test class `AiraloAPITest`.

- **`src/test/resources`**: Contains resources such as test data files and configuration files.
  - **`testDataFiles`**: Stores the JSON files (e.g., `testData.json`) that contain test data.
  - **`META-INF/services`**: Contains services for Allure reporting listeners.

- **`src/main/resources/properties`**: Contains configuration files for various settings.

## Approach to Implementing Each Test Case

### Web UI Test Cases

1. **Home Page Validations**:
   - Implemented using the Page Object Model (POM) pattern with the `Home` class.
   - The test navigates to the home page and verifies the page title using the `verifyBrowserTitleIsCorrect()` method.

2. **Search Functionality on Home Page**:
   - Conducted by typing the search query (e.g., "Japan") into the search box and selecting the appropriate option.
   - The test checks that the page title matches the search query using the `verifyNewPageTitleIsCorrect()` method.

3. **Japan eSIM Package Validations**:
   - Implemented in the `Japan` class to select an eSIM package and validate its details such as title, coverage, data, validity, and price.
   - Methods like `verifyPageTitleIsCorrect()`, `verifyPackageTitle()`, and `verifyPackageAllDetails()` ensure that all elements are correctly displayed.

### API Test Cases

1. **Token Management**:
   - Managed using the `TokenManager` class, which handles the retrieval and caching of the API access token.
   - This approach ensures tokens are reused until expiration, optimizing API performance.

2. **Order Creation**:
   - Implemented in the `OrderPage` class using the `createOrder()` method.
   - This method sends an HTTP POST request to create a new eSIM order, validates the response status code, and extracts essential data like the ICCID from the response for use in subsequent tests.

3. **Get eSIM List**:
   - Implemented in the `SimsPage` class using the `getEsimList()` method.
   - This test retrieves a list of eSIMs associated with a specific order ID and ICCID and validates the number and details of the eSIMs returned in the response.

## Configure the Project

Update the `custom.properties` file in `src/main/resources/properties` to set environment-specific variables such as `apiBaseURL`, `tokenEndpoint`, `clientId`, and `clientSecret`.

## Install Dependencies

Run the following command to install all necessary dependencies:

```sh
mvn clean install
## Running the Tests

### Running the Web UI Tests

To execute the UI tests, run the `AiraloWEBTestClass` located in `src/test/java/webtestpackage` using the following Maven command:

```sh
mvn clean test -Dtest=AiraloWEBTestClass
### Running the API Tests

To execute the API tests, run the `AiraloAPITest` located in `src/test/java/apitestpackage` using the following Maven command:

```sh
mvn clean test -Dtest=AiraloAPITest
## Test Data Management

The test data is managed using JSON files located in the `src/test/resources/testDataFiles/` directory. The primary test data file is `testData.json`, which contains data such as package details, quantities, and expected results.

## Configuration

### `custom.properties` File

The `custom.properties` file is located in the `src/main/resources/properties` directory. This file contains environment-specific configurations that the test suite requires, such as:

- **baseURL**: The base URL for the Airalo website.
- **apiBaseURL**: The base URL for the API endpoint.
- **tokenEndpoint**: The endpoint for fetching authentication tokens.
- **orderEndpoint**: The endpoint for creating orders.
- **simsEndpoint**: The endpoint for retrieving SIM information.
- **clientId** and **clientSecret**: Credentials for API authentication.
- **targetOperatingSystem**: The OS for browser execution.
- **targetBrowserName**: The browser name for Web UI testing.
- Additional configuration options for test execution, such as **headlessExecution**, **createAnimatedGif**, and **videoParams_recordVideo**.

## Error Handling

The framework is designed to handle errors gracefully. Any failures during token retrieval or API calls are logged automatically by the SHAFT Engine, and exceptions are thrown to indicate the exact issue for debugging purposes.

## Reporting

All test results are reported using the Allure framework. The Allure report will be automatically generated after each test run, providing a detailed report of the test execution, including logs, screenshots, and API request/response details.

## License

This project is licensed under the MIT License.

