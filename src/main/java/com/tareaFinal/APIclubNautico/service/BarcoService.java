package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Barco;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import com.tareaFinal.APIclubNautico.error.dto.BarcoDTO;

import java.util.List;

public interface BarcoService {

    List<BarcoDTO> findAllBarcos();
    //Método que devolverá una lista de elementos Barco (READ)

    BarcoDTO findBarcoByMatriculaIgnoreCase(String matricula) throws NotFoundException;
    //Método que devolverá los barcos filtrados por "id"
    //Puede arrojar la excepción "no encontrado"

    BarcoDTO findbarcoByNombreIgnoreCase(String nombre) throws NotFoundException;
    //Método que devolverá el barco filtrado por "nombre"(Spring JPA)
    //Puede arrojar la excepción "no encontrado"

    BarcoDTO saveBarco(int idSocio, Barco barco) throws NotFoundException, AlreadyExistsException;
    //Método que guardará el registro de un nuevo Barco (CREATE)
    //Puede arrojar la excepción "no encontrado"
    //Puede arrojar la excepción "ya existe"

    BarcoDTO updateBarco (String matricula, Barco barco) throws NotFoundException;
    //Método que actualizará el registro de un Barco existente (UPDATE)
    //Puede arrojar la excepción "no encontrado"
    //Puede arrojar la excepción "ya existe"

    BarcoDTO cambiaPropietarioBarco (String matricula, int idSocio) throws NotFoundException;
    //Método que actualizará el propietario asociado a un Barco existente (UPDATE)
    //Puede arrojar la excepción "no encontrado"

    void deleteBarco (String matricula) throws NotFoundException;
    //Método que borrará el registro de un Barco (DELETE)
    //Puede arrojar la excepción "no encontrado"
}
