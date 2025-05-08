package com.application.model.dao;

import com.application.model.entities.City;
import com.application.exceptions.runtimeExceptions.dataAccessException.ConstraintViolationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import com.application.exceptions.runtimeExceptions.dataAccessException.EntityNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CityDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/therapist_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static final String SELECT_ALL_CITY =
        "SELECT city_id, city_name, city_zip_code FROM tbl_city ORDER BY city_name";

    private static final String SELECT_CITY_BY_ID =
        "SELECT city_id, city_name, city_zip_code FROM tbl_city WHERE city_id = ?";

    private static final String INSERT_CITY =
        "INSERT INTO tbl_city (city_id, city_name, city_zip_code) " +
        "VALUES (?, ?, ?)";

    private static final String UNIQUE_CITY_CONSTRAINT = "uk_city_name";

    /**
     * Obtiene todas las ciudades de la base de datos
     * @return Lista de ciudades
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public List<City> getAllCities() throws DataAccessException {
        List<City> cities = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_CITY);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                City city = mapResultSetToCity(rs);
                cities.add(city);
            }
            
            return cities;

        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener el listado de ciudades", e);
        }
    }

    /**
     * Busca una ciudad por su ID
     * @param cityId ID de la ciudad a buscar
     * @return La ciudad encontrada
     * @throws EntityNotFoundException Si no se encuentra la ciudad
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public City getCityById(UUID cityId) throws EntityNotFoundException, DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_CITY_BY_ID)) {
            
            ps.setString(1, cityId.toString());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCity(rs);
                } else {
                    throw new EntityNotFoundException("City", cityId);
                }
            }
            
        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar ciudad por ID", e);
        }
    }
    
    /**
     * Verifica si existe una ciudad con el nombre especificado
     * @param cityName Nombre de la ciudad a verificar
     * @return true si existe, false si no
     * @throws DataAccessException Si ocurre un error al acceder a la base de datos
     */
    public boolean isCityNameExists(String cityName) throws DataAccessException {
        final String SQL = "SELECT 1 FROM tbl_city WHERE city_name = ? LIMIT 1";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            ps.setString(1, cityName);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar existencia de ciudad", e);
        }
    }

    /**
     * Inserta una nueva ciudad en la base de datos
     * @param city Ciudad a insertar
     * @throws ConstraintViolationException Si se viola una restricción única
     * @throws DataAccessException Si ocurre otro error al acceder a la base de datos
     */
    public void insertCity(City city) throws ConstraintViolationException, DataAccessException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_CITY)) {

            ps.setString(1, city.getCityId().toString());
            ps.setString(2, city.getCityName());
            ps.setString(3, city.getCityZIPCode());

            ps.executeUpdate();

        } catch (SQLException e) {
            // Verificar si es violación de constraint única
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains(UNIQUE_CITY_CONSTRAINT)) {
                throw new ConstraintViolationException("City", "name");
            }
            throw new DataAccessException("Error al insertar ciudad", e);
        }
    }

    /**
     * Mapea un ResultSet a un objeto City
     */
    private City mapResultSetToCity(ResultSet rs) throws SQLException {
        return new City(
            UUID.fromString(rs.getString("city_id")),
            rs.getString("city_name"),
            rs.getString("city_zip_code")
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