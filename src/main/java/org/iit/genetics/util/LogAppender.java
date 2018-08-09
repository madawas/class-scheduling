package org.iit.genetics.util;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextArea;

/**
 * Extended appender for application logs
 */
public class LogAppender extends AppenderSkeleton {
    private final JTextArea console;

    public LogAppender(JTextArea console) {
        this.console = console;
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        console.append(generateLogMessage(loggingEvent));
    }

    /**
     * Generates formatted log message
     * @param event {@link LoggingEvent}
     * @return formatted log message
     */
    private String generateLogMessage(LoggingEvent event) {
        DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (event.locationInformationExists()) {
            StringBuilder message = new StringBuilder();
            String className = event.getLogger().getName();
            message.append("\n");
            message.append(dFormat.format(new Date(event.getTimeStamp())));
            message.append("  ");
            message.append(event.getLevel());
            message.append(" ");
            message.append(className.substring(className.lastIndexOf('.') + 1));
            message.append(":");
            message.append(event.getLocationInformation().getLineNumber());
            message.append(" - ");
            message.append(event.getMessage().toString());
            return message.toString();
        }
        return event.getMessage().toString();
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
