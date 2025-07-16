package api.modeldataproviders;

import api.models.AuthorModel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthorModelProvider extends GenericDataProvider {

    public static AuthorModel getValidAuthorModel() {
        return AuthorModel.builder()
                .id(faker.number().numberBetween(1000, 10000))
                .idBook(faker.number().numberBetween(1, 200))
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();
    }

    public static AuthorModel getAuthorModel(Integer id, Integer idBook, String firstName, String lastName) {
        return AuthorModel.builder()
                .id(id)
                .idBook(idBook)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}