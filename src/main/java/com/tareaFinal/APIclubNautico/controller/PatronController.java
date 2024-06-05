package com.tareaFinal.APIclubNautico.controller;

import com.tareaFinal.APIclubNautico.entity.Patron;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import com.tareaFinal.APIclubNautico.service.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Indica que la clase es un Controlador de Spring.
// Manejará las solicitudes HTTP y devolverá respuestas JSON
public class PatronController {

    @Autowired
    //Inyeccción de dependencias.
    PatronService patronService;
    //Se inyecta automáticamente una instancia del Servicio en la variable
    //Permite que el controlador use los métodos del Servicio sin necesidad de instanciarlo manualmente.


    //ENDPOINTS

    @GetMapping("/findAllPatrones")
        //Mapea las solicitudes GET a la URL indicada
    List<Patron> findAllPatrones() {
        return patronService.findAllPatrones();
        //Llama al método del Servicio para devolver la lista de todos los patrones
    }

    @GetMapping("/findPatronById/{id}")
        //Mapea las solicitudes GET a la URL indicada junto con el parametro "id"
    Patron findPatronById(@PathVariable int id) throws NotFoundException {
        //Indica que va a capturar el parámetro "id" de la URL
        //Y que puede arrojar la excepción de "no encontrado"
        return patronService.findPatronById(id);
        //Llama al método del Servicio para devolver la lista de patrons filtrada por nombre
    }

    @GetMapping("/findPatronByDni/{dni}")
        //Mapea las solicitudes GET a la URL indicada junto con el parámetro "dni"
    Patron findPatronByDniIgnoreCase(@PathVariable String dni) throws NotFoundException {
        //Indica que va a capturar el parámetro "dni" de la URL
        //Y que puede arrojar la excepción de "no encontrado"
        return patronService.findPatronByDniIgnoreCase(dni);
        //Llama al método del Servicio para devolver la lista de patrons filtrada por nombre
    }

    @PostMapping("/createPatron")
        //Mapea las solicitudes POST a la url indicada
    Patron savePatron(@Valid @RequestBody Patron patron) throws AlreadyExistsException {
        //Indica que va a recibir el cuerpo de la peticion HTTP y mapeará al objeto Patron
        //Y que puede arrojar la excepción de "ya existe"
        //@Valid exige que se validen los datos
        return patronService.savePatron(patron);
        //Llama al método del Servicio para crear un nuevo patron
    }

    @PutMapping("/updatePatron/{id}")
        //Mapea las solicitudes PUT a la url indicada junto con el parámetro "id"
    Patron updatePatron(@PathVariable int id, @Valid @RequestBody Patron patron) throws NotFoundException, AlreadyExistsException {
        //Indica que va a capturar el parámetro "id" de la URL y que el cuerpo de la peticion HTTP y mapeará al objeto Patron con ese "id"
        //Y que puede arrojar la excepción de "no encontrado"
        //@Valid exige que se validen los datos
        return patronService.updatePatron(id, patron);
        //Llama al método del Servicio para actualizar un patron existente (según "id")
    }

    @DeleteMapping("/deletePatron/{id}")
    //Mapea las solicitudes DELETE a la url indicada junto con el parámetro "id"
    public String deletePatron(@PathVariable int id) throws NotFoundException {
        //Indica que va a capturar el parámetro "id" de la URL
        //Y que puede arrojar la excepción de "no encontrado"
        patronService.deletePatron(id);
        //Llama al método del Servicio para borrar un patron existente (según "id")
        return "Patron eliminado correctamente";
    }

}
