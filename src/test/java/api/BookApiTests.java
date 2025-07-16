package api;

import api.apimanagers.BooksApiManager;
import api.dataproviders.BookTestsDataProvider;
import api.models.BookModel;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static api.utils.DateTimeUtils.parseDateTime;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class BookApiTests extends BasicTest {
    private final BooksApiManager booksManager = new BooksApiManager();
    private final String responseShouldBe400or404Error = "Response status code should be 400 or 404";
    private final String responseShouldBe400or404ErrorForId = "Response status code should be 400 or 404 for '%s' id";

    @Test
    @Description("Test GET /api/v1/Books endpoint")
    public void testGetAllBooksEndpoint() {
        var listOfBooks = booksManager.getAllBooks(SC_OK);
        assertThat(listOfBooks)
                .as("List of books should not be empty")
                .isNotNull()
                .hasSize(200);
    }

    @Test(dataProvider = "getExistingBook",
            dataProviderClass = BookTestsDataProvider.class,
            description = "Test GET /api/v1/Books/{id} endpoint")
    public void testGetBookByIdEndpoint(BookModel existingBook) {
        var bookById = booksManager.getBookById(existingBook.getId(), SC_OK);
        soft.assertThat(bookById.getTitle())
                .as("Book title should match expected")
                .isEqualTo(existingBook.getTitle());
        soft.assertThat(bookById.getPageCount())
                .as("Book page count should match expected")
                .isEqualTo(existingBook.getPageCount());
        soft.assertThat(parseDateTime(bookById.getPublishDate()))
                .as("Book publish date should be close to expected")
                .isCloseTo(parseDateTime(existingBook.getPublishDate()), within(5, ChronoUnit.SECONDS));
        soft.assertAll();
    }

    @Test(description = "Test invalid ids GET /api/v1/Books/{id} endpoint")
    public void testGetBookByIdEndpointWithInvalidIds() {
        var invalidIds = List.of("Five", UUID.randomUUID().toString(), "0", "-1", "1.01", "1000", "2147483648", "-2,147,483,649");
        invalidIds.forEach(id -> {
            var bookByIdResponse = booksManager.getBookByIdResponse(id);
            soft.assertThat(bookByIdResponse.getStatusCode())
                    .as(responseShouldBe400or404ErrorForId, id)
                    .isIn(400, 404);

        });
        soft.assertAll();
    }

    @Test(dataProvider = "getRandomValidBook",
            dataProviderClass = BookTestsDataProvider.class,
            description = "Test POST /api/v1/Books endpoint")
    public void testPostBooksEndpoint(BookModel validNewBook) {
        var newBookResponse = booksManager.createNewBook(validNewBook, SC_OK);
        assertThat(newBookResponse)
                .as("Valid model should be returned after book creation")
                .isEqualTo(validNewBook);
    }

    @Test(dataProvider = "getInvalidBookModels",
            dataProviderClass = BookTestsDataProvider.class,
            description = "Test POST /api/v1/Books endpoint with invalid data")
    public void testPostBooksEndpointWithInvalidData(BookModel invalidBookModel) {
        var postNewBookResponse = booksManager.createNewBookAndGetResponse(invalidBookModel);
        assertThat(postNewBookResponse.getStatusCode())
                .as(responseShouldBe400or404Error)
                .isIn(400, 404);
    }

    @Test(dataProvider = "getExistingBook",
            dataProviderClass = BookTestsDataProvider.class,
            description = "Test PUT /api/v1/Books/{id} endpoint")
    public void testPutBookByIdEndpoint(BookModel existingBook) {
        var updateBookResponse = booksManager.updateBook(existingBook.getId(), existingBook, SC_OK);
        assertThat(updateBookResponse)
                .as("Valid model should be returned after book update")
                .isEqualTo(existingBook);
    }

    @Test(dataProvider = "getInvalidBookModels",
            dataProviderClass = BookTestsDataProvider.class,
            description = "Test PUT /api/v1/Books/{id} endpoint with invalid data")
    public void testPutBooksEndpointWithInvalidData(BookModel invalidBookModel) {
        var id = invalidBookModel.getId() == null ? 9999 : invalidBookModel.getId();
        var updateBookResponse = booksManager.updateBookAndGetResponse(id, invalidBookModel);
        assertThat(updateBookResponse.getStatusCode())
                .as(responseShouldBe400or404Error)
                .isIn(400, 404);
    }

    @Test(dataProvider = "getExistingBook",
            dataProviderClass = BookTestsDataProvider.class,
            description = "Test DELETE /api/v1/Books/{id} endpoint")
    public void testDeleteBookByIdEndpoint(BookModel existingBook) {
        booksManager.deleteBook(existingBook.getId(), SC_OK);
    }

    @Test(description = "Test DELETE /api/v1/Books/{id} endpoint with invalid ids")
    public void testDeleteBookByIdEndpointWithInvalidIds() {
        var invalidIds = List.of("Text", UUID.randomUUID().toString());
        invalidIds.forEach(id -> {
            var deleteBookResponse = booksManager.deleteBookAndGetResponse(id);
            soft.assertThat(deleteBookResponse.getStatusCode())
                    .as(responseShouldBe400or404ErrorForId, id)
                    .isIn(400, 404);
        });
        soft.assertAll();
    }
}