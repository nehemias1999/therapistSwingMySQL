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

    private PatientsFormController patientFormController;
    DefaultTableModel tableModel;
    
    public PatientsForm() {
        initComponents();
        setStyle();   
        
        jTableMain.getColumnModel().getColumn(0).setCellRenderer(new PatientProfileCellRender(jTableMain));
        
        initActionsData();
        
    }
    
    private void initActionsData() {
        IPatientActionsEvent event = new IPatientActionsEvent() {
            @Override
            public void onView(String patientId) {
                System.out.println("VER → paciente con ID = " + patientId);
                viewPatient(patientId);
            }
            @Override
            public void onEdit(String patientId) {
                System.out.println("EDITAR → paciente con ID = " + patientId);
                updatePatient(patientId);
            }
            @Override
            public void onDelete(String patientId) {
                System.out.println("BORRAR → paciente con ID = " + patientId);
                deletePatient(patientId);
            }  
        };
        
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
        this.patientFormController = controller;
        loadData();
    }

    public void loadData() {
        
        tableModel = (DefaultTableModel) jTableMain.getModel();
            
        if (jTableMain.isEditing()) {
            jTableMain.getCellEditor().stopCellEditing();
        }

        tableModel.setRowCount(0);

        List<PatientDTO> patientsDTO = patientFormController.getAllPatients();

        if (patientsDTO == null) {
            this.showErrorMessage("Error: No se recibieron datos del servidor");
            return;
        }

        if (patientsDTO.isEmpty()) {
            this.showInformationMessage("No se encontraron pacientes registrados");
            return;
        }

        for (PatientDTO patientDTO : patientsDTO) {
            tableModel.addRow(new Object[]{
                patientDTO, 
                patientDTO.getPatientDTOId()
            });
        }
        
    }

    private void searchData(String textToSearch) {
//        try {
//            DefaultTableModel model = (DefaultTableModel) table.getModel();
//            if (table.isEditing()) {
//                table.getCellEditor().stopCellEditing();
//            }
//            model.setRowCount(0);
//            List<ModelEmployee> list = service.search(search);
//            for (ModelEmployee d : list) {
//                model.addRow(d.toTableRow(table.getRowCount() + 1));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    
    public void insertPatient() {
        try {
            
            boolean saved = PatientFormDialog.showDialog(this, patientFormController, ViewType.INSERT, new PatientDTO());
            if (saved) {
                Toast.show(this, Toast.Type.SUCCESS, "Paciente agregado exitosamente");
            }
            initActionsData();
            loadData();
            
        } catch (Exception ex) {
            showErrorMessage("Error al mostrar el formulario: " + ex.getMessage());
        }
    }
    
    public void updatePatient(String patientId) {
        try {
            
            PatientDTO patientDTO = patientFormController.getPatientById(patientId);
            
            boolean updated = PatientFormDialog.showDialog(this, patientFormController, ViewType.UPDATE, patientDTO);
            if (updated) {
                Toast.show(this, Toast.Type.SUCCESS, "Paciente modificado exitosamente");
            }
            initActionsData();
            loadData();
            
        } catch (Exception ex) {
            showErrorMessage("Error al mostrar el formulario: " + ex.getMessage());
        }
    }
    
    public void deletePatient(String patientId) {
        try {
            
            boolean updated = confirmAction("¿Está seguro de eliminar este paciente?");  
            if (updated) {
                patientFormController.deletePatient(patientId);
                Toast.show(this, Toast.Type.SUCCESS, "Paciente eliminado exitosamente");
            }
            
            initActionsData();
            loadData();
            
        } catch (ValidationException ex) {
            Logger.getLogger(PatientsForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BusinessException ex) {
            Logger.getLogger(PatientsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void viewPatient(String patientId) {
        try {
            PatientDTO patientDTO = patientFormController.getPatientById(patientId);
            PatientProfileDialog.showDialog(this, patientFormController, patientDTO);
            initActionsData();
            loadData();
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
        jTextFieldSearcher.setToolTipText("buscar pacientes");
        jTextFieldSearcher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearcherKeyReleased(evt);
            }
        });

        jButtonAddPatient.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButtonAddPatient.setText("Agregar");
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1225, Short.MAX_VALUE)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelMainTitle)
                            .addComponent(jTextFieldSearcher, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAddPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jLabelMainTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jTextFieldSearcher, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonAddPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldSearcherKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearcherKeyReleased
        this.searchData(jTextFieldSearcher.getText().trim());
    }//GEN-LAST:event_jTextFieldSearcherKeyReleased

    private void jButtonAddPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddPatientActionPerformed
        this.insertPatient();
    }//GEN-LAST:event_jButtonAddPatientActionPerformed

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
