package api.modeldataproviders;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericDataProvider {
    protected static final Faker faker = new Faker();
}
