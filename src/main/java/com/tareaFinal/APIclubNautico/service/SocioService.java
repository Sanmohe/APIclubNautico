package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import com.tareaFinal.APIclubNautico.error.dto.SocioDTO;

import java.util.List;

public interface SocioService {

    List<SocioDTO> findAllSocios();
    //Método que devolverá una lista de elementos Socio (READ)

    Socio findSocioById(int id) throws NotFoundException;
    //Método que devolverá los socios filtrados por "id"
    //Puede arrojar la excepción "no encontrado"

    Socio findSocioByDniWithJPQL(String dni) throws NotFoundException;
    //Método que devolverá el socio filtrado por "dni" (JPQL)
    //Puede arrojar la excepción "no encontrado"

    Socio findSocioByDniIgnoreCase(String dni) throws NotFoundException;
    //Método que devolverá el socio filtrado por "dni"(Spring JPA)
    //Puede arrojar la excepción "no encontrado"

    SocioDTO saveSocio(Socio socio) throws AlreadyExistsException;
    //Método que guardará el registro de un nuevo Socio (CREATE)
    //Puede arrojar la excepción "ya existe"

    Socio updateSocio (int id, Socio socio) throws NotFoundException, AlreadyExistsException;
    //Método que actualizará el registro de un Socio existente (UPDATE)
    //Puede arrojar la excepción "no encontrado"
    //Puede arrojar la excepción "ya existe"

    void deleteSocio (int id) throws NotFoundException;;
    //Método que borrará el registro de un Socio (DELETE)
    //Puede arrojar la excepción "no encontrado"

}

