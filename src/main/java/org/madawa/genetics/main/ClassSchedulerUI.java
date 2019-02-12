/*
 * Copyright (c) 2019 Madawa Soysa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.madawa.genetics.main;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.madawa.genetics.algorithm.Algorithm;
import org.madawa.genetics.bean.TimeSlot;
import org.madawa.genetics.util.CommonConstants;
import org.madawa.genetics.util.LogAppender;
import org.madawa.genetics.configuration.AppConfig;
import org.madawa.genetics.configuration.AppData;
import org.madawa.genetics.configuration.ConfigurationManager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

public class ClassSchedulerUI {
    private static final Logger log = Logger.getLogger(ClassSchedulerUI.class);
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel consolePanel;
    private JTextArea console;
    private JButton scheduleClassesButton;
    private JButton viewTimetableButton;
    private JScrollPane textAreaScrollPane;
    private JPanel buttonsPanel;

    private AppConfig appConfig;
    private AppData appData;
    private Timetable timetable;

    protected ClassSchedulerUI() {
        initComponents();
        this.appConfig = ConfigurationManager.getAppConfiguration(CommonConstants.DEFAULT_CONFIG_PATH);
        this.appData = ConfigurationManager.getAppData(CommonConstants.DEFAULT_DATA_PATH);
    }

    private void scheduleClassesButtonActionPerformed(ActionEvent e) {
        Timetable timetable = this.initializeTimetable();
        log.info("Starting to schedule classes");
        Algorithm algorithm = new Algorithm(appConfig.getInitialPopulationSize(), appConfig.getMutationRate(),
                appConfig.getCrossoverRate(), appConfig.getElitismCount(), appConfig.getTournamentCount());
        this.timetable = algorithm.runGA(timetable, appConfig.getMaxGenerations());

        this.viewTimetableButton.setEnabled(true);
    }

    private void viewTimetableButtonActionPerformed(ActionEvent e) {
        FinalTimetableUI finalTimetableUI = new FinalTimetableUI();
        finalTimetableUI.generateTimetableData(Arrays.asList(timetable.getScheduledClasses()));
        finalTimetableUI.displayTimetable();
    }

    /**
     * Initiate components
     */
    private void initComponents() {
        frame = new JFrame(CommonConstants.APP_UI_HEADING);
        mainPanel = new JPanel();
        consolePanel = new JPanel();
        textAreaScrollPane = new JScrollPane();
        console = new JTextArea();
        buttonsPanel = new JPanel();
        scheduleClassesButton = new JButton();
        viewTimetableButton = new JButton();
        final Spacer spacer1 = new Spacer();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(false);

        mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), 5, 5));
        consolePanel.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        buttonsPanel.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 10, 10), -1, -1));

        scheduleClassesButton.setText("Schedule Classes");
        scheduleClassesButton.addActionListener(this::scheduleClassesButtonActionPerformed);
        viewTimetableButton.setText("View Timetable");
        viewTimetableButton.addActionListener(this::viewTimetableButtonActionPerformed);
        viewTimetableButton.setEnabled(false);

        buttonsPanel.add(scheduleClassesButton,
                new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonsPanel.add(viewTimetableButton,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        consolePanel.setBorder(
                BorderFactory.createTitledBorder(null, "Console", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
        console.setTabSize(4);
        textAreaScrollPane.setViewportView(console);
        console.setEditable(false);
        createLogAppender(this.console);
        DefaultCaret caret = (DefaultCaret) console.getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);

        consolePanel.add(textAreaScrollPane,
                new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null,
                        new Dimension(300, 250), null, 0, true));
        mainPanel.add(consolePanel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        mainPanel.add(buttonsPanel,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        buttonsPanel.add(spacer1,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(300, -1), null, 0, false));

        Container frameContentPane = frame.getContentPane();
        frameContentPane.setLayout(new BorderLayout());
        frameContentPane.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(frame.getOwner());
    }

    private Timetable initializeTimetable() {
        Timetable timetable = new Timetable(this.appData, this.appConfig);
        List<TimeSlot> timeSlots = new ArrayList<>();

        int id = 1;
        for (int day = 1; day < CommonConstants.TABLE_COLUMNS; ++day) {
            for (int slot = 1; slot <= CommonConstants.NUMBER_OF_SLOTS_PER_DAY; ++slot) {
                timeSlots.add(new TimeSlot(id, day, slot));
                ++id;
            }
        }

        timetable.setTimeSlots(timeSlots);
        return timetable;
    }

    /**
     * Initializes UI log appender.
     *
     * @param console {@link JTextArea}
     */
    private void createLogAppender(JTextArea console) {
        LogAppender logAppender = new LogAppender(console);
        LogManager.getRootLogger().addAppender(logAppender);
    }

    public static void main(String[] args) {
        String log4jConfPath = CommonConstants.DEFAULT_LOG_PROPERTY_PATH;
        PropertyConfigurator.configure(log4jConfPath);

        ClassSchedulerUI classSchedulerUI = new ClassSchedulerUI();
        classSchedulerUI.frame.setVisible(true);
    }
}
