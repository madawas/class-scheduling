package org.iit.genetics.util;

import java.io.File;

public class CommonConstants {

    public static final String DEFAULT_CONFIG_PATH = "conf" + File.separator + "app_config.yaml";

    public static final String DEFAULT_DATA_PATH = "data" + File.separator + "data.yaml";

    public static final String DEFAULT_LOG_PROPERTY_PATH = "conf" + File.separator + "log4j.properties";

    public static final String[] TIMETABLE_HEADER = {"TIME", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};

    public static final Integer TABLE_ROW_HEIGHT = 250;

    public static final String APP_UI_HEADING = "Class Scheduler - v1.0.0";

    public static final String TIMETABLE_UI_HEADING = "Scheduled Timetable";

    public static final int TABLE_COLUMNS = 6;

    public static final int NUMBER_OF_SLOTS_PER_DAY = 4;
}
