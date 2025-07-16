package api.apimanagers;

import api.client.ApiClient;
import api.models.BookModel;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

public class BooksApiManager extends ApiClient {
    private static final String BOOKS_ENDPOINT = "/Books";

    @Step("Get all books")
    public List<BookModel> getAllBooks(int expectedStatusCode) {
        return get(BOOKS_ENDPOINT, BookModel.class)
                .expectingStatusCode(expectedStatusCode)
                .readEntities();
    }

    @Step("Get book by '{0}' id response")
    public Response getBookByIdResponse(String id) {
        return get(BOOKS_ENDPOINT + getPathVariable(id), BookModel.class).getResponse();
    }

    @Step("Get book by '{0}' id")
    public BookModel getBookById(int id, int expectedStatusCode) {
        return get(BOOKS_ENDPOINT + getPathVariable(id), BookModel.class)
                .expectingStatusCode(expectedStatusCode)
                .readEntity();
    }

    @Step("POST book model to create new book")
    public BookModel createNewBook(BookModel bookModel, int expectedStatusCode) {
        return post(BOOKS_ENDPOINT, bookModel, BookModel.class)
                .expectingStatusCode(expectedStatusCode)
                .readEntity();
    }

    @Step("POST book model to create new book and get response")
    public Response createNewBookAndGetResponse(BookModel bookModel) {
        return post(BOOKS_ENDPOINT, bookModel, BookModel.class).getResponse();
    }

    @Step("PUT book model to update existing book with '{0}' id")
    public BookModel updateBook(int id, BookModel bookModel, int expectedStatusCode) {
        return put(BOOKS_ENDPOINT + getPathVariable(id), bookModel, BookModel.class)
                .expectingStatusCode(expectedStatusCode)
                .readEntity();
    }

    @Step("PUT book model to update existing book with '{0}' id and get response")
    public Response updateBookAndGetResponse(int id, BookModel bookModel) {
        return put(BOOKS_ENDPOINT + getPathVariable(id), bookModel, BookModel.class).getResponse();
    }

    @Step("DELETE existing book with '{0}' id")
    public void deleteBook(int id, int expectedStatusCode) {
        delete(BOOKS_ENDPOINT + getPathVariable(id))
                .expectingStatusCode(expectedStatusCode);
    }

    @Step("DELETE existing book with '{0}' id and get response")
    public Response deleteBookAndGetResponse(String id) {
        return delete(BOOKS_ENDPOINT + getPathVariable(id)).getResponse();
    }
}