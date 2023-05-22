package br.com.fbourguignon.core.usecases;

import br.com.fbourguignon.core.domain.Product;
import br.com.fbourguignon.core.ports.EmailSender;
import br.com.fbourguignon.core.ports.ProductRepository;
import br.com.fbourguignon.core.ports.ReportGenerator;


import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GenerateInventoryReport {
    private final String SUBJECT = "Relatório de estoque";
    private final String BODY = "Relatório de produtos no estoque";
    public static final String INVENTORY_REPORT = "inventory_report";
    public static final String PDF_EXTENSION = ".pdf";

    private final ProductRepository repository;
    private final EmailSender emailSender;
    private final ReportGenerator reportGenerator;

    public GenerateInventoryReport(ProductRepository repository, EmailSender emailSender, ReportGenerator reportGenerator) {
        this.repository = repository;
        this.emailSender = emailSender;
        this.reportGenerator = reportGenerator;
    }

    public void sendPaymentReceiptReport(UUID tenantId, String emailTo, String emailReply) {

       List<Product> products = repository.findAllProductsByTenantID(tenantId);

        final byte[] bytes = reportGenerator.generateReport(INVENTORY_REPORT, new HashMap<>(), products);

        emailSender.sendEmail(emailTo, emailReply, SUBJECT, BODY, bytes, INVENTORY_REPORT + PDF_EXTENSION);
    }

}
