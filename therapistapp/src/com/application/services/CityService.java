package com.application.services;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.ConstraintViolationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import com.application.model.dao.CityDAO;
import com.application.model.dto.CityDTO;
import com.application.model.entities.City;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CityService {
    private final CityDAO cityDAO;

    public CityService() {
        this.cityDAO = new CityDAO();
    }

    /**
     * Obtiene todas las ciudades convertidas a DTO
     * @return Lista de CityDTO
     * @throws BusinessException Si ocurre un error al acceder a los datos
     */
    public List<CityDTO> getAllCities() throws BusinessException {
        try {
            return cityDAO.getAllCities().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new BusinessException("Error al obtener el listado de ciudades", e);
        }
    }

    /**
     * Inserta una nueva ciudad
     * @param cityDTO Datos de la ciudad a insertar
     * @throws ValidationException Si los datos no son válidos o la ciudad ya existe
     * @throws BusinessException Si ocurre otro error de negocio
     */
    public void insertCity(CityDTO cityDTO) throws ValidationException, BusinessException {
        // Validación básica de datos de entrada
        if (cityDTO.getCityName() == null || cityDTO.getCityName().trim().isEmpty()) {
            throw new ValidationException("El nombre de la ciudad es requerido");
        }

        if (cityDTO.getCityZIPCode() == null || cityDTO.getCityZIPCode().trim().isEmpty()) {
            throw new ValidationException("El código postal es requerido");
        }

        try {
            // Verificar si la ciudad ya existe
            if (cityDAO.isCityNameExists(cityDTO.getCityName().trim())) {
                throw new ValidationException("Ya existe una ciudad con nombre: " + cityDTO.getCityName());
            }

            // Crear y guardar la nueva ciudad
            City city = new City(
                UUID.randomUUID(),
                cityDTO.getCityName().trim(),
                cityDTO.getCityZIPCode().trim()
            );

            cityDAO.insertCity(city);

        } catch (ConstraintViolationException e) {
            // Esto podría ocurrir si hay una condición de carrera
            throw new ValidationException("La ciudad ya existe en el sistema");
        } catch (DataAccessException e) {
            throw new BusinessException("Error al guardar la ciudad en el sistema", e);
        }
    }

    /**
     * Convierte una entidad City a CityDTO
     */
    private CityDTO convertToDTO(City city) {
        return new CityDTO(
            city.getCityId().toString(),
            city.getCityName(),
            city.getCityZIPCode()
        );
    }
}