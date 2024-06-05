package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Patron;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;

import java.util.List;

public interface PatronService {

    List<Patron> findAllPatrones();
    //Método que devolverá una lista de elementos Patron (READ)

    Patron findPatronById(int id) throws NotFoundException;
    //Método que devolverá los patrons filtrados por "id"
    //Puede arrojar la excepción "no encontrado"

    Patron findPatronByDniIgnoreCase(String dni) throws NotFoundException;
    //Método que devolverá el patron filtrado por "dni"(Spring JPA)
    //Puede arrojar la excepción "no encontrado"

    Patron savePatron(Patron patron) throws AlreadyExistsException;
    //Método que guardará el registro de un nuevo Patron (CREATE)
    //Puede arrojar la excepción "ya existe"

    Patron updatePatron (int id, Patron patron) throws NotFoundException, AlreadyExistsException;
    //Método que actualizará el registro de un Patron existente (UPDATE)
    //Puede arrojar la excepción "no encontrado"

    void deletePatron (int id) throws NotFoundException;;
    //Método que borrará el registro de un Patron (DELETE)
    //Puede arrojar la excepción "no encontrado"
}

