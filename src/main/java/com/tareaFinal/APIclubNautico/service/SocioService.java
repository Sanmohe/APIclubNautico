package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Socio;

import java.util.List;

public interface SocioService {

    List<Socio> findAllSocios();    //Método que devolverá una lista de elementos Socio

    Socio saveSocio(Socio socio);   //Método que guardará el registro de un nuevo Socio

    Socio updateSocio (int id, Socio socio);    //Métoodo que actualizará el registro de un Socio existente

    void deleteSocio (int id, Socio socio);     //Método que borrará el registro de un Socio

}
