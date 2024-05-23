package com.tareaFinal.APIclubNautico.controller;

import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SocioController {

    @Autowired
    SocioService socioService;

    @GetMapping("/findAllSocios")
    public List<Socio> findAllSocios() {
        return socioService.findAllSocios();
    }






}
