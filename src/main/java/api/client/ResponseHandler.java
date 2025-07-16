package api.client;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Getter
public class ResponseHandler<T> {
    private final Response response;
    private final Class<T> responseClass;

    public ResponseHandler(Response response, Class<T> responseClass) {
        this.response = response;
        this.responseClass = responseClass;
        Allure.addAttachment("Response time: " + response.getTime(), String.valueOf(response.getTime()));
    }

    public T readEntity() {
        return isResponseContentEmpty() ? null : response.getBody().as(responseClass);
    }

    public List<T> readEntities() {
        return isResponseContentEmpty() ? null : response.getBody().jsonPath().getList("", responseClass);
    }

    public ResponseHandler<T> expectingStatusCode(int statusCode) {
        assertThat("Response code differs ", response.getStatusCode(), is(statusCode));
        return this;
    }

    private boolean isResponseContentEmpty() {
        var contentLength = response.header("Content-Length");
        if (contentLength != null) {
            return contentLength.equals("0");
        }
        return false;
    }
}