package api.dataproviders;

import api.apimanagers.BooksApiManager;
import api.modeldataproviders.GenericDataProvider;
import api.models.BookModel;
import api.utils.RandomUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testng.annotations.DataProvider;

import java.time.LocalDateTime;
import java.util.List;

import static api.modeldataproviders.BookModelProvider.getBookModel;
import static api.modeldataproviders.BookModelProvider.getValidBookModel;
import static api.utils.DateTimeUtils.formatDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookTestsDataProvider extends GenericDataProvider {
    private static final BooksApiManager booksManager = new BooksApiManager();

    @DataProvider(name = "getExistingBook")
    public static Object[][] getExistingBook() {
        List<BookModel> books = booksManager.getAllBooks(200);
        BookModel randomBookModel = RandomUtils.selectRandomItem(books);

        return new Object[][]{
                {
                        randomBookModel
                },
        };
    }

    @DataProvider(name = "getRandomValidBook")
    public static Object[][] getRandomValidBook() {
        BookModel validNewBook = getValidBookModel();

        return new Object[][]{
                {
                        validNewBook
                }
        };
    }

    @DataProvider(name = "getInvalidBookModels")
    public static Object[][] getInvalidBookModels() {
        var randomText = faker.text().text(10, 100);
        var validDate = formatDateTime(LocalDateTime.now());

        return new Object[][]{
                {
                        getBookModel(null, randomText, randomText, 5, randomText, validDate)
                },
                {
                        getBookModel(3, randomText, randomText, 5, randomText, null)
                },
                {
                        getBookModel(5, randomText, randomText, 5, randomText, randomText)
                },
                {
                        getBookModel(11, randomText, faker.text().text(100000), 5, randomText, randomText)
                },
                {
                        getBookModel(99, faker.text().text(100000), randomText, 5, randomText, randomText)
                }
        };
    }
}
