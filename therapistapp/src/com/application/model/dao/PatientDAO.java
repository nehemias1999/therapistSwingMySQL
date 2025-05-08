package com.application.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.application.exceptions.runtimeExceptions.dataAccessException.ConstraintViolationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import com.application.exceptions.runtimeExceptions.dataAccessException.EntityNotFoundException;
import com.application.model.entities.Patient;

public class PatientDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/therapist_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    private static final String SELECT_ALL_PATIENT =
            "SELECT * FROM tbl_patient ORDER BY patient_dni";
    
    private static final String SELECT_PATIENT_BY_DNI =
            "SELECT * FROM tbl_patient WHERE patient_dni = ?";

    private static final String INSERT_PATIENT =
            "INSERT INTO tbl_patient (patient_id, patient_dni, patient_name, patient_last_name, patient_birth_date, patient_phone, patient_email, city_id, patient_address, patient_address_number, patient_address_floor, patient_address_department) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String UNIQUE_DNI_CONSTRAINT = "uk_patient_dni";
    private static final String UNIQUE_EMAIL_CONSTRAINT = "uk_patient_email";

    /**
     * Obtiene todos los pacientes de la base de datos
     * @return Lista de pacientes
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public List<Patient> getAllPatients() throws DataAccessException {
        List<Patient> patients = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_PATIENT);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Patient patient = mapResultSetToPatient(rs);
                patients.add(patient);
            }
            
            return patients;
            
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener el listado de pacientes", e);
        }
    }
    
    /**
     * Busca un paciente por su DNI
     * @param patientDNI DNI del paciente a buscar
     * @return El paciente encontrado
     * @throws EntityNotFoundException Si no se encuentra el paciente
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public Patient getPatientByPatientDNI(String patientDNI) throws EntityNotFoundException, DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_PATIENT_BY_DNI)) {
            
            ps.setString(1, patientDNI);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPatient(rs);
                } else {
                    throw new EntityNotFoundException("Patient", patientDNI);
                }
            }
            
        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar paciente por DNI", e);
        }
    }
    
    /**
     * Verifica si existe un paciente con el DNI especificado
     * @param patientDNI DNI del paciente a verificar
     * @return true si existe, false si no
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public boolean isPatientDNIExists(String patientDNI) throws DataAccessException {
        final String SQL = "SELECT 1 FROM tbl_patient WHERE patient_dni = ? LIMIT 1";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            
            ps.setString(1, patientDNI);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
            
        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar existencia de DNI de paciente", e);
        }
    }
    
    /**
     * Verifica si existe un paciente con el email especificado
     * @param patientEmail Email del paciente a verificar
     * @return true si existe, false si no
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public boolean isPatientEmailExists(String patientEmail) throws DataAccessException {
        final String SQL = "SELECT 1 FROM tbl_patient WHERE patient_email = ? LIMIT 1";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            
            ps.setString(1, patientEmail);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
            
        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar existencia de email de paciente", e);
        }
    }

    /**
     * Inserta un nuevo paciente en la base de datos
     * @param patient Paciente a insertar
     * @throws ConstraintViolationException Si se viola una restricción única
     * @throws DataAccessException Si ocurre otro error al acceder a la base de datos
     */
    public void insertPatient(Patient patient) throws ConstraintViolationException, DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_PATIENT)) {
            
            ps.setString(1, patient.getPatientId().toString());
            ps.setString(2, patient.getPatientDNI());
            ps.setString(3, patient.getPatientName());
            ps.setString(4, patient.getPatientLastName());
            ps.setDate(5, Date.valueOf(patient.getPatientBirthDate()));
            ps.setString(6, patient.getPatientPhone());
            ps.setString(7, patient.getPatientEmail());
            ps.setString(8, patient.getCityId().toString());
            ps.setString(9, patient.getPatientAddress());
            ps.setInt(10, patient.getPatientAddressNumber());
            
            if (patient.getPatientAddressFloor() >= 0) {
                ps.setInt(11, patient.getPatientAddressFloor());
            } else {
                ps.setNull(11, Types.INTEGER);
            }
            
            if (patient.getPatientAddressApartment() != null) {
                ps.setString(12, patient.getPatientAddressApartment());
            } else {
                ps.setNull(12, Types.VARCHAR);
            }

            ps.executeUpdate();
            
        } catch (SQLException e) {
            // Verificar si es violación de constraint única
            if (e.getMessage().contains("Duplicate entry")) {
                if (e.getMessage().contains(UNIQUE_DNI_CONSTRAINT)) {
                    throw new ConstraintViolationException("Patient", "DNI");
                } else if (e.getMessage().contains(UNIQUE_EMAIL_CONSTRAINT)) {
                    throw new ConstraintViolationException("Patient", "email");
                }
            }
            throw new DataAccessException("Error al insertar paciente", e);
        }
    }
    
    /**
     * Mapea un ResultSet a un objeto Patient
     */
    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        return new Patient(
            UUID.fromString(rs.getString("patient_id")),
            rs.getString("patient_dni"),
            rs.getString("patient_name"),
            rs.getString("patient_last_name"),
            rs.getDate("patient_birth_date").toLocalDate(),
            rs.getString("patient_phone"),
            rs.getString("patient_email"),
            UUID.fromString(rs.getString("city_id")),
            rs.getString("patient_address"),
            rs.getInt("patient_address_number"),
            rs.getInt("patient_address_floor"),
            rs.getString("patient_address_department")
        );
    }

    /**
     * Obtiene una conexión a la base de datos
     */
    private Connection getConnection() throws DataAccessException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DataAccessException("Error al conectar con la base de datos", e);
        }
    }
}