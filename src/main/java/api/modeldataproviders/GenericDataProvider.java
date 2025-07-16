package api.modeldataproviders;

import lombok.NoArgsConstructor;
import net.datafaker.Faker;

@NoArgsConstructor
public class GenericDataProvider {
    protected static final Faker faker = new Faker();
}
