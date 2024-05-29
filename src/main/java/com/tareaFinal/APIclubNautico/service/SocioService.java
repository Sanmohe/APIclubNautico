package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.error.SocioNotFoundException;

import java.util.List;
import java.util.Optional;

public interface SocioService {

    List<Socio> findAllSocios();    //Método que devolverá una lista de elementos Socio (READ)

    //Optional se usa para evitar el manejo directo de valores null. Encapsula valores.
    //En lugar de devolver un "null" devuelve un "optional" que indica la posibilidad de ausencia de un valor
    //Si el valor optional existe, hay que usar GET para aceder.

    Optional<Socio> findSocioByNombreWithJPQL(String nombre);   //Método que devolverá los socios filtrados por "nombre"

    Optional<Socio> findSocioByNombreIgnoreCase(String nombre);   //Método que devolverá los socios filtrados por "nombre"

    //Optional<Socio> findSocioById(int id);  //Método que devolverá los socios filtrados por "id"

    Socio findSocioById(int id) throws SocioNotFoundException;    //Método que devolverá los socios filtrados por "id"
                                                                  //Puede arrojar la excepción "socio no encontrado"

    Socio saveSocio(Socio socio);   //Método que guardará el registro de un nuevo Socio (CREATE)

    Socio updateSocio (int id, Socio socio);    //Método que actualizará el registro de un Socio existente (UPDATE)

    void deleteSocio (int id);     //Método que borrará el registro de un Socio (DELETE)

}

