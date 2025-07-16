package api.apimanagers;

import api.client.ApiClient;
import api.models.AuthorModel;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

public class AuthorsApiManager extends ApiClient {
    private static final String AUTHORS_ENDPOINT = "/Authors";

    @Step("Get all authors")
    public List<AuthorModel> getAllAuthors(int expectedStatusCode) {
        return get(AUTHORS_ENDPOINT, AuthorModel.class)
                .expectingStatusCode(expectedStatusCode)
                .readEntities();
    }

    @Step("Get author by '{0}' id response")
    public Response getAuthorByIdResponse(String id) {
        return get(AUTHORS_ENDPOINT + getPathVariable(id), AuthorModel.class).getResponse();
    }

    @Step("Get author by '{0}' id")
    public AuthorModel getAuthorById(int id, int expectedStatusCode) {
        return get(AUTHORS_ENDPOINT + getPathVariable(id), AuthorModel.class)
                .expectingStatusCode(expectedStatusCode)
                .readEntity();
    }

    @Step("POST author model to create new author")
    public AuthorModel createNewAuthor(AuthorModel authorModel, int expectedStatusCode) {
        return post(AUTHORS_ENDPOINT, authorModel, AuthorModel.class)
                .expectingStatusCode(expectedStatusCode)
                .readEntity();
    }

    @Step("POST author model to create new author and get response")
    public Response createNewAuthorAndGetResponse(AuthorModel authorModel) {
        return post(AUTHORS_ENDPOINT, authorModel, AuthorModel.class).getResponse();
    }

    @Step("PUT author model to update existing author with '{0}' id")
    public AuthorModel updateAuthor(int id, AuthorModel authorModel, int expectedStatusCode) {
        return put(AUTHORS_ENDPOINT + getPathVariable(id), authorModel, AuthorModel.class)
                .expectingStatusCode(expectedStatusCode)
                .readEntity();
    }

    @Step("PUT author model to update existing author with '{0}' id and get response")
    public Response updateAuthorAndGetResponse(int id, AuthorModel authorModel) {
        return put(AUTHORS_ENDPOINT + getPathVariable(id), authorModel, AuthorModel.class).getResponse();
    }

    @Step("DELETE existing author with '{0}' id")
    public void deleteAuthor(int id, int expectedStatusCode) {
        delete(AUTHORS_ENDPOINT + getPathVariable(id))
                .expectingStatusCode(expectedStatusCode);
    }

    @Step("DELETE existing author with '{0}' id and get response")
    public Response deleteAuthorAndGetResponse(String id) {
        return delete(AUTHORS_ENDPOINT + getPathVariable(id)).getResponse();
    }
}
