package com.application.model.dao;

import com.application.exceptions.runtimeExceptions.dataAccessException.ConstraintViolationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import com.application.exceptions.runtimeExceptions.dataAccessException.EntityNotFoundException;
import com.application.model.entities.Consultation;
import com.application.model.enumerations.ConsultationStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConsultationDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/therapist_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static final String SELECT_CONSULTATION_BY_DATE =
            "SELECT * FROM tbl_consultation WHERE DATE(consultation_start_datetime) = ? ORDER BY consultation_start_datetime";

    private static final String SELECT_CONSULTATION_BY_PATIENT_ID =
            "SELECT * FROM tbl_consultation WHERE patient_id = ? ORDER BY consultation_start_datetime";

    private static final String INSERT_CONSULTATION =
            "INSERT INTO tbl_consultation (consultation_id, patient_id, consultation_start_datetime, consultation_end_datetime, consultation_status, consultation_note_path, consultation_amount, consultation_amount_paid) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UNIQUE_CONSULTATION_TIME_CONSTRAINT = "uk_consultation_time";

    /**
     * Obtiene todas las consultas para una fecha específica
     * @param consultationDate Fecha de las consultas a buscar
     * @return Lista de consultas para la fecha especificada
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public List<Consultation> getConsultationByConsultationDate(Date consultationDate) throws DataAccessException {
        List<Consultation> consultations = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_CONSULTATION_BY_DATE)) {
            
            ps.setDate(1, consultationDate);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consultation consultation = mapResultSetToConsultation(rs);
                    consultations.add(consultation);
                }
            }
            
            return consultations;

        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener consultas por fecha", e);
        }
    }

    /**
     * Obtiene todas las consultas para un paciente específico
     * @param patientId ID del paciente
     * @return Lista de consultas del paciente
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     * @throws EntityNotFoundException Si no se encuentra el paciente
     */
    public List<Consultation> getConsultationByPatientId(UUID patientId) throws DataAccessException, EntityNotFoundException {
        List<Consultation> consultations = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_CONSULTATION_BY_PATIENT_ID)) {
            
            ps.setString(1, patientId.toString());
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consultation consultation = mapResultSetToConsultation(rs);
                    consultations.add(consultation);
                }
                
                if (consultations.isEmpty()) {
                    throw new EntityNotFoundException("Consultations for patient", patientId);
                }
                
                return consultations;
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener consultas por ID de paciente", e);
        }
    }

    /**
     * Verifica si ya existe una consulta en la fecha/hora especificada
     * @param consultationStartDatetime Fecha y hora de inicio a verificar
     * @return true si existe una consulta en ese horario, false si no
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public boolean isConsultationStartDatetimeExists(LocalDateTime consultationStartDatetime) throws DataAccessException {
        final String SQL = "SELECT 1 FROM tbl_consultation WHERE consultation_start_datetime = ? LIMIT 1";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            ps.setTimestamp(1, Timestamp.valueOf(consultationStartDatetime));
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar disponibilidad de horario", e);
        }
    }

    /**
     * Inserta una nueva consulta en la base de datos
     * @param consultation Consulta a insertar
     * @throws ConstraintViolationException Si se viola una restricción única
     * @throws DataAccessException Si ocurre otro error al acceder a la base de datos
     */
    public void insertConsultation(Consultation consultation) throws ConstraintViolationException, DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_CONSULTATION)) {

            ps.setString(1, consultation.getConsultationId().toString());
            ps.setString(2, consultation.getPatientId().toString());
            ps.setTimestamp(3, Timestamp.valueOf(consultation.getConsultationStartDateTime()));
            ps.setTimestamp(4, Timestamp.valueOf(consultation.getConsultationEndDateTime()));
            ps.setString(5, consultation.getConsultationStatus().toString());
            ps.setString(6, consultation.getConsultationNotePath());
            ps.setBigDecimal(7, consultation.getConsultationAmount());
            ps.setBoolean(8, consultation.getConsultationAmountPaid());

            ps.executeUpdate();

        } catch (SQLException e) {
            // Verificar si es violación de constraint única
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains(UNIQUE_CONSULTATION_TIME_CONSTRAINT)) {
                throw new ConstraintViolationException("Consultation", "start datetime");
            }
            throw new DataAccessException("Error al insertar consulta", e);
        }
    }

    /**
     * Mapea un ResultSet a un objeto Consultation
     */
    private Consultation mapResultSetToConsultation(ResultSet rs) throws SQLException {
        return new Consultation(
            UUID.fromString(rs.getString("consultation_id")),
            UUID.fromString(rs.getString("patient_id")),
            rs.getTimestamp("consultation_start_datetime").toLocalDateTime(),
            rs.getTimestamp("consultation_end_datetime").toLocalDateTime(),
            ConsultationStatus.valueOf(rs.getString("consultation_status")),
            rs.getString("consultation_note_path"),
            rs.getBigDecimal("consultation_amount"),
            rs.getBoolean("consultation_amount_paid")
        );
    }
    
    public boolean isTimeSlotBooked(LocalDateTime startDateTime, LocalDateTime endDateTime) throws DataAccessException {
        final String SQL = "SELECT COUNT(*) FROM tbl_consultation " +
                          "WHERE (? BETWEEN consultation_start_datetime AND consultation_end_datetime " +
                          "OR ? BETWEEN consultation_start_datetime AND consultation_end_datetime " +
                          "OR (consultation_start_datetime BETWEEN ? AND ?) " +
                          "OR (consultation_end_datetime BETWEEN ? AND ?))";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            // Set parameters
            ps.setTimestamp(1, Timestamp.valueOf(startDateTime));
            ps.setTimestamp(2, Timestamp.valueOf(endDateTime));
            ps.setTimestamp(3, Timestamp.valueOf(startDateTime));
            ps.setTimestamp(4, Timestamp.valueOf(endDateTime));
            ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
            ps.setTimestamp(6, Timestamp.valueOf(endDateTime));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar disponibilidad de horario", e);
        }
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