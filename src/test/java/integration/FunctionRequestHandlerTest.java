package integration;

import br.com.fbourguignon.FunctionRequestHandler;
import br.com.fbourguignon.application.exception.ProcessSQSEventException;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
public class FunctionRequestHandlerTest {
    private static FunctionRequestHandler handler;

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

    @Test
    public void consumeSqsEvent_Success() {
        SQSEvent event = new SQSEvent();
        SQSEvent.SQSMessage message = new SQSEvent.SQSMessage();
        message.setBody("{\"tenant_uuid\":\"84a07635-aced-422d-8a5b-2ffccef4e181\",\"type\":\"INVENTORY_REPORT\",\"parameters\":{\"email_to\":\"company@gmail.com\"}}");

        event.setRecords(Arrays.asList(message));

        handler.execute(event);
    }

    @Test
    public void consumeSqsEvent_InvalidPayload() {
        SQSEvent event = new SQSEvent();
        SQSEvent.SQSMessage message = new SQSEvent.SQSMessage();
        message.setBody("{\"tenant_uuid\":\"ddsfsdfdsfdsfdds\",\"parameters\":{\"email_to\":\"company@gmail.com\"}}");

        event.setRecords(Arrays.asList(message));

        assertThrows(ProcessSQSEventException.class, () -> handler.execute(event));
    }
}
