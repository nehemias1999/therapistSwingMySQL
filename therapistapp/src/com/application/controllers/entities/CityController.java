package com.application.controllers.entities;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.CityDTO;
import com.application.services.CityService;

import java.util.List;
import java.util.stream.Collectors;

public class CityController {
    private final CityService cityService;
    
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Retrieves all cities from the system
     * @return List of CityDTO objects
     * @throws BusinessException If there's an error accessing data
     */
    public List<CityDTO> getAllCities() throws BusinessException {
        return cityService.getAllCities();
    }

    /**
     * Registers a new city in the system
     * @param name City name
     * @param zipCode Postal code
     * @throws ValidationException If input data is invalid
     * @throws BusinessException If there's a business error
     */
    public void registerCity(String name, String zipCode) throws ValidationException, BusinessException {
        CityDTO cityDTO = new CityDTO(name, zipCode);
        cityService.insertCity(cityDTO);
    }

    /**
     * Checks if a city already exists
     * @param name City name to check
     * @return true if exists, false otherwise
     * @throws BusinessException If there's an error checking
     */
    public boolean cityExists(String name) throws BusinessException {
        return cityService.getAllCities().stream()
                .anyMatch(c -> c.getCityName().equalsIgnoreCase(name.trim()));
    }

    /**
     * Searches cities by name (partial match)
     * @param searchTerm Search term
     * @return List of matching cities
     * @throws BusinessException If there's a search error
     */
    public List<CityDTO> searchCitiesByName(String searchTerm) throws BusinessException {
        String term = searchTerm.toLowerCase().trim();
        return cityService.getAllCities().stream()
                .filter(c -> c.getCityName().toLowerCase().contains(term))
                .collect(Collectors.toList());
    }

    /**
     * Validates city data before registration
     * @param name City name
     * @param zipCode Postal code
     * @throws ValidationException If validation fails
     */
    public void validateCityData(String name, String zipCode) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("El nombre de la ciudad es requerido");
        }
        
        if (zipCode == null || zipCode.trim().isEmpty()) {
            throw new ValidationException("El código postal es requerido");
        }
    }
}