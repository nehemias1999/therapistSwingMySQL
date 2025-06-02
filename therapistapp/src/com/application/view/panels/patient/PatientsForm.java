package com.application.view.panels.patient;

import com.application.view.panels.renderers.PatientProfileCellRender;
import com.application.view.panels.renderers.PatientActionsCellRender;
import com.application.interfaces.IPanels;
import com.application.controllers.panels.PatientsFormController;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.PatientDTO;
import com.application.model.enumerations.ViewType;
import com.formdev.flatlaf.FlatClientProperties;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import raven.modal.Toast;

public class PatientsForm extends javax.swing.JPanel implements IPanels {

    private PatientsFormController patientsFormController;
    DefaultTableModel tableModel;
    
    public PatientsForm() {
        initComponents();
        setStyle();
 
        initActionsData();
        
    }
    
    private void initActionsData() {
        IPatientActionsEvent event = new IPatientActionsEvent() {
            @Override
            public void onView(String patientId) {
                viewPatient(patientId);
            }
            @Override
            public void onEdit(String patientId) {
                updatePatient(patientId);
            }
            @Override
            public void onDelete(String patientId) {
                deletePatient(patientId);
            }  
        };
        
        jTableMain.getColumnModel().getColumn(0).setCellRenderer(new PatientProfileCellRender(jTableMain));
        jTableMain.getColumnModel().getColumn(1).setCellRenderer(new PatientActionsCellRender());
        jTableMain.getColumnModel().getColumn(1).setCellEditor(new PatientActionsCellEditor(event));
    }
        
    private void setStyle() {
        TableColumnModel columnModel = jTableMain.getColumnModel();
        
        columnModel.getColumn(0).setPreferredWidth(600);
        
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
        
    public void setController(PatientsFormController controller) {
        this.patientsFormController = controller;
        loadTableData();
    }

    public void loadTableData() {
        
        tableModel = (DefaultTableModel) jTableMain.getModel();
            
        if (jTableMain.isEditing()) {
            jTableMain.getCellEditor().stopCellEditing();
        }
        
        tableModel.setRowCount(0);

        List<PatientDTO> patientsDTO = patientsFormController.getAllPatients();

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

    private void searchData(String patientLastName) {
        
        tableModel = (DefaultTableModel) jTableMain.getModel();

        if (jTableMain.isEditing()) {
            jTableMain.getCellEditor().stopCellEditing();
        }
        
        tableModel.setRowCount(0);

        List<PatientDTO> patientsDTO = patientsFormController.getPatientsThatMatch(patientLastName);

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
    
    public void insertPatient() {
        try {
            
            boolean saved = PatientFormDialog.showDialog(this, patientsFormController, ViewType.INSERT, new PatientDTO());
            if (saved) {
                Toast.show(this, Toast.Type.SUCCESS, "Paciente agregado exitosamente");
            }
            initActionsData();
            loadTableData();
            
        } catch (Exception ex) {
            showErrorMessage("Error al mostrar el formulario: " + ex.getMessage());
        }
    }
    
    public void viewPatient(String patientId) {
        try {
            
            PatientDTO patientDTO = patientsFormController.getPatientById(patientId);
            PatientProfileDialog.showDialog(this, patientsFormController, patientDTO);
            initActionsData();
            loadTableData();
            
        } catch (ValidationException ex) {
            Logger.getLogger(PatientsForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BusinessException ex) {
            Logger.getLogger(PatientsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updatePatient(String patientId) {
        try {
            
            PatientDTO patientDTO = patientsFormController.getPatientById(patientId);
            
            boolean updated = PatientFormDialog.showDialog(this, patientsFormController, ViewType.UPDATE, patientDTO);
            if (updated) {
                Toast.show(this, Toast.Type.SUCCESS, "Paciente modificado exitosamente");
            }
            initActionsData();
            loadTableData();
            
        } catch (Exception ex) {
            showErrorMessage("Error al mostrar el formulario: " + ex.getMessage());
        }
    }
    
    public void deletePatient(String patientId) {
        try {
            
            boolean updated = confirmAction("¿Está seguro de eliminar este paciente?");  
            if (updated) {
                patientsFormController.deletePatient(patientId);
                Toast.show(this, Toast.Type.SUCCESS, "Paciente eliminado exitosamente");
            }
            
            initActionsData();
            loadTableData();
            
        } catch (ValidationException ex) {
            Logger.getLogger(PatientsForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BusinessException ex) {
            Logger.getLogger(PatientsForm.class.getName()).log(Level.SEVERE, null, ex);
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
        jTextFieldSearcher = new javax.swing.JTextField();
        jButtonAddPatient = new javax.swing.JButton();

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setFocusable(false);

        jTableMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
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
        jScrollPane1.setViewportView(jTableMain);
        if (jTableMain.getColumnModel().getColumnCount() > 0) {
            jTableMain.getColumnModel().getColumn(0).setResizable(false);
            jTableMain.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabelMainTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabelMainTitle.setText("Pacientes");

        jTextFieldSearcher.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextFieldSearcher.setText("buscar...");
        jTextFieldSearcher.setToolTipText("buscar paciente");
        jTextFieldSearcher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldSearcherMouseClicked(evt);
            }
        });
        jTextFieldSearcher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearcherKeyReleased(evt);
            }
        });

        jButtonAddPatient.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        jButtonAddPatient.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButtonAddPatient.setText("Agregar Paciente");
        jButtonAddPatient.setToolTipText("Agregar paciente");
        jButtonAddPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddPatientActionPerformed(evt);
            }
        });

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainLayout.createSequentialGroup()
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1235, Short.MAX_VALUE)
                            .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addComponent(jTextFieldSearcher, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonAddPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabelMainTitle)
                .addGap(18, 18, 18)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSearcher, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAddPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
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

    private void jTextFieldSearcherKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearcherKeyReleased
        this.searchData(jTextFieldSearcher.getText().trim());
    }//GEN-LAST:event_jTextFieldSearcherKeyReleased

    private void jButtonAddPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddPatientActionPerformed
        this.insertPatient();
    }//GEN-LAST:event_jButtonAddPatientActionPerformed

    private void jTextFieldSearcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldSearcherMouseClicked
        jTextFieldSearcher.setText("");
    }//GEN-LAST:event_jTextFieldSearcherMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddPatient;
    private javax.swing.JLabel jLabelMainTitle;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableMain;
    private javax.swing.JTextField jTextFieldSearcher;
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
