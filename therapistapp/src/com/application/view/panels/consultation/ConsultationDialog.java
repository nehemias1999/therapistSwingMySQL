package com.application.view.panels.consultation;

import com.application.controllers.panels.ConsultationsFormController;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.interfaces.IPanels;
import com.application.model.dto.CityDTO;
import com.application.model.dto.ConsultationDTO;
import com.application.model.dto.PatientDTO;
import com.application.model.enumerations.ConsultationStatus;
import com.application.model.enumerations.ViewType;
import java.awt.Component;
import java.awt.Frame;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConsultationDialog extends javax.swing.JDialog implements IPanels {
    private final ConsultationsFormController controller;
    private ViewType viewType;
    private ConsultationDTO consultationDTO;
    
    private boolean operationSuccess = false;

    public ConsultationDialog(Frame owner, ConsultationsFormController controller, ViewType viewtype, ConsultationDTO consultationDTO) {
        super(owner, "Agregar consulta", true);
        this.controller = controller;
        this.viewType = viewtype;
        if(viewtype == ViewType.UPDATE) {
            this.consultationDTO = consultationDTO;
        }
       
        initComponents();
        
        datePickerStartDate.setCloseAfterSelected(true);
        datePickerStartDate.setEditor(jFormattedTextStartDate);
        datePickerEndDate.setCloseAfterSelected(true);
        datePickerEndDate.setEditor(jFormattedTextEndDate);

        loadComboBoxPatient();
        
        setLocationRelativeTo(null);        
    }

    private void loadComboBoxPatient() {
        
        for (PatientDTO patient : controller.getAllPatients()) {

            jComboBoxPatients.addItem(patient);

        }
        
        jComboBoxPatients.clearSelectedItems();

    }
    
    private void saveAction() throws IOException {
//        try {
//            
//            ConsultationDTO consultationDTO = getConsultationDTO();
//            
//            if(viewType == ViewType.INSERT) {
//                controller.insertConsultation(consultationDTO);
//            }
//            if(viewType == ViewType.UPDATE) {
//                controller.updateConsultation(consultationDTO);
//            }
//           
//            operationSuccess = true;
//            dispose();
//
//        } catch (ValidationException ex) {
//            showErrorMessage(ex.getMessage());
//        } catch (BusinessException ex) {
//            showErrorMessage(ex.getMessage());
//        }
    }
    
//    private ConsultationDTO getConsultationDTO() {
//        
//        String consultationId = "";
//        if(viewType == ViewType.UPDATE) {
//            consultationId = consultationDTO.getConsultationDTOId();
//        }
//        
//        String consultationStartDate = datePickerStartDate.isDateSelected() ? Date.valueOf(datePickerStartDate.getSelectedDate()).toString() : null;
//        String consultationEndDate = datePickerEndDate.isDateSelected() ? Date.valueOf(datePickerEndDate.getSelectedDate()).toString() : null;
//        String consultationStatus = ConsultationStatus.SCHEDULED.toString();
//                       
//        return new ConsultationDTO(
//               consultationId,
//                consultationStartDate, 
//                consultationEndDate, 
//                consultationStatus
//        );
//    }
    
    private void cancelAction() {
        operationSuccess = false;
        dispose();
    }
    
    /**  
     * Llama al diálogo y bloquea hasta que se cierre.  
     * @param parent
     * @param controller
     * @param viewType
     * @param consultationDTO
     * @return true si el guardado fue exitoso, false si canceló o hubo error.  
     */
    public static boolean showDialog(Component parent, ConsultationsFormController controller, ViewType viewType, ConsultationDTO consultationDTO) {
        Frame owner = JOptionPane.getFrameForComponent(parent);
        ConsultationDialog dialog = new ConsultationDialog(owner, controller, viewType, consultationDTO);
        dialog.setVisible(true);  
        return dialog.operationSuccess;
    }
    
    @Override
    public void showInformationMessage(String message) {
        JOptionPane.showMessageDialog(
                this, 
                message, 
                "Informacion",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
                this, 
                message, 
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        datePickerStartDate = new raven.datetime.component.date.DatePicker();
        datePickerEndDate = new raven.datetime.component.date.DatePicker();
        jPanelMainForm = new javax.swing.JPanel();
        jPanelMainTitle = new javax.swing.JPanel();
        jLabelMainTitle = new javax.swing.JLabel();
        jLabelStarDate = new javax.swing.JLabel();
        jLabelEndDate = new javax.swing.JLabel();
        jLabelAmount = new javax.swing.JLabel();
        jPanelActions = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jTextFieldAmount = new javax.swing.JTextField();
        jFormattedTextStartDate = new javax.swing.JFormattedTextField();
        jFormattedTextEndDate = new javax.swing.JFormattedTextField();
        jComboBoxPatients = new com.application.view.panels.consultation.ComboBoxMultiSelection<>();
        jLabelPatients = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(512, 568));
        setResizable(false);

        jLabelMainTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelMainTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMainTitle.setText("Agregar Consulta");
        jLabelMainTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanelMainTitleLayout = new javax.swing.GroupLayout(jPanelMainTitle);
        jPanelMainTitle.setLayout(jPanelMainTitleLayout);
        jPanelMainTitleLayout.setHorizontalGroup(
            jPanelMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelMainTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelMainTitleLayout.setVerticalGroup(
            jPanelMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelMainTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        jLabelStarDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelStarDate.setText("Fecha de comienzo*:");
        jLabelStarDate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabelEndDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelEndDate.setText("Fecha de fin*:");
        jLabelEndDate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabelAmount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAmount.setText("Precio de la consulta por paciente*:");
        jLabelAmount.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanelActions.setMinimumSize(new java.awt.Dimension(195, 100));
        jPanelActions.setName(""); // NOI18N
        jPanelActions.setPreferredSize(new java.awt.Dimension(270, 100));

        jButtonCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonCancel.setText("Cancelar");
        jButtonCancel.setAlignmentX(0.5F);
        jButtonCancel.setPreferredSize(new java.awt.Dimension(90, 30));
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonAdd.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        jButtonAdd.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButtonAdd.setText("Agregar");
        jButtonAdd.setPreferredSize(new java.awt.Dimension(90, 30));
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelActionsLayout = new javax.swing.GroupLayout(jPanelActions);
        jPanelActions.setLayout(jPanelActionsLayout);
        jPanelActionsLayout.setHorizontalGroup(
            jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActionsLayout.createSequentialGroup()
                .addContainerGap(386, Short.MAX_VALUE)
                .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        jPanelActionsLayout.setVerticalGroup(
            jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActionsLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 33, Short.MAX_VALUE))
        );

        jTextFieldAmount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jFormattedTextStartDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jFormattedTextEndDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jComboBoxPatients.setMaximumSize(new java.awt.Dimension(64, 30));
        jComboBoxPatients.setMinimumSize(new java.awt.Dimension(64, 30));
        jComboBoxPatients.setPreferredSize(new java.awt.Dimension(64, 30));

        jLabelPatients.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPatients.setText("Paciente/s:");
        jLabelPatients.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanelMainFormLayout = new javax.swing.GroupLayout(jPanelMainForm);
        jPanelMainForm.setLayout(jPanelMainFormLayout);
        jPanelMainFormLayout.setHorizontalGroup(
            jPanelMainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMainTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainFormLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanelActions, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainFormLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelMainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelEndDate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelStarDate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelAmount, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelPatients, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelMainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .addComponent(jFormattedTextEndDate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jFormattedTextStartDate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBoxPatients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(150, 150, 150))
        );
        jPanelMainFormLayout.setVerticalGroup(
            jPanelMainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainFormLayout.createSequentialGroup()
                .addComponent(jPanelMainTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMainFormLayout.createSequentialGroup()
                        .addComponent(jFormattedTextStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFormattedTextEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxPatients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelMainFormLayout.createSequentialGroup()
                        .addComponent(jLabelStarDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelPatients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanelActions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMainForm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMainForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        try {
            saveAction();
        } catch (IOException ex) {
            Logger.getLogger(ConsultationDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        cancelAction();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private raven.datetime.component.date.DatePicker datePickerEndDate;
    private raven.datetime.component.date.DatePicker datePickerStartDate;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private com.application.view.panels.consultation.ComboBoxMultiSelection<PatientDTO> jComboBoxPatients;
    private javax.swing.JFormattedTextField jFormattedTextEndDate;
    private javax.swing.JFormattedTextField jFormattedTextStartDate;
    private javax.swing.JLabel jLabelAmount;
    private javax.swing.JLabel jLabelEndDate;
    private javax.swing.JLabel jLabelMainTitle;
    private javax.swing.JLabel jLabelPatients;
    private javax.swing.JLabel jLabelStarDate;
    private javax.swing.JPanel jPanelActions;
    private javax.swing.JPanel jPanelMainForm;
    private javax.swing.JPanel jPanelMainTitle;
    private javax.swing.JTextField jTextFieldAmount;
    // End of variables declaration//GEN-END:variables
}
