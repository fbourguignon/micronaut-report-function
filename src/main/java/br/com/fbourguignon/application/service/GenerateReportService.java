package br.com.fbourguignon.application.service;

import br.com.fbourguignon.adapters.email.SESService;
import br.com.fbourguignon.adapters.report.ReportService;
import br.com.fbourguignon.application.constants.ReportConstants;
import br.com.fbourguignon.application.dto.ReportRequestDTO;
import br.com.fbourguignon.application.exception.ReportNotImplementedException;
import br.com.fbourguignon.core.ports.ProductRepository;
import br.com.fbourguignon.core.usecases.GenerateInventoryReport;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class GenerateReportService {

    private final ProductRepository productRepository;
    private final SESService sesService;
    private final ReportService reportService;

    @Inject
    public GenerateReportService(ProductRepository productRepository, SESService sesService, ReportService reportService) {
        this.productRepository = productRepository;
        this.sesService = sesService;
        this.reportService = reportService;
    }

    public void generateReport(ReportRequestDTO reportRequestDTO) {
        switch (reportRequestDTO.getType()) {
            case ReportConstants.INVENTORY_REPORT:
                sendInventoryReport(reportRequestDTO);
                break;
            default:
                log.error("Requested report are not implemented [{}]", reportRequestDTO.getType());
                throw new ReportNotImplementedException("Requested report are not implemented");
        }
    }

    private void sendInventoryReport(ReportRequestDTO reportRequestDTO) {

        String emailTo = reportRequestDTO.getParameters().get(ReportConstants.EMAIL_TO_PARAMETERS);

        new GenerateInventoryReport(productRepository, sesService, reportService)
                .sendInventoryReport(reportRequestDTO.getTenantUUID(), emailTo);
    }

}
