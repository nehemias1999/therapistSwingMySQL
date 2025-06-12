package com.application.view.panels.renderers;

import com.application.model.dto.PatientDTO;
import com.application.view.panels.consultation.dialog.ConsultationPatientActionsCell;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ConsultationPatientActionsCellRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object object, boolean isSelected, boolean hasFocus, int row, int column) {
        PatientDTO patient = (PatientDTO) object;
        ConsultationPatientActionsCell action = new ConsultationPatientActionsCell();
        action.setIsPaid(patient.isPaid());
        action.setBackground(jtable.getBackground());
        return action;
    }
}
