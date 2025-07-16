package api;

import api.apimanagers.AuthorsApiManager;
import api.apimanagers.BooksApiManager;
import api.dataproviders.AuthorTestsDataProvider;
import api.models.AuthorModel;
import api.models.BookModel;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthorApiTests extends BasicTest {
    private final AuthorsApiManager authorManager = new AuthorsApiManager();
    private final String responseShouldBe400or404Error = "Response status code should be 400 or 404";
    private final String responseShouldBe400or404ErrorForId = "Response status code should be 400 or 404 for '%s' id";

    @Test
    @Description("Test GET /api/v1/Authors endpoint")
    public void testGetArtistEndpoint() {
        var listOfAuthors = authorManager.getAllAuthors(SC_OK);
        assertThat(listOfAuthors)
                .as("List of artists should not be empty")
                .isNotNull()
                .hasSizeGreaterThan(200);
    }

    @Test(dataProvider = "getExistingAuthor",
            dataProviderClass = AuthorTestsDataProvider.class,
            description = "Test GET /api/v1/Authors/{id} endpoint")
    public void testGetAuthorByIdEndpoint(AuthorModel existingAuthor) {
        var authorById = authorManager.getAuthorById(existingAuthor.getId(), SC_OK);
        BookModel linkedBook = new BooksApiManager().getBookById(authorById.getIdBook(), SC_OK);
        soft.assertThat(authorById.getId())
                .as("Author id should match expected")
                .isEqualTo(existingAuthor.getId());
        soft.assertThat(authorById.getFirstName())
                .as("Artist first name should match expected")
                .isEqualTo(existingAuthor.getFirstName());
        soft.assertThat(authorById.getLastName())
                .as("Artist last name should match expected")
                .isEqualTo(existingAuthor.getLastName());
        soft.assertThat(linkedBook)
                .as("Linked book should not be null")
                .isNotNull();
        soft.assertAll();
    }


    @Test(description = "Test invalid ids GET /api/v1/Authors/{id} endpoint")
    public void testGetAuthorByIdEndpointWithInvalidIds() {
        var invalidIds = List.of("Five", UUID.randomUUID().toString(), "0", "-1", "1.01", "1000", "2147483648", "-2,147,483,649");
        invalidIds.forEach(id -> {
            var authorByIdResponse = authorManager.getAuthorByIdResponse(id);
            soft.assertThat(authorByIdResponse.getStatusCode())
                    .as(responseShouldBe400or404ErrorForId, id)
                    .isIn(400, 404);

        });
        soft.assertAll();
    }

    @Test(dataProvider = "getRandomValidAuthor",
            dataProviderClass = AuthorTestsDataProvider.class,
            description = "Test POST /api/v1/Authors endpoint")
    public void testPostAuthorsEndpoint(AuthorModel validNewAuthor) {
        var newAuthorResponse = authorManager.createNewAuthor(validNewAuthor, SC_OK);
        assertThat(newAuthorResponse)
                .as("Valid model should be returned after Author creation")
                .isEqualTo(validNewAuthor);
    }

    @Test(dataProvider = "getInvalidAuthorModels",
            dataProviderClass = AuthorTestsDataProvider.class,
            description = "Test POST /api/v1/Authors endpoint with invalid data")
    public void testPostAuthorsEndpointWithInvalidData(AuthorModel invalidAuthorModel) {
        var postNewAuthorResponse = authorManager.createNewAuthorAndGetResponse(invalidAuthorModel);
        assertThat(postNewAuthorResponse.getStatusCode())
                .as(responseShouldBe400or404Error)
                .isIn(400, 404);
    }

    @Test(dataProvider = "getExistingAuthor",
            dataProviderClass = AuthorTestsDataProvider.class,
            description = "Test PUT /api/v1/Authors/{id} endpoint")
    public void testPutAuthorByIdEndpoint(AuthorModel existingAuthor) {
        var updateAuthorResponse = authorManager.updateAuthor(existingAuthor.getId(), existingAuthor, SC_OK);
        assertThat(updateAuthorResponse)
                .as("Valid model should be returned after Author update")
                .isEqualTo(existingAuthor);
    }

    @Test(dataProvider = "getInvalidAuthorModels",
            dataProviderClass = AuthorTestsDataProvider.class,
            description = "Test PUT /api/v1/Authors/{id} endpoint with invalid data")
    public void testPutAuthorsEndpointWithInvalidData(AuthorModel invalidAuthorModel) {
        var id = invalidAuthorModel.getId() == null ? 9999 : invalidAuthorModel.getId();
        var updateAuthorResponse = authorManager.updateAuthorAndGetResponse(id, invalidAuthorModel);
        assertThat(updateAuthorResponse.getStatusCode())
                .as(responseShouldBe400or404Error)
                .isIn(400, 404);
    }

    @Test(dataProvider = "getExistingAuthor",
            dataProviderClass = AuthorTestsDataProvider.class,
            description = "Test DELETE /api/v1/Authors/{id} endpoint")
    public void testDeleteAuthorByIdEndpoint(AuthorModel existingAuthor) {
        authorManager.deleteAuthor(existingAuthor.getId(), SC_OK);
    }

    @Test(description = "Test DELETE /api/v1/Authors/{id} endpoint with invalid ids")
    public void testDeleteAuthorByIdEndpointWithInvalidIds() {
        var invalidIds = List.of("Text", UUID.randomUUID().toString());
        invalidIds.forEach(id -> {
            var deleteAuthorResponse = authorManager.deleteAuthorAndGetResponse(id);
            soft.assertThat(deleteAuthorResponse.getStatusCode())
                    .as(responseShouldBe400or404ErrorForId, id)
                    .isIn(400, 404);

        });
        soft.assertAll();
    }
}
