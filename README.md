# API Test Task

This project is a Java-based API testing framework. It provides a structured approach to API testing with comprehensive test coverage for CRUD operations, including both happy paths and edge cases.

## Technologies Used

- **Java 21**: Programming language
- **Maven**: Build and dependency management
- **TestNG**: Test execution framework
- **REST Assured**: API testing library
- **Allure**: Test reporting
- **AssertJ**: Fluent assertions library
- **Jackson**: JSON processing
- **Log4j2**: Logging
- **Lombok**: Reduces boilerplate code
- **DataFaker**: Test data generation

## Project Structure

```
src/
├── main/
│   ├── java/api/
│   │   ├── apimanagers/       # API manager classes for different endpoints
│   │   ├── client/            # API client classes
│   │   ├── configuration/     # Configuration management
│   │   ├── modeldataproviders/ # Data providers for models
│   │   ├── models/            # POJO models for API entities
│   │   └── utils/             # Utility classes
│   └── resources/             # Main resources
└── test/
    └── java/
        ├── api/               # API test classes and base test class
        │   └── dataproviders/ # Test data providers
        └── resources/         # Test resources and configuration
            └── suites/        # TestNG suite files
```

## Features

- **Modular Architecture**: Clean separation of concerns with dedicated packages
- **Fluent API Client**: Easy-to-use API client with fluent interface
- **Data-Driven Testing**: Utilizes TestNG data providers for test data
- **Comprehensive Reporting**: Allure reports with detailed test steps
- **Robust Assertions**: Fluent assertions with AssertJ
- **Test Suites**: Organized test suites for different API endpoints
- **Edge Case Testing**: Comprehensive validation of error scenarios
- **CI/CD Ready**: GitHub Actions workflows for continuous testing

## API Endpoints Tested

This project tests the [Fake REST API](https://fakerestapi.azurewebsites.net/index.html), which provides a sandbox environment for testing REST API clients.

### Books API
- GET /api/v1/Books - Get all books
- GET /api/v1/Books/{id} - Get book by ID
- POST /api/v1/Books - Create a new book
- PUT /api/v1/Books/{id} - Update an existing book
- DELETE /api/v1/Books/{id} - Delete a book

### Authors API
- GET /api/v1/Authors - Get all authors
- GET /api/v1/Authors/{id} - Get author by ID
- POST /api/v1/Authors - Create a new author
- PUT /api/v1/Authors/{id} - Update an existing author
- DELETE /api/v1/Authors/{id} - Delete an author

## Test Case Implementation

The test framework implements a comprehensive testing strategy:

### Positive Path Testing
- Verification of successful CRUD operations
- Validation of response data against expected values
- Data-driven tests with valid input combinations

### Edge Case Testing
- Invalid input validation (e.g., non-existent IDs, malformed data)
- Boundary value testing (e.g., minimum/maximum values)
- Error response validation for incorrect requests

## Reporting Capabilities

The framework uses Allure reporting to provide detailed insights into test execution:

- **Test Execution Summary**: Overview of passed/failed tests
- **Detailed Test Steps**: Each API call is logged with request/response details
- **Attachments**: Response times and payloads are attached to reports
- **Categorization**: Tests are categorized by endpoint and test type
- **Historical Data**: Trends of test executions over time

## Setup Instructions

1. Ensure you have Java 21 installed
2. Clone the repository
3. Configure the API endpoint in `src/test/java/resources/configuration.properties`

## Running Tests

### Using Maven

Run all tests:
```bash
mvn clean test
```

Run specific test suite:
```bash
mvn clean test -Dsuite=book_tests
```

### Generating Allure Reports

Generate and open Allure report:
```bash
mvn allure:serve
```

Generate Allure report without opening:
```bash
mvn allure:report
```

## CI/CD Integration

This project includes comprehensive CI/CD integration through GitHub Actions workflows for continuous testing and reporting:

### GitHub Actions Workflows

#### Run All Tests Workflow

This workflow runs all tests and publishes the Allure report to GitHub Pages. It is triggered on:
- Push to master branch
- Pull requests to master branch
- Manual trigger from the Actions tab

To view the workflow, go to the Actions tab in the GitHub repository and select "Run All Tests and Publish Allure Report".

#### Run Specific Test Suite Workflow

This workflow allows you to select and run a specific test suite and publishes the Allure report to GitHub Pages. It is triggered only manually.

To run a specific test suite:
1. Go to the Actions tab in the GitHub repository
2. Select "Run Specific Test Suite and Publish Allure Report"
3. Click "Run workflow"
4. Select the desired test suite from the dropdown menu (book_tests, author_tests, or all_api_tests)
5. Click "Run workflow"

### CI/CD Pipeline Features

The CI/CD pipeline provides the following capabilities:

- **Automated Testing**: Tests run automatically on code changes
- **Environment Setup**: Automatic Java and Maven configuration
- **Parallel Execution**: Tests run in parallel for faster feedback
- **Reporting**: Automatic generation of Allure reports
- **Report Publishing**: Reports are published to GitHub Pages
- **Historical Data**: Test results are preserved across runs
- **Selective Testing**: Ability to run specific test suites
- **Manual Triggering**: Option to manually trigger test runs
