package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Socio;

import java.util.List;

public interface SocioService {

    List<Socio> findAllSocios();    //Método que devolverá una lista de elementos Socio (READ)

    Socio saveSocio(Socio socio);   //Método que guardará el registro de un nuevo Socio (CREATE)

    Socio updateSocio (int id, Socio socio);    //Método que actualizará el registro de un Socio existente (UPDATE)

    void deleteSocio (int id);     //Método que borrará el registro de un Socio (DELETE)

}
