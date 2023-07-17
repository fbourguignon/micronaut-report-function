package br.com.fbourguignon.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@Introspected
public class ReportRequestDTO {

    @NotEmpty
    @JsonProperty("tenant_uuid")
    private UUID tenantUUID;

    @NotEmpty
    private String type;

    @Builder.Default
    private Map<String,String> parameters = new HashMap<>();
}
