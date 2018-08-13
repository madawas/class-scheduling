package org.iit.genetics.main;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.iit.genetics.bean.Professor;
import org.iit.genetics.bean.TimeSlot;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class ConfigureDataUI {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel professorComboPanel;
    private JComboBox<String> selectProfessorCombo;
    private JPanel professorConfigPanel;
    private JScrollPane professorConfigPanelScrollPane;
    private JLabel selectProfessorLabel;
    private JComboBox<String> selectModuleCombo;
    private JPanel moduleComboPanel;
    private JLabel selectModuleLabel;
    private JPanel moduleConfigPanel;
    private JScrollPane moduleConfigPanelScrollPane;

    private Map<String, JPanel> profesorConfigurationMap;

    public ConfigureDataUI() {
        initComponents();
    }

    public void populateProfessors(List<Professor> professorList) {
        String[] professors = professorList.stream().map(Professor::getName).toArray(String[]::new);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(professors);
        this.selectProfessorCombo.setModel(model);
    }

    public void generateProfessorConfigurationMap(List<Professor> professorList, List<TimeSlot> timeSlots) {
        this.profesorConfigurationMap = new HashMap<>();

        for(Professor professor: professorList) {
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new GridLayoutManager(timeSlots.size()/5, 5, new Insets(0, 0, 0, 0), -1, -1));
        }
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane = new JTabbedPane();
        mainPanel.add(tabbedPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
                        new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Professors", panel1);
        professorComboPanel = new JPanel();
        professorComboPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(professorComboPanel,
                new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        selectProfessorCombo = new JComboBox<String>();
        professorComboPanel.add(selectProfessorCombo,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        selectProfessorLabel = new JLabel();
        selectProfessorLabel.setText("Select Professor");
        professorComboPanel.add(selectProfessorLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
                        false));
        final Spacer spacer1 = new Spacer();
        professorComboPanel.add(spacer1,
                new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        professorConfigPanel = new JPanel();
        professorConfigPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(professorConfigPanel,
                new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        professorConfigPanelScrollPane = new JScrollPane();
        professorConfigPanel.add(professorConfigPanelScrollPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
                        0, false));
        professorConfigPanelScrollPane.setBorder(BorderFactory.createTitledBorder("Configure Professor Availability"));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Modules", panel2);
        moduleComboPanel = new JPanel();
        moduleComboPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(moduleComboPanel,
                new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        selectModuleCombo = new JComboBox();
        moduleComboPanel.add(selectModuleCombo,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        selectModuleLabel = new JLabel();
        selectModuleLabel.setText("Select Module");
        moduleComboPanel.add(selectModuleLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1,
                        false));
        moduleConfigPanel = new JPanel();
        moduleConfigPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(moduleConfigPanel,
                new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        moduleConfigPanelScrollPane = new JScrollPane();
        moduleConfigPanel.add(moduleConfigPanelScrollPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null,
                        0, false));
        moduleConfigPanelScrollPane.setBorder(BorderFactory.createTitledBorder("Configure Module"));
    }
}
