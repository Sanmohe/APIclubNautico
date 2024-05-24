package com.tareaFinal.APIclubNautico.controller;

import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                     //Indica que la clase es un Controlador de Spring.
                                    // Manejará las solicitudes HTTP y devolverá respuestas JSON
public class SocioController {

    @Autowired                      //Inyeccción de dependencias.
    SocioService socioService;
    //Se inyecta automáticamente una instancia del Servicio en la variable
    //Permite que el controlador use los métodos del Servicio sin necesidad de instanciarlo manualmente.


    //ENDPOINTS

    @GetMapping("/findAllSocios")               //Mapea las solicitudes GET a la URL indicada
    public List<Socio> findAllSocios() {
        return socioService.findAllSocios();    //Llama al método del Servicio para devolver la lista de todos los socios
    }

    @PostMapping("/createSocio")                        //Mapea las solicitudes POST a la url indicada
    public Socio saveSocio(@RequestBody Socio socio) {  //Indica que va a recibir el cuerpo de la peticion HTTP y mapeará al objeto Socio
        return socioService.saveSocio(socio);           //Llama al método del Servicio para crear un nuevo socio
    }

    @PutMapping("/updateSocio/{id}")                    //Mapea las solicitudes PUT a la url indicada junto con un parámetro "id"
    public Socio updateSocio(@PathVariable int id, @RequestBody Socio socio) {  //Indica que va a capturar el parámetro "id" de la URL
                                                                                //y que el cuerpo de la peticion HTTP y mapeará al objeto Socio con ese "id"
        return socioService.updateSocio(id, socio);      //Llama al método del Servicio para actualizar un socio existente (según "id")
    }

    @DeleteMapping("/deleteSocio/{id}")                 //Mapea las solicitudes DELETE a la url indicada junto con un parámetro "id"
    public String deleteSocio(@PathVariable int id) {   //Indica que va a capturar el parámetro "id" de la URL
        socioService.deleteSocio(id);                   //Llama al método del Servicio para borrar un socio existente (según "id"
        return "Socio eliminado correctamente";
    }




}
