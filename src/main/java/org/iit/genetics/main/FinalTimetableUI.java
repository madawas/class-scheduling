package org.iit.genetics.main;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.iit.genetics.bean.ScheduledClass;
import org.iit.genetics.util.CommonConstants;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Insets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class FinalTimetableUI {
    public static final String NEW_LINE = "\n";
    private JFrame frame;
    private JPanel mainPanel;
    private JScrollPane timetableScrollPane;
    private JTable finalTimetable;

    FinalTimetableUI() {
        initComponents();
    }

    void generateTimetableData(List<ScheduledClass> scheduledClasses) {
        DefaultTableModel tableModel = new DefaultTableModel(CommonConstants.TIMETABLE_HEADER, 0);
        StringBuilder[][] classData = new StringBuilder[3][5];

        Map<Integer, List<ScheduledClass>> mappedClasses = scheduledClasses.stream()
                .collect(Collectors.groupingBy(scheduledClass -> scheduledClass.getTimeSlot().getSlotIndex()));

        mappedClasses.forEach((key, value) -> {
            StringBuilder sb = new StringBuilder();
            value.forEach(scheduledClass -> {
                sb.append(scheduledClass.getModule().getName()).append(NEW_LINE)
                        .append(scheduledClass.getProfessor().getName()).append(NEW_LINE)
                        .append(scheduledClass.getClassroom().getNumber());

                int row = key - 1;
                int col = scheduledClass.getTimeSlot().getDay() - 1;

                StringBuilder existing = classData[row][col];
                if (existing == null) {
                    classData[row][col] = sb;
                } else {
                    existing.append(NEW_LINE).append(sb);
                }
            });
        });

        for(int row = 0; row < 3; ++row) {
            tableModel.addRow(classData[row]);
        }

        finalTimetable.setModel(tableModel);

    }

    void displayTimetable() {
        frame = new JFrame("Final Timetable");
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
        timetableScrollPane = new JScrollPane();
        mainPanel.add(timetableScrollPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
                        0, false));
        finalTimetable = new JTable();
        timetableScrollPane.setViewportView(finalTimetable);
    }
}
