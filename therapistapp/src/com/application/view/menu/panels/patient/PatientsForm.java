/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.application.view.menu.panels.patient;

import com.application.interfaces.IPanels;
import com.application.controllers.panels.PatientsFormController;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import com.application.model.dto.PatientDTO;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.SimpleModalBorder;

public class PatientsForm extends javax.swing.JPanel implements IPanels {

    private PatientsFormController patientFormController;
    
    public PatientsForm() {
        initComponents();
        setStyle();
    }
        
    private void setStyle() {

        jTableMain.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "height:30;"
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "separatorColor:$TableHeader.background;"
                + "font:bold;");

        jTableMain.putClientProperty(FlatClientProperties.STYLE, ""
                + "rowHeight:70;"
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
        
        DefaultTableModel model = (DefaultTableModel) jTableMain.getModel();
            
        if (jTableMain.isEditing()) {
            jTableMain.getCellEditor().stopCellEditing();
        }

        model.setRowCount(0);

        List<PatientDTO> patients = patientFormController.getAllPatients();

        if (patients == null) {
            //this.showErrorMessage("Error: No se recibieron datos del servidor");
            return;
        }

        if (patients.isEmpty()) {
            this.showInformationMessage("No se encontraron pacientes registrados");
            return;
        }

        for (PatientDTO p : patients) {
            model.addRow(new Object[]{
                p.getPatientDTODNI(),
                2,
                p.getPatientDTOName(), 
                p.getPatientDTOLastName(), 
                1
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
    
    private void insertPatient() {
        try {
            AddPatientForm addPatientForm = new AddPatientForm(this.patientFormController);

            SimpleModalBorder.Option[] options = new SimpleModalBorder.Option[]{
                new SimpleModalBorder.Option("Cancelar", SimpleModalBorder.CANCEL_OPTION),
                new SimpleModalBorder.Option("Guardar", SimpleModalBorder.OK_OPTION)
            };

            if(UIManager.getLookAndFeel() == null) {
                FlatMacDarkLaf.setup();
            }

            ModalDialog.showModal(this, new SimpleModalBorder(addPatientForm, "Agregar paciente", options, (mc, i) -> {
                if (i == SimpleModalBorder.OK_OPTION) {
                    try {
                        patientFormController.insertPatient(addPatientForm.getData());

                        // Éxito - cierra y actualiza
                        Toast.show(this, Toast.Type.SUCCESS, "Paciente agregado exitosamente");
                        loadData();
                        mc.close();

                    } catch (Exception e) {
                        // Manejo unificado de errores
                        String errorMessage = "Error al guardar paciente: ";

                        if (e instanceof DataAccessException) {
                            errorMessage += "Problema de base de datos. " + e.getMessage();
                        } else if (e instanceof BusinessException) {
                            errorMessage += e.getMessage();
                        } else {
                            errorMessage += "Error inesperado. " + e.getMessage();
                        }

                        addPatientForm.showErrorMessage(errorMessage);
                        // No se llama a mc.close() ni return necesario
                        // ya que es el último bloque del try-catch
                    }
                }
            }));

        } catch (Exception e) {
            this.showErrorMessage("Error al mostrar el formulario: " + e.getMessage());
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

        jTableMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "DNI", "Imagen", "Nombre", "Apellido", "Acciones"
            }
        ));
        jScrollPane1.setViewportView(jTableMain);

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    
}
