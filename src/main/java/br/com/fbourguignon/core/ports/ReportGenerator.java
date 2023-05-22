package br.com.fbourguignon.core.ports;

import java.util.Collection;
import java.util.HashMap;

public interface ReportGenerator {
    byte[] generateReport(String reportName, HashMap<String, Object> parameters, Collection elements);
}
