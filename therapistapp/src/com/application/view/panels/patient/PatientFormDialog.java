package com.application.view.panels.patient;

import com.application.controllers.panels.PatientsFormController;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.interfaces.IPanels;
import com.application.model.dto.CityDTO;
import com.application.model.dto.PatientDTO;
import com.application.model.enumerations.ViewType;
import java.awt.Component;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PatientFormDialog extends javax.swing.JDialog implements IPanels {
    private final PatientsFormController controller;
    private ViewType viewType;
    private PatientDTO patientDTO;
    
    private boolean operationSuccess = false;
    private String patientPhotoPath = "";
    
    public PatientFormDialog(Frame owner, PatientsFormController controller, ViewType viewtype, PatientDTO patientDTO) {
        super(owner, "Thera Kairos", true);
        this.controller = controller;
        this.viewType = viewtype;
        
        initComponents();
        
        if(viewtype == ViewType.UPDATE) {
            jLabelMainTitle.setText("Modificar paciente");
            this.patientDTO = patientDTO;
            this.patientPhotoPath = patientDTO.getPatientDTOPhotoPath();
        }
               
        datePicker.setCloseAfterSelected(true);
        datePicker.setEditor(jFormattedTextFieldBirthDate);
        
        initFormData();
        
        setLocationRelativeTo(null);        
    }
    
    private void initFormData() {
        if(viewType == ViewType.INSERT) {
            loadComboBoxCityIdData();
            jButtonRemovePhoto.setEnabled(false);
        }
        if(viewType == ViewType.UPDATE) {
            
            jTextFieldDNI.setText(patientDTO.getPatientDTODNI());
            jTextFieldName.setText(patientDTO.getPatientDTOName());
            jTextFieldLastName.setText(patientDTO.getPatientDTOLastName());
            datePicker.setSelectedDate(LocalDate.parse(patientDTO.getPatientDTOBirthDate()));
            jTextFieldOccupation.setText(patientDTO.getPatienDTOOccupation());
            jTextFieldPhone.setText(patientDTO.getPatientDTOPhone());
            jTextFieldEmail.setText(patientDTO.getPatientDTOEmail());
            loadComboBoxCityIdData();
            jTextFieldAddress.setText(patientDTO.getPatientDTOAddress());
            jTextFieldAddressNumber.setText(patientDTO.getPatientDTOAddressNumber());
            jTextFieldAddressFloor.setText(patientDTO.getPatientDTOAddressFloor());
            jTextFieldAddressDepartment.setText(patientDTO.getPatientDTOAddressDepartment());
            if(!patientDTO.getPatientDTOPhotoPath().isEmpty()) {
                jButtonRemovePhoto.setEnabled(true);
                jLabelPhotoName.setText(new File(patientPhotoPath).getName());
            } else {
                jButtonRemovePhoto.setEnabled(false);
                jLabelPhotoName.setText("Sin imagen seleccionada");
            }
                       
            jButtonAdd.setText("Cambiar");
        }
    }
    
    private void loadComboBoxCityIdData() {
        try {
            
            jComboBoxCityId.removeAllItems();
            
            jComboBoxCityId.addItem(new CityDTO("", "Seleccione...", ""));
            
            CityDTO selectedCity = null;
                        
            for (CityDTO city : controller.getAllCities()) {
                
                if((viewType == viewType.UPDATE) && (city.getCityId().equals(patientDTO.getCityId()))) {
                    selectedCity = city;
                }
                
                jComboBoxCityId.addItem(city);
            
            }
            if(viewType == ViewType.INSERT) {
                jComboBoxCityId.setSelectedIndex(0);
            }
            if(viewType == ViewType.UPDATE) {
                jComboBoxCityId.setSelectedItem(selectedCity);
            }
            
        } catch (Exception e) {
        }
    }
    
    private PatientDTO getPatientDTO() {
        
        String patientId = "";
        if(viewType == ViewType.UPDATE) {
            patientId = patientDTO.getPatientDTOId();
        }
               
        String patientDNI = jTextFieldDNI .getText().trim();
        String patientName = jTextFieldName.getText().trim();
        String patientLastName = jTextFieldLastName.getText().trim();
        String patientBirthDate = datePicker.isDateSelected() ? Date.valueOf(datePicker.getSelectedDate()).toString() : null;
        String patientOccupation = jTextFieldOccupation.getText().trim();
        String patientPhone = jTextFieldPhone.getText().trim();
        String patientEmail = jTextFieldEmail.getText().trim();
        CityDTO cityDTO = (CityDTO) jComboBoxCityId.getSelectedItem();
        String patientCityId = cityDTO.getCityId();
        String patientAddress = jTextFieldAddress.getText().trim();
        String patientAddressNumber = jTextFieldAddressNumber.getText().trim();
        String patientAddressFloor = jTextFieldAddressFloor.getText().trim();
        String patientAddressDepartment = jTextFieldAddressDepartment.getText().trim();
        
        return new PatientDTO(
                patientId,
                patientDNI, 
                patientName, 
                patientLastName, 
                patientBirthDate,
                patientOccupation,
                patientPhone, 
                patientEmail, 
                patientCityId, 
                patientAddress,
                patientAddressNumber,
                patientAddressFloor,
                patientAddressDepartment,
                patientPhotoPath
        );
    }
    
    private void addPhoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar foto del paciente");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
       
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter(
            "Imágenes (jpg, png, gif)", "jpg", "jpeg", "png", "gif"
        ));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            patientPhotoPath = file.getAbsolutePath().trim();       
            jLabelPhotoName.setText(file.getName());
            jButtonAddPhoto.setEnabled(false);
            jButtonRemovePhoto.setEnabled(true);
        }
    }
    
    private void deletePhoto() {
        patientPhotoPath = "";
        jLabelPhotoName.setText("Sin imagen seleccionada");
        jButtonAddPhoto.setEnabled(true);
        jButtonRemovePhoto.setEnabled(false);
    }
    
    private void saveAction() throws IOException {
        try {
            
            PatientDTO patientDTO = getPatientDTO();
            
            if(viewType == ViewType.INSERT) {
                controller.insertPatient(patientDTO);
            }
            if(viewType == ViewType.UPDATE) {
                controller.updatePatient(patientDTO);
            }
           
            operationSuccess = true;
            dispose();

        } catch (ValidationException ex) {
            showErrorMessage(ex.getMessage());
        } catch (BusinessException ex) {
            showErrorMessage(ex.getMessage());
        }
    }
    
    private void cancelAction() {
        operationSuccess = false;
        dispose();
    }
    
    /**  
     * Llama al diálogo y bloquea hasta que se cierre.  
     * @param parent
     * @param controller
     * @param viewType
     * @param patientDTO
     * @return true si el guardado fue exitoso, false si canceló o hubo error.  
     */
    public static boolean showDialog(Component parent, PatientsFormController controller, ViewType viewType, PatientDTO patientDTO) {
        Frame owner = JOptionPane.getFrameForComponent(parent);
        PatientFormDialog dialog = new PatientFormDialog(owner, controller, viewType, patientDTO);
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

        datePicker = new raven.datetime.component.date.DatePicker();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabelMainTitle = new javax.swing.JLabel();
        jTextFieldDNI = new javax.swing.JTextField();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldLastName = new javax.swing.JTextField();
        jFormattedTextFieldBirthDate = new javax.swing.JFormattedTextField();
        jTextFieldOccupation = new javax.swing.JTextField();
        jTextFieldPhone = new javax.swing.JTextField();
        jTextFieldEmail = new javax.swing.JTextField();
        jComboBoxCityId = new javax.swing.JComboBox<>();
        jTextFieldAddress = new javax.swing.JTextField();
        jTextFieldAddressNumber = new javax.swing.JTextField();
        jLabelAddressFloor = new javax.swing.JLabel();
        jTextFieldAddressFloor = new javax.swing.JTextField();
        jLabelAddressDepartment = new javax.swing.JLabel();
        jTextFieldAddressDepartment = new javax.swing.JTextField();
        jPanelPhoto = new javax.swing.JPanel();
        jButtonAddPhoto = new javax.swing.JButton();
        jButtonRemovePhoto = new javax.swing.JButton();
        jLabelPhotoName = new javax.swing.JLabel();
        jLabelDNI = new javax.swing.JLabel();
        jLabelName = new javax.swing.JLabel();
        jLabelLastName = new javax.swing.JLabel();
        jLabelBirthDate = new javax.swing.JLabel();
        jLabelOccupation = new javax.swing.JLabel();
        jLabelPhone = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jLabelCityId = new javax.swing.JLabel();
        jLabelAddress = new javax.swing.JLabel();
        jLabelAddressNumber = new javax.swing.JLabel();
        jPanelActions = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(512, 568));
        setResizable(false);

        jLabelMainTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelMainTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMainTitle.setText("Agregar Paciente");
        jLabelMainTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelMainTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelMainTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        jTextFieldDNI.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jTextFieldName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jTextFieldLastName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jFormattedTextFieldBirthDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jTextFieldOccupation.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jTextFieldPhone.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jTextFieldEmail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jComboBoxCityId.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jTextFieldAddress.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jTextFieldAddressNumber.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabelAddressFloor.setText("Piso:");

        jTextFieldAddressFloor.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabelAddressDepartment.setText("Dept:");

        jTextFieldAddressDepartment.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jPanelPhoto.setMinimumSize(new java.awt.Dimension(244, 67));

        jButtonAddPhoto.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButtonAddPhoto.setText("Agregar");
        jButtonAddPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddPhotoActionPerformed(evt);
            }
        });

        jButtonRemovePhoto.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Red"));
        jButtonRemovePhoto.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButtonRemovePhoto.setText("Quitar");
        jButtonRemovePhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemovePhotoActionPerformed(evt);
            }
        });

        jLabelPhotoName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabelPhotoName.setText("Sin imagen seleccionada");

        javax.swing.GroupLayout jPanelPhotoLayout = new javax.swing.GroupLayout(jPanelPhoto);
        jPanelPhoto.setLayout(jPanelPhotoLayout);
        jPanelPhotoLayout.setHorizontalGroup(
            jPanelPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPhotoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPhotoName)
                    .addGroup(jPanelPhotoLayout.createSequentialGroup()
                        .addComponent(jButtonAddPhoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemovePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelPhotoLayout.setVerticalGroup(
            jPanelPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPhotoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPhotoName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanelPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRemovePhoto)
                    .addComponent(jButtonAddPhoto))
                .addContainerGap())
        );

        jLabelDNI.setText("Documento*:");

        jLabelName.setText("Nombre*:");

        jLabelLastName.setText("Apellido*:");

        jLabelBirthDate.setText("Fecha de nacimiento*:");

        jLabelOccupation.setText("Ocupacion*:");

        jLabelPhone.setText("Celular*:");

        jLabelEmail.setText("Email*:");

        jLabelCityId.setText("Ciudad*:");

        jLabelAddress.setText("Direccion*:");

        jLabelAddressNumber.setText("Numero*:");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelPhone)
                    .addComponent(jLabelEmail)
                    .addComponent(jLabelCityId)
                    .addComponent(jLabelAddress)
                    .addComponent(jLabelAddressNumber)
                    .addComponent(jLabelName)
                    .addComponent(jLabelDNI)
                    .addComponent(jLabelLastName)
                    .addComponent(jLabelBirthDate)
                    .addComponent(jLabelOccupation))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldAddressNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelAddressFloor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldAddressFloor, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelAddressDepartment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldAddressDepartment))
                    .addComponent(jTextFieldPhone)
                    .addComponent(jTextFieldAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldDNI, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldLastName)
                    .addComponent(jFormattedTextFieldBirthDate)
                    .addComponent(jTextFieldOccupation)
                    .addComponent(jTextFieldEmail)
                    .addComponent(jComboBoxCityId, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(150, 150, 150))
            .addComponent(jPanelActions, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDNI))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLastName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBirthDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldOccupation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOccupation))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPhone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCityId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCityId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldAddressDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelAddressDepartment)
                        .addComponent(jTextFieldAddressFloor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelAddressFloor))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldAddressNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelAddressNumber)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelActions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddPhotoActionPerformed
        addPhoto();
    }//GEN-LAST:event_jButtonAddPhotoActionPerformed

    private void jButtonRemovePhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemovePhotoActionPerformed
        deletePhoto();
    }//GEN-LAST:event_jButtonRemovePhotoActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        cancelAction();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        try {
            saveAction();
        } catch (IOException ex) {
            Logger.getLogger(PatientFormDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private raven.datetime.component.date.DatePicker datePicker;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonAddPhoto;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonRemovePhoto;
    private javax.swing.JComboBox<CityDTO> jComboBoxCityId;
    private javax.swing.JFormattedTextField jFormattedTextFieldBirthDate;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelAddressDepartment;
    private javax.swing.JLabel jLabelAddressFloor;
    private javax.swing.JLabel jLabelAddressNumber;
    private javax.swing.JLabel jLabelBirthDate;
    private javax.swing.JLabel jLabelCityId;
    private javax.swing.JLabel jLabelDNI;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelLastName;
    private javax.swing.JLabel jLabelMainTitle;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelOccupation;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JLabel jLabelPhotoName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelActions;
    private javax.swing.JPanel jPanelPhoto;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldAddressDepartment;
    private javax.swing.JTextField jTextFieldAddressFloor;
    private javax.swing.JTextField jTextFieldAddressNumber;
    private javax.swing.JTextField jTextFieldDNI;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldLastName;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldOccupation;
    private javax.swing.JTextField jTextFieldPhone;
    // End of variables declaration//GEN-END:variables
}
