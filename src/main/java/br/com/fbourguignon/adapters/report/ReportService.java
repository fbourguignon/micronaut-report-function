package br.com.fbourguignon.adapters.report;


import br.com.fbourguignon.application.exception.ReportNotFoundException;
import br.com.fbourguignon.application.exception.ReportNotImplementedException;
import br.com.fbourguignon.core.ports.ReportGenerator;
import jakarta.inject.Singleton;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;

import static br.com.fbourguignon.application.constants.ReportConstants.MICRONAUT_LOGO_CLASSPATH;
import static br.com.fbourguignon.application.constants.ReportConstants.REPORT_CLASSPATH;
import static java.lang.String.format;

@Singleton
public class ReportService implements ReportGenerator {
    final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private static final String LOGO_PARAMETER = "logoImage";

    public byte[] generateReport(String reportName, HashMap<String, Object> parameters, Collection elements) {
        JRDataSource datasource;

        if(elements == null){
            datasource = new JREmptyDataSource();
        } else {
            datasource = new JRBeanCollectionDataSource(elements);
        }

        if(parameters == null){
            parameters = new HashMap<>();
        }

        try (InputStream resourceAsStream = loadCompiledJasperReport(reportName)) {
            parameters.put(LOGO_PARAMETER, new ByteArrayInputStream(loadImageLogo().readAllBytes()));
            JasperPrint jasperPrint = JasperFillManager.fillReport(resourceAsStream, parameters, datasource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            logger.error("An error has occurred on generate report", e);
            throw new ReportNotImplementedException("Requested report not implented");
        } catch (Exception e) {
            logger.error("Unexpected error on generate report", e);
            throw new RuntimeException(e);
        }

    }

    private InputStream loadCompiledJasperReport(String fileName) {
        return getFileFromResourceAsStream(format(REPORT_CLASSPATH, fileName));
    }

    private InputStream loadImageLogo() {
        return getFileFromResourceAsStream(MICRONAUT_LOGO_CLASSPATH);
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new ReportNotFoundException("Report file not found! " + fileName);
        }

        return inputStream;
    }
}
