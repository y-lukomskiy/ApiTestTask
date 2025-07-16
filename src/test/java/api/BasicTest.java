package api;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.SoftAssertions;

@Log4j2
public abstract class BasicTest {
    protected SoftAssertions soft = new SoftAssertions();
}