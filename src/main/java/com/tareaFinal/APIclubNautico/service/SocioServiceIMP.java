package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SocioServiceIMP implements SocioService {

    @Autowired      //Inyecta el repositorio. inversión de control
    SocioRepository socioRepository;

    @Override
    public List<Socio> findAllSocios() {
        return socioRepository.findAll();
    }
}
