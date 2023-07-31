package br.com.fbourguignon.unit.core;


import br.com.fbourguignon.core.domain.Product;
import br.com.fbourguignon.core.ports.EmailSender;
import br.com.fbourguignon.core.ports.ProductRepository;
import br.com.fbourguignon.core.ports.ReportGenerator;
import br.com.fbourguignon.core.usecases.GenerateInventoryReport;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@MicronautTest
public class GenerateInventoryReportTest {

    @Inject
    private ProductRepository repository;
    @Inject
    private EmailSender emailSender;
    @Inject
    private ReportGenerator reportGenerator;

    @Test
    void generateInventoryReport_GenerateReportSuccess() {
        UUID tenantId = UUID.fromString("8b904dc1-1aa3-43a4-9b79-deedb0adbde2");
        String email = "email@gmail.com";
        byte[] reportBinaries = new byte[10];

        Product product = new Product();
        product.setTenantId(tenantId);
        product.setCategory("Consoles");
        product.setName("PS5");
        product.setPrice(4500.00);

        List<Product> productList = Arrays.asList(product);



        when(repository.findAllProductsByTenantID(tenantId))
                .then(invocation -> productList);

        when(reportGenerator.generateReport(eq("inventory_report"),eq(new HashMap<>()),eq(productList)))
                .then(invocation -> reportBinaries);

        new GenerateInventoryReport(repository, emailSender, reportGenerator)
                .sendInventoryReport(tenantId, email);

        verify(repository,times(1)).findAllProductsByTenantID(eq(tenantId));
        verify(reportGenerator,times(1)).generateReport(eq("inventory_report"), eq(new HashMap<>()), eq(productList));
        verify(emailSender, times(1)).sendEmail(
                eq("email@gmail.com"),
                eq("Relatório de estoque"),
                eq("Relatório de produtos no estoque"),
                eq(reportBinaries),
                eq("inventory_report.pdf"));
    }

    @Test
    void generateInventoryReport_RepositoryError() {
        UUID tenantId = UUID.fromString("8b904dc1-1aa3-43a4-9b79-deedb0adbde2");
        String email = "email@gmail.com";
        byte[] reportBinaries = new byte[10];

        Product product = new Product();
        product.setTenantId(tenantId);
        product.setCategory("Consoles");
        product.setName("PS5");
        product.setPrice(4500.00);

        List<Product> productList = Arrays.asList(product);

        when(repository.findAllProductsByTenantID(tenantId))
                .thenThrow(NullPointerException.class);

        when(reportGenerator.generateReport(eq("inventory_report"),eq(new HashMap<>()),eq(productList)))
                .then(invocation -> reportBinaries);

        assertThrows(NullPointerException.class, () -> new GenerateInventoryReport(repository, emailSender, reportGenerator)
                .sendInventoryReport(tenantId, email));

        verify(reportGenerator,never()).generateReport(any(),any(),any());
        verify(emailSender, never()).sendEmail(any(),any(), any(), any(), any());
    }

    @Test
    void generateInventoryReport_GenerateReportError() {
        UUID tenantId = UUID.fromString("8b904dc1-1aa3-43a4-9b79-deedb0adbde2");
        String email = "email@gmail.com";

        Product product = createMockProduct(tenantId);

        List<Product> productList = Arrays.asList(product);

        when(repository.findAllProductsByTenantID(tenantId))
                .then(invocation -> productList);

        when(reportGenerator.generateReport(eq("inventory_report"),eq(new HashMap<>()),eq(productList)))
                .thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> new GenerateInventoryReport(repository, emailSender, reportGenerator)
                .sendInventoryReport(tenantId, email));

        verify(repository,times(1)).findAllProductsByTenantID(eq(tenantId));
        verify(reportGenerator,times(1)).generateReport(eq("inventory_report"), eq(new HashMap<>()), eq(productList));
        verify(emailSender, never()).sendEmail(any(),any(), any(), any(), any());
    }

    @NotNull
    private Product createMockProduct(UUID tenantId) {
        Product product = new Product();
        product.setTenantId(tenantId);
        product.setCategory("Consoles");
        product.setName("PS5");
        product.setPrice(4500.00);
        return product;
    }

    @MockBean(ProductRepository.class)
    ProductRepository productRepository() {
        return mock(ProductRepository.class);
    }

    @MockBean(EmailSender.class)
    EmailSender emailSender() {
        return mock(EmailSender.class);
    }

    @MockBean(ReportGenerator.class)
    ReportGenerator reportGenerator() {
        return mock(ReportGenerator.class);
    }

}
