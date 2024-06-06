package com.tareaFinal.APIclubNautico.controller;


import com.tareaFinal.APIclubNautico.entity.Barco;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import com.tareaFinal.APIclubNautico.DTO.BarcoDTO;
import com.tareaFinal.APIclubNautico.service.BarcoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Indica que la clase es un Controlador de Spring.
// Manejará las solicitudes HTTP y devolverá respuestas JSON
public class BarcoController {

    @Autowired
    //Inyeccción de dependencias.
    BarcoService barcoService;
    //Se inyecta automáticamente una instancia del Servicio en la variable
    //Permite que el controlador use los métodos del Servicio sin necesidad de instanciarlo manualmente.
    
    
    //ENDPOINTS

    @GetMapping("/findAllBarcos")
    //Mapea las solicitudes GET a la URL indicada
    List<BarcoDTO> findAllBarcoes() {
        return barcoService.findAllBarcos();
        //Llama al método del Servicio para devolver la lista de todos los barcos
    }

    @GetMapping("/findBarcoByMatricula/{matricula}")
        //Mapea las solicitudes GET a la URL indicada junto con el parametro "matrcula"
    BarcoDTO findBarcoByMatricula(@PathVariable String matricula) throws NotFoundException {
        //Indica que va a capturar el parámetro "matricula" de la URL
        //Y que puede arrojar la excepción de "no encontrado"
        return barcoService.findBarcoByMatriculaIgnoreCase(matricula);
        //Llama al método del Servicio para devolver la lista de barcos filtrada por nombre
    }

    @GetMapping("/findBarcoByNombre/{nombre}")
        //Mapea las solicitudes GET a la URL indicada junto con el parámetro "dni"
    BarcoDTO findBarcoByDniIgnoreCase(@PathVariable String nombre) throws NotFoundException {
        //Indica que va a capturar el parámetro "dni" de la URL
        //Y que puede arrojar la excepción de "no encontrado"
        return barcoService.findBarcoByNombreIgnoreCase(nombre);
        //Llama al método del Servicio para devolver el barco filtrado por nombre
    }

    @PostMapping("/createBarco/{idSocio}")
        //Mapea las solicitudes POST a la url indicada
    BarcoDTO saveBarco(@PathVariable int idSocio, @Valid @RequestBody Barco barco) throws NotFoundException, AlreadyExistsException {
        //Indica que va a recibir el cuerpo de la peticion HTTP y mapeará al objeto Barco
        //Puede arrojar la excepción de "no encontrado" y "ya existe"
        //@Valid exige que se validen los datos
        return barcoService.saveBarco(idSocio, barco);
        //Llama al método del Servicio para crear un nuevo barco
    }

    @PutMapping("/updateBarco/{matricula}")
        //Mapea las solicitudes PUT a la url indicada junto con el parámetro "matricula"
    BarcoDTO updateBarco(@PathVariable String matricula, @Valid @RequestBody Barco barco) throws NotFoundException {
        //Indica que va a capturar el parámetro "matricula" de la URL y que el cuerpo de la peticion HTTP y mapeará al objeto Barco con esa "matricula"
        //Y que puede arrojar la excepción de "no encontrado"
        //@Valid exige que se validen los datos
        return barcoService.updateBarco(matricula, barco);
        //Llama al método del Servicio para actualizar un barco existente (según "matricula")
    }


    @PutMapping("/cambiaPropietario/{matricula}/socio/{idSocio}")
    //Mapea las solicitudes PUT a la url indicada junto con el parámetro "matricula"
    BarcoDTO cambiaPropietarioBarco(@PathVariable String matricula, @PathVariable int idSocio) throws NotFoundException {
        //Indica que va a capturar el parámetro "matricula" de la URL y que el cuerpo de la peticion HTTP indicará el ID del nuevo propietario
        //Y que puede arrojar la excepción de "no encontrado"
        return barcoService.cambiaPropietarioBarco(matricula, idSocio);
        //Llama al método del Servicio para actualizar un barco existente (según "matricula")
    }

    @DeleteMapping("/deleteBarco/{matricula}")
    //Mapea las solicitudes DELETE a la url indicada junto con el parámetro "matricula"
    public String deleteBarco(@PathVariable String matricula) throws NotFoundException {
        //Indica que va a capturar el parámetro "matricula" de la URL
        //Y que puede arrojar la excepción de "no encontrado"
        barcoService.deleteBarco(matricula);
        //Llama al método del Servicio para borrar un barco existente (según "matricula")
        return "Barco eliminado correctamente";
    }
    
    
    
}
