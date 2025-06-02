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
    
    private static final String INSERT_SQL =
        "INSERT INTO tbl_consultation ( " +
        "consultation_id, " +
        "consultation_start_datetime, " +
        "consultation_end_datetime, " +
        "consultation_status " +
        ") VALUES (?, ?, ?, ?)";
    
    private static final String UPDATE_SQL =
        "UPDATE tbl_consultation SET " +
        "consultation_start_datetime = ?, " +
        "consultation_end_datetime = ?, " +
        "consultation_status = ? " +
        "WHERE consultation_id = ?";
    
    private static final String DELETE_SQL =
        "UPDATE tbl_consultation SET is_active = false WHERE consultation_id = ?";

    private static final String SELECT_CONSULTATION_BY_DATE =
        "SELECT * FROM tbl_consultation WHERE DATE(consultation_start_datetime) = ? and is_active = true ORDER BY consultation_start_datetime";
    
    private static final String SELECT_CONSULTATION_BY_ID =
        "SELECT * FROM tbl_consultation WHERE consultation_id = ? and is_active = true";
    
    private static final String CHECK_START_DATETIME_SQL =
        "SELECT COUNT(*) FROM tbl_consultation WHERE consultation_start_datetime = ?";

    private static final String UNIQUE_CONSULTATION_TIME_CONSTRAINT = "uk_consultation_time";

    /**
     * Inserta una nueva consulta en la base de datos
     * @param consultation Consulta a insertar
     * @throws ConstraintViolationException Si se viola una restricción única
     * @throws DataAccessException Si ocurre otro error al acceder a la base de datos
     */
    public void insertConsultation(Consultation consultation) throws ConstraintViolationException, DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, consultation.getConsultationId().toString());
            ps.setObject(2, consultation.getConsultationStartDateTime());
            ps.setObject(3, consultation.getConsultationEndDateTime());
            ps.setString(4, consultation.getConsultationStatus().toString());

            ps.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains(UNIQUE_CONSULTATION_TIME_CONSTRAINT)) {
                throw new ConstraintViolationException("Consultation", "start datetime");
            }
            throw new DataAccessException("Error al insertar consulta", e);
        }
    }
    
    /**
     * Modifica una consulta existente en la base de datos
     * @param consultation Consulta a modificar
     * @throws EntityNotFoundException Si no se encuentra la consulta
     * @throws ConstraintViolationException Si se viola la clave única de tiempo
     * @throws DataAccessException Si ocurre otro error al acceder a la base de datos
     */
    public void updateConsultation(Consultation consultation) throws EntityNotFoundException, ConstraintViolationException, DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setObject(1, consultation.getConsultationStartDateTime());
            ps.setObject(2, consultation.getConsultationEndDateTime());
            ps.setString(3, consultation.getConsultationStatus().name());
            ps.setString(4, consultation.getConsultationId().toString());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new EntityNotFoundException("consultation", consultation.getConsultationId().toString());
            }

        } catch (SQLException e) {
             if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains(UNIQUE_CONSULTATION_TIME_CONSTRAINT)) {
                throw new ConstraintViolationException("Consultation", "start datetime");
            }
            throw new DataAccessException("Error al actualizar consulta", e);
        }
    }
    
    /**
     * Eliminar una consulta existente en la base de datos (borrado logico)
     * @param consultationId de la consulta a eliminar
     * @throws EntityNotFoundException Si no se encuentra la consulta
     * @throws DataAccessException Si ocurre otro error al acceder a la base de datos
     */
    public void deleteConsultation(UUID consultationId) throws EntityNotFoundException, DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setString(1, consultationId.toString());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new EntityNotFoundException("consultation", consultationId.toString());
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar consulta", e);
        }
    }
    
    /**
     * Obtiene todas las consultas para una fecha específica
     * @param consultationDate Fecha de las consultas a buscar
     * @return Lista de consultas para la fecha especificada
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public List<Consultation> getConsultationsByDate(Date consultationDate) throws DataAccessException {
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
     * Obtiene la consulta para un identificador determinado 
     * @param consultationId Identificador de la consulta
     * @return consulta asociada al identificador
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public Consultation getConsultationById(UUID consultationId) throws DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_CONSULTATION_BY_ID)) {
            
            ps.setString(1, consultationId.toString());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToConsultation(rs);
                } else {
                    throw new EntityNotFoundException("Consultation", consultationId.toString());
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener la consulta", e);
        }
    }
    
    /**
     * Verifica si existe una consulta con la fecha/hora de inicio indicada
     * @param startDateTime Fecha y hora de inicio a verificar
     * @return true si ya existe, false en caso contrario
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public boolean isConsultationStartDatetimeExists(LocalDateTime startDateTime) throws DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(CHECK_START_DATETIME_SQL)) {

            ps.setTimestamp(1, Timestamp.valueOf(startDateTime));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar existencia de consulta en la fecha/hora: " + startDateTime, e);
        }
    }
    
    /**
     * Mapea un ResultSet a un objeto Consultation
     */
    private Consultation mapResultSetToConsultation(ResultSet rs) throws SQLException {
        return new Consultation(
            UUID.fromString(rs.getString("consultation_id")),
            rs.getTimestamp("consultation_start_datetime").toLocalDateTime(),
            rs.getTimestamp("consultation_end_datetime").toLocalDateTime(),
            ConsultationStatus.valueOf(rs.getString("consultation_status"))
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