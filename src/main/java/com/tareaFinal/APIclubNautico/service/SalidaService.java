package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.DTO.SalidaDTO;
import com.tareaFinal.APIclubNautico.entity.Salida;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import java.util.List;

public interface SalidaService {

    List<SalidaDTO> findAllSalidas();
    //Método que devolverá una lista de elementos Salida (READ)

    SalidaDTO findSalidaById(int id) throws NotFoundException;
    //Método que devolverá la salida filtrada por "id" (READ)
    //Puede arrojar la excepción "no encontrado"

    List<SalidaDTO> findSalidasByFecha(String fecha) throws NotFoundException;
    //Método que devolverá las salidas filtrados por "fecha" (READ)
    //Puede arrojar la excepción "no encontrado"

    List<SalidaDTO> findSalidasByBarco(String matricula) throws NotFoundException;
    //Método que devolverá las salidas filtradas por la "matricula" de un barco (READ)
    //Puede arrojar la excepción "no encontrado"

    List<SalidaDTO> findSalidasByPatron(int idPatron) throws NotFoundException;
    //Método que devolverá las salidas asociadas a un patron (idPatron) (READ)
    //Puede arrojar la excepción "no encontrado"

    SalidaDTO saveSalida(String matricula, int idPatron, Salida salida) throws NotFoundException;
    //Método que guardará el registro de una nueva  salida (CREATE)
    //Puede arrojar la excepción "no encontrado"
   
    SalidaDTO updateSalida (int idSalida, Salida salida) throws NotFoundException;
    //Método que actualizará el registro de una Salida existente (UPDATE)
    //Puede arrojar la excepción "no encontrado"

    SalidaDTO cambiaBarco (int idSalida, String matricula) throws NotFoundException;
    //Método que actualizará el barco de una Salida existente (UPDATE)
    //Puede arrojar la excepción "no encontrado"

    SalidaDTO cambiaPatron (int idSalida, int idPatron) throws NotFoundException;
    //Método que actualizará el patrón de una Salida existente (UPDATE)
    //Puede arrojar la excepción "no encontrado"

    void deleteSalida (int id) throws NotFoundException;
    //Método que borrará el registro de un Salida (DELETE)
    //Puede arrojar la excepción "no encontrado"
}