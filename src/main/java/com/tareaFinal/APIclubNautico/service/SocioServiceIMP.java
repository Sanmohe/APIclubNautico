package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service                                    //Indica que es un Servicio de Spring. Spring la detectará y gestionará como un bean.
public class SocioServiceIMP implements SocioService {

    @Autowired                              //Inyección de dependencias
    SocioRepository socioRepository;
    ///Se inyecta automáticamente una instancia del Repositorio en la variable
    //Permite que el controlador use los métodos del Repositorio sin necesidad de instanciarlo manualmente.

    @Override
    public List<Socio> findAllSocios() {
        return socioRepository.findAll();   //Llama al método del Repositorio para listar todos los Socios (READ)
    }

    @Override
    public Socio saveSocio(Socio socio) {
        return socioRepository.save(socio); //Llama al método del Repositorio para guardar un socio (CREATE)
    }

    @Override
    public Socio updateSocio(int id, Socio socio) {
        Socio socioExistente = socioRepository.findById(id).get();     //Se extrae el Socio existente buscandolo por el "id" introducido
        if (socioExistente != null) {
            socioExistente.setId_socio(socio.getId_socio());
        }
        //socio.getNombre();
        return socioRepository.save(socio);
    }

    @Override
    public void deleteSocio(int id, Socio socio) {
        socioRepository.deleteById(id);
    }
}
