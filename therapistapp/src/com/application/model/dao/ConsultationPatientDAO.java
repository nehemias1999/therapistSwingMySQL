package com.application.model.dao;

import com.application.exceptions.runtimeExceptions.dataAccessException.ConstraintViolationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import com.application.exceptions.runtimeExceptions.dataAccessException.EntityNotFoundException;
import com.application.model.entities.ConsultationPatient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConsultationPatientDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/therapist_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    private static final String INSERT_SQL =
        "INSERT INTO tbl_consultation_patient ( " +
        "consultation_id, " +
        "patient_id, " +
        "consultation_amount, " +
        "is_paid, " +
        "patient_note_path " +
        ") VALUES (?, ?, ?, ?, ?)";
    
    private static final String UPDATE_SQL =
        "UPDATE tbl_consultation_patient SET " +
        "consultation_amount = ?, " +
        "is_paid = ?, " +
        "WHERE consultation_id = ? and patient_id = ?";
    
    private static final String DELETE_SQL =
        "UPDATE tbl_consultation_patient SET is_active = false WHERE consultation_id = ? and patient_id = ?";

    private static final String SELECT_PATIENTS_BY_CONSULTATION =
        "SELECT * FROM tbl_consultation_patient WHERE consultation_id = ?";
    
    private static final String UPDATE_IS_PAID_TRUE =
        "UPDATE tbl_consultation_patient SET " +
        "is_paid = true " +
        "WHERE consultation_id = ? and patient_id = ?";
    
    private static final String UNIQUE_CONSULTATION_PATIENT_CONSTRAINT = "uk_consultation_time";

    /**
     * Inserta un nuevo paciente a una consulta existente en la base de datos
     * @param consultationPatient Paciente a insertar
     * @throws ConstraintViolationException Si se viola una restricción única
     * @throws DataAccessException Si ocurre otro error al acceder a la base de datos
     */
    public void insertConsultationPatient(ConsultationPatient consultationPatient) throws ConstraintViolationException, DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, consultationPatient.getConsultationId().toString());
            ps.setString(2, consultationPatient.getPatientId().toString());
            ps.setDouble(3, consultationPatient.getConsultationAmount());
            ps.setBoolean(4, consultationPatient.getIsPaid());
            ps.setString(5, consultationPatient.getPatientNotePath());

            ps.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains(UNIQUE_CONSULTATION_PATIENT_CONSTRAINT)) {
                throw new ConstraintViolationException("Consultation", "start datetime");
            }
            throw new DataAccessException("Error al insertar consulta", e);
        }
    }
    
    /**
     * Eliminar un paciente de una consulta existente en la base de datos (borrado logico)
     * @param consultationId de la consulta a eliminar
     * @param patientId del paciente
     * @throws EntityNotFoundException Si no se encuentra la consulta
     * @throws DataAccessException Si ocurre otro error al acceder a la base de datos
     */
    public void deleteConsultationPatient(UUID consultationId, UUID patientId) throws EntityNotFoundException, DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setString(1, consultationId.toString());
            ps.setString(2, patientId.toString());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new EntityNotFoundException("consultation", consultationId.toString());
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar consulta", e);
        }
    }
    
    /**
     * Obtiene todos los pacientes de una consulta determinada
     * @param consultationId Id de la consulta a buscar
     * @return Lista de pacientes para la consulta especificada especificada
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public List<ConsultationPatient> getPatientsByConsultationId(UUID consultationId) throws DataAccessException {
        List<ConsultationPatient> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_PATIENTS_BY_CONSULTATION)) {

            ps.setString(1, consultationId.toString());
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToConsultationPatient(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new DataAccessException("Error al listar pacientes", e);
        }
    }

    /**
     * Modifica un tuple de la base de datos indicando que la consulta esta paga (is_paid = true)
     * @param consultationId de la consulta a modificar
     * @param patientId del paciente a modificar
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public void setConsultationPatientPaid(UUID consultationId, UUID patientId) throws DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_IS_PAID_TRUE)) {

            ps.setString(1, consultationId.toString());
            ps.setString(2, patientId.toString());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new EntityNotFoundException("consultation", consultationId.toString());
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar existencia de tupla", e);
        }
    }
    
    /**
     * Mapea un ResultSet a un objeto ConsultationPatient
     */
    private ConsultationPatient mapResultSetToConsultationPatient(ResultSet rs) throws SQLException {
        return new ConsultationPatient(
            UUID.fromString(rs.getString("consultation_id")),
            UUID.fromString(rs.getString("patient_id")),
            rs.getDouble("consultation_amount"),
            rs.getBoolean("is_paid"),
            rs.getString("patient_note_path")
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