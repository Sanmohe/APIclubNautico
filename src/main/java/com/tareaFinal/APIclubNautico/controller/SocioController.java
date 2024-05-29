package com.tareaFinal.APIclubNautico.controller;

import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.error.SocioNotFoundException;
import com.tareaFinal.APIclubNautico.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController                     //Indica que la clase es un Controlador de Spring.
                                    // Manejará las solicitudes HTTP y devolverá respuestas JSON
public class SocioController {

    @Autowired                      //Inyeccción de dependencias.
    SocioService socioService;
    //Se inyecta automáticamente una instancia del Servicio en la variable
    //Permite que el controlador use los métodos del Servicio sin necesidad de instanciarlo manualmente.


    //ENDPOINTS

    @GetMapping("/findAllSocios")               //Mapea las solicitudes GET a la URL indicada
    List<Socio> findAllSocios() {
        return socioService.findAllSocios();    //Llama al método del Servicio para devolver la lista de todos los socios
    }

    @GetMapping("/findSocioById/{id}")                              //Mapea las solicitudes GET a la URL indicada junto con el parametro "id"
    Socio findSocioById(@PathVariable int id) throws SocioNotFoundException {
        //Indica que va a capturar el parámetro "id" de la URL
        //Y que puede arrojar la excepción de "socio no encontrado"
        return socioService.findSocioById(id);                      //Llama al método del Servicio para devolver la lista de socios filtrada por nombre
    }

    /*
    @GetMapping("/findSocioById/{id}")                              //Mapea las solicitudes GET a la URL indicada junto con el parametro "id"
    Optional<Socio> findSocioById(@PathVariable int id) {    //Indica que va a capturar el parámetro "id" de la URL
        return socioService.findSocioById(id);                      //Llama al método del Servicio para devolver la lista de socios filtrada por nombre
    }
    */


    @GetMapping("/findSocioByNombreWithJPQL/{nombre}")                          //Mapea las solicitudes GET a la URL indicada junto con el parámetro "nombre"
    Optional<Socio> findSocioByNombreWithJPQL(@PathVariable String nombre) {    //Indica que va a capturar el parámetro "nombre" de la URL
        return socioService.findSocioByNombreWithJPQL(nombre);                  //Llama al método del Servicio para devolver la lista de socios filtrada por nombre
    }

    @GetMapping("/findSocioByNombre/{nombre}")                          //Mapea las solicitudes GET a la URL indicada junto con el parámetro "nombre"
    Optional<Socio> findSocioByNombreIgnoreCase(@PathVariable String nombre) {    //Indica que va a capturar el parámetro "nombre" de la URL
        return socioService.findSocioByNombreIgnoreCase(nombre);                  //Llama al método del Servicio para devolver la lista de socios filtrada por nombre
    }

    @PostMapping("/createSocio")                        //Mapea las solicitudes POST a la url indicada
    Socio saveSocio(@Valid @RequestBody Socio socio) {  //Indica que va a recibir el cuerpo de la peticion HTTP y mapeará al objeto Socio
        //@Valid exige que se validen los datos
        return socioService.saveSocio(socio);           //Llama al método del Servicio para crear un nuevo socio
    }

    @PutMapping("/updateSocio/{id}")                                        //Mapea las solicitudes PUT a la url indicada junto con el parámetro "id"
    Socio updateSocio(@PathVariable int id, @RequestBody Socio socio) {     //Indica que va a capturar el parámetro "id" de la URL
                                                                            //y que el cuerpo de la peticion HTTP y mapeará al objeto Socio con ese "id"
        return socioService.updateSocio(id, socio);                         //Llama al método del Servicio para actualizar un socio existente (según "id")
    }

    @DeleteMapping("/deleteSocio/{id}")                 //Mapea las solicitudes DELETE a la url indicada junto con el parámetro "id"
    public String deleteSocio(@PathVariable int id) {   //Indica que va a capturar el parámetro "id" de la URL
        socioService.deleteSocio(id);                   //Llama al método del Servicio para borrar un socio existente (según "id"
        return "Socio eliminado correctamente";
    }




}
