package com.tareaFinal.APIclubNautico.controller;

import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController                     //Indica que la clase es un Controlador de Spring.
                                    // Manejará las solicitudes HTTP y devolverá respuestas JSON
public class SocioController {

    @Autowired                      //Inyeccción de dependencias.
    SocioService socioService;
    //Se inyecta automáticamente una instancia del Servicio en la variable
    //Permite que el controlador use los métodos del Servicio sin necesidad de instanciarlo manualmente.


    @GetMapping("/findAllSocios")               //Mapea las solicitudes GET a la URL indicada
    public List<Socio> findAllSocios() {
        return socioService.findAllSocios();    //Llama a los métodos del Servicio para devolver la lista de todos los socios
    }






}
