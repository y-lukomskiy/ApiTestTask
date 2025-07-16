package api.modeldataproviders;

import api.models.BookModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static api.utils.DateTimeUtils.formatDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookModelProvider extends GenericDataProvider {

    public static BookModel getValidBookModel() {
        int id = faker.number().numberBetween(1000, 10000);
        return BookModel.builder()
                .id(id)
                .title("Book " + id)
                .description(faker.text().text(50, 200))
                .pageCount(faker.number().numberBetween(100, 10000))
                .excerpt(faker.text().text(50, 200))
                .publishDate(formatDateTime(LocalDateTime.now()))
                .build();
    }

    public static BookModel getBookModel(Integer id, String title, String description, Integer pageCount, String excerpt, String publishDate) {
        return BookModel.builder()
                .id(id)
                .title(title)
                .description(description)
                .pageCount(pageCount)
                .excerpt(excerpt)
                .publishDate(publishDate)
                .build();
    }
}