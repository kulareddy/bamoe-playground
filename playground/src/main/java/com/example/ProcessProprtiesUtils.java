package com.example;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.jbpm.workflow.instance.impl.WorkflowProcessInstanceImpl;
import org.kie.kogito.internal.process.runtime.KogitoProcessContext;
import java.util.Map;

import java.util.Date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.ZoneId;

@ApplicationScoped
public class ProcessProprtiesUtils {   

    private static final Logger LOGGER = Logger.getLogger(ProcessProprtiesUtils.class.getName());
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void setSLA(String slaDateString, KogitoProcessContext context) {
        LOGGER.log( Level.INFO, "Process Instance Id is {0}", context.getProcessInstance().getStringId() );
        WorkflowProcessInstanceImpl kogitoProcessInstance = (WorkflowProcessInstanceImpl)context.getProcessInstance();
        Map<String, Object> metadata = kogitoProcessInstance.getMetaData();
        metadata.forEach((key,value) -> {
            LOGGER.log( Level.INFO, "Metadate Key : {0}", key );
        });
        kogitoProcessInstance.internalSetSlaDueDate(parseSLADate(slaDateString));
        LOGGER.log( Level.INFO, "SLA Due Date: {0}", kogitoProcessInstance.getSlaDueDate() );
        LOGGER.log( Level.INFO, "SLA Compliance: {0}", kogitoProcessInstance.getSlaCompliance() );
        
    }

    private Date parseSLADate(String dateString)  throws DateTimeParseException {
        return Date.from(LocalDate.parse(dateString, formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());        
    }
}
