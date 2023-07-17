package integration;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.localstack.LocalStackContainer;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

import java.io.IOException;
@Factory
public class SESLocalStackClientFactory {

    private static Integer LOCALSTACK_PORT = 4566;
    static LocalStackContainer localstack;
    static {
        localstack = new LocalStackContainer()
                .withServices(LocalStackContainer.Service.SES)
                .withExposedPorts(LOCALSTACK_PORT);
        localstack.start();
    }

    @Singleton
    @Replaces(SesClient.class)
    @Requires(property = "integration-tests", value = "true")
    SesClient sesLocalStackClient() throws IOException, InterruptedException {

        String command =
                "awslocal ses verify-email-identity --email-address company@gmail.com --region us-east-1 --endpoint-url http://localhost:4566";
        Container.ExecResult result = localstack.execInContainer(command.split(" "));

        if (result.getExitCode() != 0) {
            throw new RuntimeException(result.getStderr());
        }

        return SesClient
                .builder()
                .endpointOverride(localstack.getEndpointOverride(LocalStackContainer.Service.SES))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                        localstack.getAccessKey(), localstack.getSecretKey())))
                .region(Region.of(localstack.getRegion()))
                .build();
    }
}
