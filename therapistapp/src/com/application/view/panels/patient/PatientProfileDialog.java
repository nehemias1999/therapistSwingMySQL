package com.application.view.panels.patient;

import com.application.controllers.panels.PatientsFormController;
import com.application.model.dto.PatientDTO;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import javax.print.CancelablePrintJob;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class PatientProfileDialog extends javax.swing.JDialog {
    private final PatientsFormController controller;
    private PatientDTO patientDTO;

    /**
     * Creates new form PatientProfileDialog
     */
    public PatientProfileDialog(java.awt.Frame parent, PatientsFormController controller, PatientDTO patientDTO) {
        super(parent, true);
        initComponents();
        this.controller = controller;
        this.patientDTO = patientDTO;
        
        loadPatientData();
        
        setLocationRelativeTo(null);        
    }
    
    private void loadPatientData() {
        loadPatientPhoto(patientDTO.getPatientDTOIcon());
        jLabelCompleteName.setText(patientDTO.getPatientDTOLastName()+ ", " + patientDTO.getPatientDTOName());
        jLabelDNI.setText("DNI: " + patientDTO.getPatientDTODNI());
        jLabelBirthDate.setText("Fecha de nacimiento: " + patientDTO.getPatientDTOBirthDate() + " (" + patientDTO.getPatientDTOActualAge() + ")");
        jLabelOccupation.setText("Ocupacion: " + patientDTO.getPatienDTOOccupation());
        jLabelPhone.setText("Celular: " + patientDTO.getPatientDTOPhone());
        jLabelEmail.setText("Email: " + patientDTO.getPatientDTOEmail());
        jLabelCity.setText("Ciudad: " + getCityNameById(patientDTO.getCityId()));
        jLabelAddress.setText("Direccion: " + patientDTO.getPatientDTOAddress());
        jLabelAddressNumber.setText("Numero: " + patientDTO.getPatientDTOAddressNumber());
        jLabelAddressFloor.setText("Piso: " + patientDTO.getPatientDTOAddressFloor());
        jLabelAddressDepartment.setText("Dept: " + patientDTO.getPatientDTOAddressDepartment());
    }

    private void loadPatientPhoto(Icon icon) {
        if (icon instanceof ImageIcon) {
            ImageIcon imageIcon = (ImageIcon) icon;
            Image scaledImage = imageIcon.getImage().getScaledInstance(
                jPanelPhoto.getWidth(), jPanelPhoto.getHeight(), Image.SCALE_SMOOTH);
            jLabelPhoto.setText("");
            jLabelPhoto.setIcon(new ImageIcon(scaledImage));
        } else {
            jLabelPhoto.setText("Sin imagen");
            System.err.println("Icono no válido: no es una instancia de ImageIcon.");
        }
    }
    
    private String getCityNameById(String cityId) {
       return controller.getCityNameById(cityId);
    }

    private void BackAction() {
        dispose();
    }
    
    /**  
     * Llama al diálogo y bloquea hasta que se cierre.  
     * @param parent
     * @param controller
     * @param patientDTO 
     */
    public static void showDialog(Component parent, PatientsFormController controller, PatientDTO patientDTO) {
        Frame owner = JOptionPane.getFrameForComponent(parent);
        PatientProfileDialog dialog = new PatientProfileDialog(owner, controller, patientDTO);
        dialog.setVisible(true);
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
        jPanelMainTitle = new javax.swing.JPanel();
        jLabelMainTitle = new javax.swing.JLabel();
        jPanelActions = new javax.swing.JPanel();
        jButtonBack = new javax.swing.JButton();
        jPanelPhoto = new javax.swing.JPanel();
        jLabelPhoto = new javax.swing.JLabel();
        jLabelCompleteName = new javax.swing.JLabel();
        jLabelDNI = new javax.swing.JLabel();
        jLabelOccupation = new javax.swing.JLabel();
        jLabelPhone = new javax.swing.JLabel();
        jLabelBirthDate = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jLabelCity = new javax.swing.JLabel();
        jLabelAddress = new javax.swing.JLabel();
        jLabelAddressNumber = new javax.swing.JLabel();
        jLabelAddressFloor = new javax.swing.JLabel();
        jLabelAddressDepartment = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(650, 520));
        setModal(true);
        setPreferredSize(new java.awt.Dimension(650, 520));
        setResizable(false);

        jPanelMain.setMinimumSize(new java.awt.Dimension(650, 520));
        jPanelMain.setName(""); // NOI18N
        jPanelMain.setPreferredSize(new java.awt.Dimension(650, 520));

        jPanelMainTitle.setMinimumSize(new java.awt.Dimension(650, 80));
        jPanelMainTitle.setName(""); // NOI18N
        jPanelMainTitle.setPreferredSize(new java.awt.Dimension(650, 80));

        jLabelMainTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelMainTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMainTitle.setText("Perfil del Paciente");
        jLabelMainTitle.setToolTipText("");
        jLabelMainTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanelMainTitleLayout = new javax.swing.GroupLayout(jPanelMainTitle);
        jPanelMainTitle.setLayout(jPanelMainTitleLayout);
        jPanelMainTitleLayout.setHorizontalGroup(
            jPanelMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelMainTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelMainTitleLayout.setVerticalGroup(
            jPanelMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelMainTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        jPanelActions.setMinimumSize(new java.awt.Dimension(0, 100));
        jPanelActions.setPreferredSize(new java.awt.Dimension(140, 100));

        jButtonBack.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButtonBack.setText("Volver");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelActionsLayout = new javax.swing.GroupLayout(jPanelActions);
        jPanelActions.setLayout(jPanelActionsLayout);
        jPanelActionsLayout.setHorizontalGroup(
            jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelActionsLayout.createSequentialGroup()
                .addContainerGap(516, Short.MAX_VALUE)
                .addComponent(jButtonBack)
                .addGap(59, 59, 59))
        );
        jPanelActionsLayout.setVerticalGroup(
            jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActionsLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jButtonBack)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanelPhoto.setBackground(new java.awt.Color(255, 255, 255));
        jPanelPhoto.setMinimumSize(new java.awt.Dimension(100, 100));

        jLabelPhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPhoto.setText("Photo");
        jLabelPhoto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelPhoto.setMaximumSize(new java.awt.Dimension(100, 100));
        jLabelPhoto.setMinimumSize(new java.awt.Dimension(100, 100));
        jLabelPhoto.setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout jPanelPhotoLayout = new javax.swing.GroupLayout(jPanelPhoto);
        jPanelPhoto.setLayout(jPanelPhotoLayout);
        jPanelPhotoLayout.setHorizontalGroup(
            jPanelPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelPhoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanelPhotoLayout.setVerticalGroup(
            jPanelPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelPhoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabelCompleteName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelCompleteName.setText("Nombre Completo");

        jLabelDNI.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabelDNI.setText("Documento");

        jLabelOccupation.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabelOccupation.setText("Ocupacion");

        jLabelPhone.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabelPhone.setText("Celular");

        jLabelBirthDate.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabelBirthDate.setText("Fecha de Nacimiento y Edad Actual");

        jLabelEmail.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabelEmail.setText("Email");

        jLabelCity.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabelCity.setText("Ciudad");

        jLabelAddress.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabelAddress.setText("Direccion");

        jLabelAddressNumber.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabelAddressNumber.setText("Numero");

        jLabelAddressFloor.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabelAddressFloor.setText("Piso");

        jLabelAddressDepartment.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabelAddressDepartment.setText("Departamento");

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMainTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jPanelPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addComponent(jLabelDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelBirthDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabelCompleteName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelOccupation, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabelEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jLabelAddressNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelAddressFloor, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelAddressDepartment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanelActions, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addComponent(jPanelMainTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jLabelCompleteName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelDNI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelOccupation))
                    .addComponent(jPanelPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabelPhone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelCity)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelAddress)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAddressNumber)
                    .addComponent(jLabelAddressFloor)
                    .addComponent(jLabelAddressDepartment))
                .addGap(18, 18, 18)
                .addComponent(jPanelActions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        BackAction();
    }//GEN-LAST:event_jButtonBackActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelAddressDepartment;
    private javax.swing.JLabel jLabelAddressFloor;
    private javax.swing.JLabel jLabelAddressNumber;
    private javax.swing.JLabel jLabelBirthDate;
    private javax.swing.JLabel jLabelCity;
    private javax.swing.JLabel jLabelCompleteName;
    private javax.swing.JLabel jLabelDNI;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelMainTitle;
    private javax.swing.JLabel jLabelOccupation;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JLabel jLabelPhoto;
    private javax.swing.JPanel jPanelActions;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelMainTitle;
    private javax.swing.JPanel jPanelPhoto;
    // End of variables declaration//GEN-END:variables
}
