package api.dataproviders;

import api.apimanagers.AuthorsApiManager;
import api.modeldataproviders.GenericDataProvider;
import api.models.AuthorModel;
import api.utils.RandomUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testng.annotations.DataProvider;

import java.util.List;

import static api.modeldataproviders.AuthorModelProvider.getAuthorModel;
import static api.modeldataproviders.AuthorModelProvider.getValidAuthorModel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorTestsDataProvider extends GenericDataProvider {
    private static final AuthorsApiManager authorsApiManager = new AuthorsApiManager();

    @DataProvider(name = "getExistingAuthor")
    public static Object[][] getExistingAuthor() {
        List<AuthorModel> authors = authorsApiManager
                .getAllAuthors(200)
                .stream()
                .filter(author -> author.getId() < 300)
                .toList();
        AuthorModel randomAuthorModel = RandomUtils.selectRandomItem(authors);

        return new Object[][]{
                {
                        randomAuthorModel
                },
        };
    }

    @DataProvider(name = "getRandomValidAuthor")
    public static Object[][] getRandomValidAuthor() {
        AuthorModel validNewAuthor = getValidAuthorModel();

        return new Object[][]{
                {
                        validNewAuthor
                }
        };
    }

    @DataProvider(name = "getInvalidAuthorModels")
    public static Object[][] getInvalidAuthorModels() {
        var firstName = faker.name().firstName();
        var lastName = faker.name().lastName();

        return new Object[][]{
                {
                        getAuthorModel(null, null, firstName, lastName)
                },
                {
                        getAuthorModel(null, 1, firstName, lastName)
                },
                {
                        getAuthorModel(5, null, firstName, lastName)
                }
        };
    }
}