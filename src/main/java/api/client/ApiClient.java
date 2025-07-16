package api.client;

import api.configuration.ConfigProvider;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class ApiClient {
    private final ConfigProvider configProvider = ConfigProvider.getInstance();

    private RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(configProvider.getBaseUrl())
                .setBasePath(configProvider.getApiPath())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setConfig(io.restassured.config.RestAssuredConfig.config()
                        .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", configProvider.getRequestTimeout())
                                .setParam("http.socket.timeout", configProvider.getRequestTimeout())))
                .log(LogDetail.ALL)
                .addFilter(new AllureRestAssured())
                .build();
    }

    @Step("Get to {path} without query parameters")
    protected <F> ResponseHandler<F> get(String path, Class<F> responseClass) {
        Response response = given().spec(getRequestSpecification()).get(path);
        return new ResponseHandler<>(response, responseClass);
    }

    @Step("Post to {path} with payload")
    protected <T, F> ResponseHandler<F> post(String path, T payload, Class<F> responseClass) {
        Response response = given().spec(getRequestSpecification()).log().all().body(payload).post(path);
        return new ResponseHandler<>(response, responseClass);
    }

    @Step("Put into {path} with payload")
    protected <T, F> ResponseHandler<F> put(String path, T payload, Class<F> responseClass) {
        Response response = given().spec(getRequestSpecification()).body(payload).put(path);
        return new ResponseHandler<>(response, responseClass);
    }

    @Step("Delete by {path} without query parameters")
    protected <F> ResponseHandler<F> delete(String path) {
        Response response = given().spec(getRequestSpecification()).delete(path);
        return new ResponseHandler<>(response, null);
    }

    protected String getPathVariable(Object id) {
        return "/" + id.toString();
    }
}
