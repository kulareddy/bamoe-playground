package com.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.jbpm.workflow.instance.impl.WorkflowProcessInstanceImpl;
import org.kie.kogito.internal.process.runtime.KogitoProcessContext;

@ApplicationScoped
public class ProcessProprtiesUtils {   

    private static final Logger LOGGER = Logger.getLogger(ProcessProprtiesUtils.class.getName());
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public void setSLA(KogitoProcessContext context) {
        LOGGER.log( Level.INFO, "Process Instance Id is {0}", context.getProcessInstance().getStringId() );
        WorkflowProcessInstanceImpl kogitoProcessInstance = (WorkflowProcessInstanceImpl)context.getProcessInstance();

        String slaDateString = (String) context.getVariable("slaDateString");
        LOGGER.log( Level.INFO, "slaDateString: {0}", slaDateString );

        LocalDateTime slaDueDate = LocalDateTime.parse(slaDateString, ProcessProprtiesUtils.formatter);
        LOGGER.log( Level.INFO, "slaDueDate: {0}", slaDueDate );

        String slaExpression = generateSLAExprssion(slaDueDate);
        LOGGER.log( Level.INFO, "SLA Expression: {0}", slaExpression );
        context.setVariable("slaExpression", slaExpression);
      
        kogitoProcessInstance.configureSLATimer(slaExpression);
        LOGGER.log( Level.INFO, "SLA Due Date: {0}", kogitoProcessInstance.getSlaDueDate() );
        LOGGER.log( Level.INFO, "SLA Compliance: {0}", kogitoProcessInstance.getSlaCompliance() );        
        
    } 

    public void printSLA(KogitoProcessContext context) {
        LOGGER.log( Level.INFO, "Process Instance Id is {0}", context.getProcessInstance().getStringId() );
        WorkflowProcessInstanceImpl kogitoProcessInstance = (WorkflowProcessInstanceImpl)context.getProcessInstance();
        LOGGER.log( Level.INFO, "Process SLA Due Date: {0}", kogitoProcessInstance.getSlaDueDate() );
        LOGGER.log( Level.INFO, "Process SLA Compliance: {0}", kogitoProcessInstance.getSlaCompliance() );  
        
    }


    private String generateSLAExprssion(LocalDateTime slaDueDate)   {
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(now, slaDueDate);

        // Break down the total duration into days, hours, and minutes
        long totalMinutes = duration.toMinutes(); // Get the total number of minutes
        long days = TimeUnit.MINUTES.toDays(totalMinutes); // Convert minutes to days
        long hours = TimeUnit.MINUTES.toHours(totalMinutes) % 24; // Convert remaining minutes to hours
        long minutes = totalMinutes % 60; // Get the remaining minutes

        // Build the SLA string in the form of "XdYhZm"
        StringBuilder slaDurationString = new StringBuilder();
        if (days > 0) {
            slaDurationString.append(days).append("d");
        }
        if (hours > 0) {
            slaDurationString.append(hours).append("h");
        }
        if (minutes > 0) {
            slaDurationString.append(minutes).append("m");
        }
         
        return slaDurationString.toString();
    }

   
}
