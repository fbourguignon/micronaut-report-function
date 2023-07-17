package br.com.fbourguignon;

import br.com.fbourguignon.application.dto.ReportRequestDTO;
import br.com.fbourguignon.application.exception.ProcessSQSEventException;
import br.com.fbourguignon.application.service.GenerateReportService;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FunctionRequestHandler extends MicronautRequestHandler<SQSEvent, Void> {
    @Inject
    ObjectMapper objectMapper;
    @Inject
    GenerateReportService reportService;
    
    @Override
    public Void execute(SQSEvent event) {
        try {
            for (SQSEvent.SQSMessage message : event.getRecords()) {
                log.info("Processing SQS message [{}]", message.getMessageId());
                ReportRequestDTO reportRequestDTO = convertEventPayload(message.getBody());
                reportService.generateReport(reportRequestDTO);
                log.info("Message processed with success [{}]", message.getMessageId());
            }
        } catch (Exception e) {
            log.error("An error has occurred on process SQS event [{}]", e.getMessage());
            throw new ProcessSQSEventException(e.getMessage());
        }

        return null;
    }

    private ReportRequestDTO convertEventPayload(String payload) throws JsonProcessingException {
        return objectMapper.readValue(payload, ReportRequestDTO.class);
    }
}
