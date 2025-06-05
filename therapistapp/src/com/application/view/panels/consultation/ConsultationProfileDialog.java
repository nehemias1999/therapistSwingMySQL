package com.application.view.panels.consultation;

import com.application.interfaces.IConsultationDialogListener;
import com.application.interfaces.IPanels;
import com.application.model.dto.ConsultationDTO;
import com.application.model.dto.PatientDTO;
import com.application.model.enumerations.ViewType;
import com.application.view.panels.patient.IPatientActionsEvent;
import com.application.view.panels.patient.PatientActionsCellEditor;
import com.application.view.panels.renderers.PatientActionsCellRender;
import com.application.view.panels.renderers.PatientProfileCellRender;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Component;
import java.awt.Frame;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ConsultationProfileDialog extends javax.swing.JDialog implements IPanels {
    private final IConsultationDialogListener listener;
    private final ViewType viewType;
    private final String consultationId;
    private ConsultationDTO consultationDTO;
    
    private boolean operationSuccess = false;

    public ConsultationProfileDialog(Frame owner, IConsultationDialogListener listener, ViewType viewType, String consultationId) {
        super(owner, "Thera Kairos", true);
        initComponents();
        this.listener = listener;
        this.viewType = viewType;
        this.consultationId = consultationId;
        
        if(viewType == ViewType.UPDATE) {
            jLabelMainTitle.setText("Modificar Consulta");
        }
        
        if(viewType == ViewType.VIEW) {
            jLabelMainTitle.setText("Ver Consulta");
        }
       
        datePickerConsultationDate.setCloseAfterSelected(true);
        datePickerConsultationDate.setEditor(jFormattedTextFieldConsultationDate);
        
        loadConsultationData();
        
        setStyle();
        initActionsData();

        setLocationRelativeTo(null);
        
    }
    
    private void initActionsData() {
        IPatientActionsEvent event = new IPatientActionsEvent() {
            @Override
            public void onView(String consultationId) {
                System.out.println("VER → consulta con ID = " + consultationId);
                //viewConsultation(consultationId);
            }
            @Override
            public void onEdit(String consultationId) {
                System.out.println("EDITAR → consulta con ID = " + consultationId);
                //updateConsultation(consultationId);
            }
            @Override
            public void onDelete(String consultationId) {
                System.out.println("BORRAR → consulta con ID = " + consultationId);
                //deleteConsultation(consultationId);
            }  
        };
        
        jTablePatients.getColumnModel().getColumn(0).setCellRenderer(new PatientProfileCellRender(jTablePatients));
        jTablePatients.getColumnModel().getColumn(1).setCellRenderer(new PatientActionsCellRender());
        jTablePatients.getColumnModel().getColumn(1).setCellEditor(new PatientActionsCellEditor(event));
    }
    
    private void setStyle() {
        TableColumnModel columnModel = jTablePatients.getColumnModel();
        
        jTablePatients.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "height:30;"
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "separatorColor:$TableHeader.background;"
                + "font:bold;");

        jTablePatients.putClientProperty(FlatClientProperties.STYLE, ""
                + "rowHeight:100;"
                + "showHorizontalLines:true;"
                + "intercellSpacing:0,1;"
                + "cellFocusColor:$TableHeader.hoverBackground;"
                + "selectionBackground:$TableHeader.hoverBackground;"
                + "selectionForeground:$Table.foreground;");

        jScrollPanePatiens.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:999;"
                + "trackInsets:3,3,3,3;"
                + "thumbInsets:3,3,3,3;"
                + "background:$Table.background;");
        
    }
    
    private void loadConsultationData() {
        
        consultationDTO = listener.getConsultationById(consultationId);
        
        datePickerConsultationDate.setSelectedDate(LocalDate.parse(consultationDTO.getConsultationDTODate()));
        jTextFieldStartTime.setText(consultationDTO.getConsultationDTOStartTime());
        jTextFieldEndTime.setText(consultationDTO.getConsultationDTOEndTime());
        jTextFieldAmount.setText(consultationDTO.getConsultationDTOAmount());

        loadConsultationPatients();
        
    }
        
    private void loadConsultationPatients() {
               
        DefaultTableModel tableModel = (DefaultTableModel) jTablePatients.getModel();
        
        if (jTablePatients.isEditing()) {
            jTablePatients.getCellEditor().stopCellEditing();
        }
        
        tableModel.setRowCount(0);

        List<PatientDTO> patientsDTO = listener.getPatientsByConsultationId(consultationId);

        if (patientsDTO == null) {
            this.showErrorMessage("Error: No se recibieron datos del servidor");
            return;
        }
        
        for (PatientDTO patientDTO : patientsDTO) {
            tableModel.addRow(new Object[]{
                patientDTO, 
                patientDTO.getPatientDTOId()
            });
        }
        
    }
    
    private void updateAction() throws IOException {
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
     * @param listener
     * @param viewType
     * @param consultationId
     * @return true si el guardado fue exitoso, false si canceló o hubo error.  
     */
    public static boolean showDialog(IConsultationDialogListener listener, ViewType viewType, String consultationId) {
        Frame ownerFrame = JOptionPane.getFrameForComponent((Component) listener);
        ConsultationProfileDialog dialog = new ConsultationProfileDialog(ownerFrame, listener, viewType, consultationId);
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

        datePickerConsultationDate = new raven.datetime.component.date.DatePicker();
        jPanelMainForm = new javax.swing.JPanel();
        jPanelMainTitle = new javax.swing.JPanel();
        jLabelMainTitle = new javax.swing.JLabel();
        jPanelActions = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jLabelPatients = new javax.swing.JLabel();
        jScrollPanePatiens = new javax.swing.JScrollPane();
        jTablePatients = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jFormattedTextFieldConsultationDate = new javax.swing.JFormattedTextField();
        jTextFieldAmount = new javax.swing.JTextField();
        jTextFieldStartTime = new javax.swing.JTextField();
        jTextFieldEndTime = new javax.swing.JTextField();
        jLabelConsultationDate = new javax.swing.JLabel();
        jLabelEndTime = new javax.swing.JLabel();
        jLabelStartTime = new javax.swing.JLabel();
        jLabelAmount = new javax.swing.JLabel();

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
            .addGroup(jPanelMainTitleLayout.createSequentialGroup()
                .addComponent(jLabelMainTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelMainTitleLayout.setVerticalGroup(
            jPanelMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainTitleLayout.createSequentialGroup()
                .addComponent(jLabelMainTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanelActionsLayout.setVerticalGroup(
            jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActionsLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelPatients.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabelPatients.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPatients.setText("Paciente/s:");
        jLabelPatients.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jTablePatients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Paciente", "Acciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPanePatiens.setViewportView(jTablePatients);
        if (jTablePatients.getColumnModel().getColumnCount() > 0) {
            jTablePatients.getColumnModel().getColumn(0).setResizable(false);
            jTablePatients.getColumnModel().getColumn(1).setResizable(false);
        }

        jFormattedTextFieldConsultationDate.setPreferredSize(new java.awt.Dimension(300, 30));

        jTextFieldAmount.setPreferredSize(new java.awt.Dimension(300, 30));

        jTextFieldStartTime.setToolTipText("Horario de inicio");
        jTextFieldStartTime.setPreferredSize(new java.awt.Dimension(300, 30));

        jTextFieldEndTime.setToolTipText("Horario de finalizacion");
        jTextFieldEndTime.setPreferredSize(new java.awt.Dimension(300, 30));

        jLabelConsultationDate.setText("Fecha de consulta:");

        jLabelEndTime.setText("Horario de fin:");

        jLabelStartTime.setText("Horario de inicio:");

        jLabelAmount.setText("Costo:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelConsultationDate)
                            .addComponent(jLabelStartTime))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldStartTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jFormattedTextFieldConsultationDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelAmount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelEndTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(130, 130, 130))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelConsultationDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFormattedTextFieldConsultationDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldStartTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelStartTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelEndTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldEndTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelMainFormLayout = new javax.swing.GroupLayout(jPanelMainForm);
        jPanelMainForm.setLayout(jPanelMainFormLayout);
        jPanelMainFormLayout.setHorizontalGroup(
            jPanelMainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMainTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelMainFormLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanelMainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelPatients)
                    .addComponent(jScrollPanePatiens)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
            .addComponent(jPanelActions, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );
        jPanelMainFormLayout.setVerticalGroup(
            jPanelMainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainFormLayout.createSequentialGroup()
                .addComponent(jPanelMainTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPatients, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPanePatiens, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelActions, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMainForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMainForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        try {
            updateAction();
        } catch (IOException ex) {
            Logger.getLogger(ConsultationProfileDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        cancelAction();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private raven.datetime.component.date.DatePicker datePickerConsultationDate;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JFormattedTextField jFormattedTextFieldConsultationDate;
    private javax.swing.JLabel jLabelAmount;
    private javax.swing.JLabel jLabelConsultationDate;
    private javax.swing.JLabel jLabelEndTime;
    private javax.swing.JLabel jLabelMainTitle;
    private javax.swing.JLabel jLabelPatients;
    private javax.swing.JLabel jLabelStartTime;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelActions;
    private javax.swing.JPanel jPanelMainForm;
    private javax.swing.JPanel jPanelMainTitle;
    private javax.swing.JScrollPane jScrollPanePatiens;
    private javax.swing.JTable jTablePatients;
    private javax.swing.JTextField jTextFieldAmount;
    private javax.swing.JTextField jTextFieldEndTime;
    private javax.swing.JTextField jTextFieldStartTime;
    // End of variables declaration//GEN-END:variables
}
