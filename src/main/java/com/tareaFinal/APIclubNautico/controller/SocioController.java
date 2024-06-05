package com.tareaFinal.APIclubNautico.controller;

import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import com.tareaFinal.APIclubNautico.error.dto.SocioDTO;
import com.tareaFinal.APIclubNautico.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Indica que la clase es un Controlador de Spring.
// Manejará las solicitudes HTTP y devolverá respuestas JSON
public class SocioController {

    @Autowired
    //Inyeccción de dependencias.
    SocioService socioService;
    //Se inyecta automáticamente una instancia del Servicio en la variable
    //Permite que el controlador use los métodos del Servicio sin necesidad de instanciarlo manualmente.


    //ENDPOINTS

    @GetMapping("/findAllSocios")
        //Mapea las solicitudes GET a la URL indicada
    List<SocioDTO> findAllSocios() {
        return socioService.findAllSocios();
        //Llama al método del Servicio para devolver la lista de todos los socios
    }

    @GetMapping("/findSocioById/{id}")
        //Mapea las solicitudes GET a la URL indicada junto con el parametro "id"
    Socio findSocioById(@PathVariable int id) throws NotFoundException {
        //Indica que va a capturar el parámetro "id" de la URL
        //Y que puede arrojar la excepción de "no encontrado"
        return socioService.findSocioById(id);
        //Llama al método del Servicio para devolver la lista de socios filtrada por nombre
    }

    @GetMapping("/findSocioByDniWithJPQL/{dni}")
        //Mapea las solicitudes GET a la URL indicada junto con el parámetro "dni"
    Socio findSocioByNombreWithJPQL(@PathVariable String dni) throws NotFoundException {
        //Indica que va a capturar el parámetro "dni" de la URL
        //Y que puede arrojar la excepción de "no encontrado"
        return socioService.findSocioByDniWithJPQL(dni);
        //Llama al método del Servicio para devolver el socio filtrado por dni
    }

    @GetMapping("/findSocioByDni/{dni}")
        //Mapea las solicitudes GET a la URL indicada junto con el parámetro "dni"
    Socio findSocioByDniIgnoreCase(@PathVariable String dni) throws NotFoundException {
        //Indica que va a capturar el parámetro "dni" de la URL
        //Y que puede arrojar la excepción de "no encontrado"
        return socioService.findSocioByDniIgnoreCase(dni);
        //Llama al método del Servicio para devolver la lista de socios filtrada por nombre
    }

    @PostMapping("/createSocio")
        //Mapea las solicitudes POST a la url indicada
    SocioDTO saveSocio(@Valid @RequestBody Socio socio) throws AlreadyExistsException {
        //Indica que va a recibir el cuerpo de la peticion HTTP y mapeará al objeto Socio
        //Y que puede arrojar la excepción de "ya existe"
        //@Valid exige que se validen los datos
        return socioService.saveSocio(socio);
        //Llama al método del Servicio para crear un nuevo socio
    }

    @PutMapping("/updateSocio/{id}")
        //Mapea las solicitudes PUT a la url indicada junto con el parámetro "id"
    SocioDTO updateSocio(@PathVariable int id, @Valid @RequestBody Socio socio) throws NotFoundException, AlreadyExistsException {
        //Indica que va a capturar el parámetro "id" de la URL y que el cuerpo de la peticion HTTP y mapeará al objeto Socio con ese "id"
        //Y que puede arrojar la excepción de "no encontrado"
        //@Valid exige que se validen los datos
        return socioService.updateSocio(id, socio);
        //Llama al método del Servicio para actualizar un socio existente (según "id")
    }

    @DeleteMapping("/deleteSocio/{id}")
    //Mapea las solicitudes DELETE a la url indicada junto con el parámetro "id"
    public String deleteSocio(@PathVariable int id) throws NotFoundException {
        //Indica que va a capturar el parámetro "id" de la URL
        //Y que puede arrojar la excepción de "no encontrado"
        socioService.deleteSocio(id);
        //Llama al método del Servicio para borrar un socio existente (según "id")
        return "Socio eliminado correctamente";
    }

}
