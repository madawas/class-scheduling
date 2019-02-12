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
import org.madawa.genetics.bean.ScheduledClass;
import org.madawa.genetics.bean.SlotIndex;
import org.madawa.genetics.util.CommonConstants;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Insets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

class FinalTimetableUI {
    private static final String NEW_LINE = "<br/>";
    private static final String MODULE = "Module: ";
    private static final String PROFESSOR = "Professor: ";
    private static final String ROOM = "Room: ";
    private static final String HTML = "<html>";
    private static final String HTML_END = "</html>";
    private static final String H_LINE = "<hr/>";

    private JPanel mainPanel;
    private JTable finalTimetable;

    FinalTimetableUI() {
        initComponents();
    }

    void generateTimetableData(List<ScheduledClass> scheduledClasses) {
        DefaultTableModel tableModel = new DefaultTableModel(CommonConstants.TIMETABLE_HEADER, 0);
        StringBuilder[][] classData = new StringBuilder[CommonConstants.NUMBER_OF_SLOTS_PER_DAY][CommonConstants.TABLE_COLUMNS];

        for (int i = 0; i < CommonConstants.NUMBER_OF_SLOTS_PER_DAY; ++i) {
            classData[i][0] = new StringBuilder(SlotIndex.findByKey(i + 1).getTime());
        }

        Map<Integer, List<ScheduledClass>> mappedClasses = scheduledClasses.stream().collect(
                Collectors.groupingBy(scheduledClass -> scheduledClass.getTimeSlot().getSlotIndex().getSlot()));

        mappedClasses.forEach((key, value) -> value.forEach(scheduledClass -> {
            StringBuilder sb = new StringBuilder();
            sb.append(MODULE).append(scheduledClass.getModule().getName()).append(NEW_LINE).append(PROFESSOR)
                    .append(scheduledClass.getProfessor().getName()).append(NEW_LINE).append(ROOM)
                    .append(scheduledClass.getClassroom().getNumber());

            int row = key - 1;
            int col = scheduledClass.getTimeSlot().getWeekday().getDay();

            StringBuilder existing = classData[row][col];
            if (existing == null) {
                sb.insert(0, HTML);
                classData[row][col] = sb.append(HTML_END);
            } else {
                int endTag = existing.indexOf(HTML_END);
                if (endTag != -1) {
                    existing.delete(endTag, endTag + HTML_END.length());
                    existing.append(H_LINE).append(sb).append(HTML);
                }
            }
        }));

        for (int row = 0; row < CommonConstants.NUMBER_OF_SLOTS_PER_DAY; ++row) {
            tableModel.addRow(classData[row]);
        }

        finalTimetable.setModel(tableModel);
    }

    void displayTimetable() {
        JFrame frame = new JFrame(CommonConstants.TIMETABLE_UI_HEADING);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(false);

        Container frameContentPane = frame.getContentPane();
        frameContentPane.setLayout(new BorderLayout());
        frameContentPane.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(frame.getOwner());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        JScrollPane timetableScrollPane = new JScrollPane();
        mainPanel.add(timetableScrollPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
                        0, false));
        finalTimetable = new JTable();
        finalTimetable.setRowHeight(CommonConstants.TABLE_ROW_HEIGHT);

        timetableScrollPane.setViewportView(finalTimetable);
    }
}
