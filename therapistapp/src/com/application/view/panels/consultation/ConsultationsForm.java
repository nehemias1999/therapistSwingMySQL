package com.application.view.panels.consultation;

import com.application.view.panels.consultation.calendar.ModelDate;
import com.application.interfaces.ICalendarSelectedListener;
import com.application.controllers.panels.ConsultationsFormController;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.interfaces.IConsultationPatientActionsEvent;
import com.application.interfaces.IPanels;
import com.application.model.dto.ConsultationDTO;
import com.application.model.dto.PatientDTO;
import com.application.model.enumerations.ViewType;
import com.application.view.panels.renderers.ConsultationPatientActionsCellRender;
import com.application.view.panels.renderers.ConsultationPatientProfileCellRender;
import com.application.view.panels.renderers.ConsultationPatientTimeCellRender;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import raven.modal.Toast;

public class ConsultationsForm extends javax.swing.JPanel implements IPanels {

    private ConsultationsFormController consultationsFormController;
    DefaultTableModel tableModel;
    
    ModelDate actualSelectedDate = null;
    
    public ConsultationsForm() {
        initComponents();
        setStyle();
        
        this.actualSelectedDate = new ModelDate();
        
        calendar.addCalendarSelectedListener(new ICalendarSelectedListener() {
            @Override
            public void selected(MouseEvent evt, ModelDate date) {
                actualSelectedDate = date;
                jLabelSelectedDate.setText(String.valueOf(date.getDay()) + "/" + date.getMonth()+ "/" + date.getYear());
                loadTableData(date);
            }    
        });
        
        initActionsData();
        
    }
    
    private void initActionsData() {
        IConsultationPatientActionsEvent event = new IConsultationPatientActionsEvent() {
            @Override
            public void onView(String consultationId) {
                System.out.println("VER → consulta con ID = " + consultationId);
                viewConsultation(consultationId);
            }
            @Override
            public void onEdit(String consultationId) {
                System.out.println("EDITAR → consulta con ID = " + consultationId);
                updateConsultation(consultationId);
            }
            @Override
            public void onDelete(String consultationId) {
                System.out.println("BORRAR → consulta con ID = " + consultationId);
                deleteConsultation(consultationId);
            }  
        };
        
        jTableMain.getColumnModel().getColumn(0).setCellRenderer(new ConsultationPatientTimeCellRender(jTableMain));
        jTableMain.getColumnModel().getColumn(1).setCellRenderer(new ConsultationPatientProfileCellRender(jTableMain));
        jTableMain.getColumnModel().getColumn(2).setCellRenderer(new ConsultationPatientActionsCellRender());
        jTableMain.getColumnModel().getColumn(2).setCellEditor(new ConsultationPatientActionsCellEditor(event));
    }
            
    private void setStyle() {
        TableColumnModel columnModel = jTableMain.getColumnModel();
        
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(200);
        
        jTableMain.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "height:30;"
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "separatorColor:$TableHeader.background;"
                + "font:bold;");

        jTableMain.putClientProperty(FlatClientProperties.STYLE, ""
                + "rowHeight:100;"
                + "showHorizontalLines:true;"
                + "intercellSpacing:0,1;"
                + "cellFocusColor:$TableHeader.hoverBackground;"
                + "selectionBackground:$TableHeader.hoverBackground;"
                + "selectionForeground:$Table.foreground;");

        jScrollPane1.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:999;"
                + "trackInsets:3,3,3,3;"
                + "thumbInsets:3,3,3,3;"
                + "background:$Table.background;");
        
    }
        
    public void setController(ConsultationsFormController controller) {
        this.consultationsFormController = controller;
        loadTableData(actualSelectedDate);
    }
    
    public void loadTableData(ModelDate date) {
        
        tableModel = (DefaultTableModel) jTableMain.getModel();
            
        if (jTableMain.isEditing()) {
            jTableMain.getCellEditor().stopCellEditing();
        }

        tableModel.setRowCount(0);

        List<ConsultationDTO> consultationsDTO = consultationsFormController.getConsultationsByDate(date.toFormattedDate());

        if (consultationsDTO == null) {
            this.showErrorMessage("Error: No se recibieron datos del servidor");
            return;
        }
        
        if(!consultationsDTO.isEmpty()) {
            for (ConsultationDTO consultationDTO : consultationsDTO) {
            
                List<PatientDTO> patientsDTO = consultationsFormController.getPatientsByConsultationId(consultationDTO.getConsultationDTOId());
                PatientDTO patient = patientsDTO.get(0);
                
                tableModel.addRow(new Object[]{
                    consultationDTO.getConsultationDTOStartTime(),
                    patient, 
                    consultationDTO.getConsultationDTOId()
                });
            }
        }
    }
        
    public void insertConsultation() {
        try {
            
            boolean saved = ConsultationFormDialog.showDialog(this, consultationsFormController, ViewType.INSERT, new ConsultationDTO());
            if (saved) {
                Toast.show(this, Toast.Type.SUCCESS, "Consulta agregada exitosamente");
            }
            initActionsData();
            loadTableData(actualSelectedDate);
            
        } catch (Exception ex) {
            showErrorMessage("Error al mostrar el formulario: " + ex.getMessage());
        }
    }
        
    public void insertPatient() {
//        try {
//            
//            boolean saved = PatientFormDialog.showDialog(this, patientFormController, ViewType.INSERT, new PatientDTO());
//            if (saved) {
//                Toast.show(this, Toast.Type.SUCCESS, "Paciente agregado exitosamente");
//            }
//            
//        } catch (Exception ex) {
//            showErrorMessage("Error al mostrar el formulario: " + ex.getMessage());
//        }
    }
    
    public void viewConsultation(String consultationId) {
//        try {
//            
//            ConsultationDTO consultationDTO = consultationsFormController.getConsultationById(consultationId);
//            ConsultationProfileDialog.showDialog(this, consultationsFormController, consultationDTO);
//            initActionsData();
//            loadTableData(actualSelectedDate);
//            
//        } catch (ValidationException ex) {
//            Logger.getLogger(ConsultationsForm.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (BusinessException ex) {
//            Logger.getLogger(ConsultationsForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    public void updateConsultation(String consultationId) {
//        try {
//            
//            ConsultationDTO consultationDTO = consultationsFormController.getConsultationById(consultationId);
//            
//            boolean updated = ConsultationFormDialog.showDialog(this, consultationsFormController, ViewType.UPDATE, consultationDTO);
//            if (updated) {
//                Toast.show(this, Toast.Type.SUCCESS, "Consulta modificada exitosamente");
//            }
//            initActionsData();
//            loadTableData(actualSelectedDate);
//            
//        } catch (Exception ex) {
//            showErrorMessage("Error al mostrar el formulario: " + ex.getMessage());
//        }
    }
    
    public void deleteConsultation(String consultationId) {
        try {
            
            boolean updated = confirmAction("¿Está seguro de eliminar esta consulta?");  
            if (updated) {
                consultationsFormController.deleteConsultation(consultationId);
                Toast.show(this, Toast.Type.SUCCESS, "Consulta eliminada exitosamente");
            }
            
            initActionsData();
            loadTableData(actualSelectedDate);
            
        } catch (ValidationException ex) {
            Logger.getLogger(ConsultationsForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BusinessException ex) {
            Logger.getLogger(ConsultationsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMain = new javax.swing.JTable();
        jLabelMainTitle = new javax.swing.JLabel();
        jButtonAddConsultation = new javax.swing.JButton();
        calendar = new com.application.view.panels.consultation.calendar.Calendar();
        jButtonAddPatient = new javax.swing.JButton();
        jLabelSelectedDate = new javax.swing.JLabel();

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setFocusable(false);

        jTableMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Horario", "Paciente/s", "Acciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableMain);
        if (jTableMain.getColumnModel().getColumnCount() > 0) {
            jTableMain.getColumnModel().getColumn(0).setResizable(false);
            jTableMain.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTableMain.getColumnModel().getColumn(1).setResizable(false);
            jTableMain.getColumnModel().getColumn(2).setResizable(false);
            jTableMain.getColumnModel().getColumn(2).setPreferredWidth(200);
        }

        jLabelMainTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabelMainTitle.setText("Consultas");

        jButtonAddConsultation.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        jButtonAddConsultation.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButtonAddConsultation.setText("Agregar Consulta");
        jButtonAddConsultation.setToolTipText("Agregar paciente");
        jButtonAddConsultation.setMaximumSize(new java.awt.Dimension(380, 50));
        jButtonAddConsultation.setMinimumSize(new java.awt.Dimension(380, 50));
        jButtonAddConsultation.setPreferredSize(new java.awt.Dimension(380, 50));
        jButtonAddConsultation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddConsultationActionPerformed(evt);
            }
        });

        jButtonAddPatient.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButtonAddPatient.setLabel("Agregar paciente");
        jButtonAddPatient.setMaximumSize(new java.awt.Dimension(120, 28));
        jButtonAddPatient.setMinimumSize(new java.awt.Dimension(120, 28));
        jButtonAddPatient.setPreferredSize(new java.awt.Dimension(350, 35));
        jButtonAddPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddPatientActionPerformed(evt);
            }
        });

        jLabelSelectedDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelSelectedDate.setText("Dia elegido");

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jLabelMainTitle)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(calendar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAddConsultation, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelMainLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jButtonAddPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelSelectedDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
                        .addGap(24, 24, 24))))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabelMainTitle)
                .addGap(18, 18, 18)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jLabelSelectedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(calendar, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonAddConsultation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonAddPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddConsultationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddConsultationActionPerformed
      insertConsultation();
    }//GEN-LAST:event_jButtonAddConsultationActionPerformed

    private void jButtonAddPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddPatientActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAddPatientActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.application.view.panels.consultation.calendar.Calendar calendar;
    private javax.swing.JButton jButtonAddConsultation;
    private javax.swing.JButton jButtonAddPatient;
    private javax.swing.JLabel jLabelMainTitle;
    private javax.swing.JLabel jLabelSelectedDate;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableMain;
    // End of variables declaration//GEN-END:variables

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
    
    public boolean confirmAction(String message) {
        int option = JOptionPane.showConfirmDialog(
            this,                   
            message,                   
            "Confirmar acción",        
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE
        );
        return option == JOptionPane.YES_OPTION;
    }
}
