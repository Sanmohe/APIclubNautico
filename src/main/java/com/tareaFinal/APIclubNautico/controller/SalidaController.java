package com.tareaFinal.APIclubNautico.controller;

import com.tareaFinal.APIclubNautico.DTO.BarcoDTO;
import com.tareaFinal.APIclubNautico.DTO.SalidaDTO;
import com.tareaFinal.APIclubNautico.entity.Barco;
import com.tareaFinal.APIclubNautico.entity.Salida;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import com.tareaFinal.APIclubNautico.service.BarcoService;
import com.tareaFinal.APIclubNautico.service.SalidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Indica que la clase es un Controlador de Spring.
// Manejará las solicitudes HTTP y devolverá respuestas JSON
public class SalidaController {

    //Inyección de dependencias
    @Autowired
    SalidaService salidaService;
    

    //ENDPOINTS

    @GetMapping("/findAllSalidas")
    List<SalidaDTO> findAllSalidas() {
        return salidaService.findAllSalidas();
    }

    @GetMapping("/findSalidaById/{id}")
    SalidaDTO findSalidaById(@PathVariable int id) throws NotFoundException {
        return salidaService.findSalidaById(id);
    }

    @GetMapping("/findSalidasByFecha/{fecha}")
    List<SalidaDTO> findSalidasByFecha(@PathVariable String fecha) throws NotFoundException {
        return salidaService.findSalidasByFecha(fecha);
    }

    @GetMapping("/findSalidasByBarco/{matricula}")
    List<SalidaDTO> findSalidasByBarco(@PathVariable String matricula) throws NotFoundException {
        return salidaService.findSalidasByBarco(matricula);
    }

    @GetMapping("/findSalidasByPatron/{idPatron}")
    List<SalidaDTO> findSalidasByPatron(@PathVariable int idPatron) throws NotFoundException {
        return salidaService.findSalidasByPatron(idPatron);
    }

    @PostMapping("/createSalida/{matricula}/{idPatron}")
    SalidaDTO saveSalida(@PathVariable String matricula, @PathVariable int idPatron, @Valid @RequestBody Salida salida) throws NotFoundException, AlreadyExistsException {
         return salidaService.saveSalida(matricula, idPatron, salida);
    }

    @PutMapping("/updateSalida/{id}")
    SalidaDTO updateSalida(@PathVariable int id, @Valid @RequestBody Salida salida) throws NotFoundException {
       return salidaService.updateSalida(id, salida);
    }


    @PutMapping("/cambiaBarco/{idSalida}/barco/{matricula}")
    SalidaDTO cambiaBarco(@PathVariable int idSalida, @PathVariable String matricula) throws NotFoundException {
        return salidaService.cambiaBarco(idSalida, matricula);
    }

    @PutMapping("/cambiaPatron/{idSalida}/patron/{idPatron}")
    SalidaDTO cambiaPatron(@PathVariable int idSalida, @PathVariable int idPatron) throws NotFoundException {
        return salidaService.cambiaPatron(idSalida, idPatron);
    }

    @DeleteMapping("/deleteSalida/{id}")
    public String deleteSalida(@PathVariable int id) throws NotFoundException {
        salidaService.deleteSalida(id);
        return "Salida eliminada correctamente";
    }

}
