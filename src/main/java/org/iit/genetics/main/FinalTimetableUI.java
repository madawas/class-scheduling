package org.iit.genetics.main;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.iit.genetics.bean.ScheduledClass;
import org.iit.genetics.bean.SlotIndex;
import org.iit.genetics.util.CommonConstants;

import java.awt.BorderLayout;
import java.awt.Component;
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
    private static final int COLUMNS = 6;
    private static final int ROWS = 4;

    private JPanel mainPanel;
    private JTable finalTimetable;

    FinalTimetableUI() {
        initComponents();
    }

    void generateTimetableData(List<ScheduledClass> scheduledClasses) {
        DefaultTableModel tableModel = new DefaultTableModel(CommonConstants.TIMETABLE_HEADER, 0);
        StringBuilder[][] classData = new StringBuilder[ROWS][COLUMNS];

        for (int i = 0; i < ROWS; ++i) {
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
                existing.delete(endTag, endTag + HTML_END.length());
                existing.append(H_LINE).append(sb).append(HTML);
            }
        }));

        for (int row = 0; row < ROWS; ++row) {
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
