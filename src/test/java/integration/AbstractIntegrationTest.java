package integration;

import br.com.fbourguignon.FunctionRequestHandler;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

@MicronautTest
public abstract class AbstractIntegrationTest {
    protected static FunctionRequestHandler handler;
    @BeforeAll
    public static void setupServer() {
        handler = new FunctionRequestHandler();
    }
    @AfterAll
    public static void stopServer() {
        if (handler != null) {
            handler.getApplicationContext().close();
        }
    }

}
