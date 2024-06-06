package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.DTO.SalidaDTO;
import com.tareaFinal.APIclubNautico.entity.Barco;
import com.tareaFinal.APIclubNautico.entity.Patron;
import com.tareaFinal.APIclubNautico.entity.Salida;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import com.tareaFinal.APIclubNautico.repository.BarcoRepository;
import com.tareaFinal.APIclubNautico.repository.PatronRepository;
import com.tareaFinal.APIclubNautico.repository.SalidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//Indica que es un Servicio de Spring. Spring la detectará y gestionará como un bean.
public class SalidaServiceIMP implements SalidaService{

    //INYECCION DE DEPENDENCIAS
    @Autowired
    SalidaRepository salidaRepository;

    @Autowired
    BarcoRepository barcoRepository;

    @Autowired
    PatronRepository patronRepository;


    @Override
    public List<SalidaDTO> findAllSalidas() {
        List<Salida> salidas = salidaRepository.findAll();
        List<SalidaDTO> salidaDTOs = new ArrayList<>();
        //Conversión de salidas a DTO
        for (Salida salida:salidas) {
            SalidaDTO salidaDTO = new SalidaDTO();
            salidaDTO.convierteDTO(salida);
            salidaDTOs.add(salidaDTO);
        }
        return salidaDTOs;
    }

    @Override
    public SalidaDTO findSalidaById(int id) throws NotFoundException {
        //LA SALIDA NO EXISTE
        Optional<Salida> salidaExistente = salidaRepository.findSalidaById(id);
        if (!salidaExistente.isPresent()) {
            throw new NotFoundException("No existe la salida");
        }
        //Conversión de salidas a DTO
        Salida salida = salidaExistente.get();
        SalidaDTO salidaDTO = new SalidaDTO();
        salidaDTO.convierteDTO(salida);
        return salidaDTO;
    }


    @Override
    public List<SalidaDTO> findSalidasByFecha(String fecha) throws NotFoundException {
        //NO HAY SALIDAS
        List<Salida> salidas = salidaRepository.findSalidasByFecha(fecha);
        if (salidas.isEmpty())
            throw new NotFoundException("No hay salidas registradas con esa fecha");
        //Conversión de salidas a DTO
        List<SalidaDTO> salidaDTOs = new ArrayList<>();
              for (Salida salida:salidas) {
            SalidaDTO salidaDTO = new SalidaDTO();
            salidaDTO.convierteDTO(salida);
            salidaDTOs.add(salidaDTO);
        }
        return salidaDTOs;
    }

    @Override
    public List<SalidaDTO> findSalidasByBarco(String matricula) throws NotFoundException {
        //NO HAY SALIDAS
        List<Salida> salidas = salidaRepository.findSalidasByBarcoMatricula(matricula);
        if (salidas.isEmpty()) {
            throw new NotFoundException("El barco no tiene salidas registradas");
        }
        //Conversión de salidas a DTO
        List<SalidaDTO> salidaDTOs = new ArrayList<>();
        for (Salida salida:salidas) {
            SalidaDTO salidaDTO = new SalidaDTO();
            salidaDTO.convierteDTO(salida);
            salidaDTOs.add(salidaDTO);
        }
        return salidaDTOs;
    }


    @Override
    public List<SalidaDTO> findSalidasByPatron(int idPatron) throws NotFoundException {
        //NO HAY SALIDAS
        List<Salida> salidas = salidaRepository.findSalidasByPatronId(idPatron);
        if (salidas.isEmpty()) {
            throw new NotFoundException("El patrón no tiene salidas registradas");
        }
        //Conversión de salidas a DTO
        List<SalidaDTO> salidaDTOs = new ArrayList<>();
        for (Salida salida:salidas) {
            SalidaDTO salidaDTO = new SalidaDTO();
            salidaDTO.convierteDTO(salida);
            salidaDTOs.add(salidaDTO);
        }
        return salidaDTOs;
    }

    @Override
    @Transactional
    public SalidaDTO saveSalida(String matricula, int idPatron, Salida salida) throws NotFoundException {
        //EL BARCO NO EXISTE
        Optional<Barco> barcoExistente = barcoRepository.findBarcoByMatriculaIgnoreCase(matricula);
        if (!barcoExistente.isPresent()) {
            throw new NotFoundException("El barco no está registrado");
        }
        //EL PATRÓN NO EXISTE
        Optional<Patron> patronExistente = patronRepository.findPatronById(idPatron);
        if (!patronExistente.isPresent()) {
            throw new NotFoundException("El patrón no está registrado");
        }
        //Se asocian barco y patrón
        Barco barco = barcoExistente.get();
        Patron patron = patronExistente.get();
        salida.setBarco(barco);
        salida.setPatron(patron);
        //Se guarda la salida
        salidaRepository.save(salida);
        //Conversión de salida a DTO
        SalidaDTO salidaDTO = new SalidaDTO();
        salidaDTO.convierteDTO(salida);
        return salidaDTO;
    }


    @Override
    public SalidaDTO updateSalida(int idSalida, Salida salida) throws NotFoundException {
        //LA SALIDA NO EXISTE
        Optional<Salida> salidaExistente = salidaRepository.findSalidaById(idSalida);
        if (!salidaExistente.isPresent()) {
            throw new NotFoundException("No existe la salida");
        }
        //Copio los datos de la salida para su manipulación
        Salida salidaCopia = salidaExistente.get();
        //Los campos se actualizan solo si se pasan en el cuerpo de la solicitud
        if (salida.getFecha() != null) {
            salidaCopia.setFecha(salida.getFecha());
        }
        if (salida.getHora() != null) {
            salidaCopia.setHora(salida.getHora());
        }
        if (salida.getDestino() != null) {
            salidaCopia.setDestino(salida.getDestino());
        }
        if (salida.getBarco() != null) {
            salidaCopia.setBarco(salida.getBarco());
        }
        if (salida.getPatron() != null) {
            salidaCopia.setPatron(salida.getPatron());
        }
        //Se guarda la salida actualizada
        salidaRepository.save(salidaCopia);
        //Conversión de salida a DTO
        SalidaDTO salidaDTO = new SalidaDTO();
        salidaDTO.convierteDTO(salidaCopia);
        return salidaDTO;
    }

    @Override
    public SalidaDTO cambiaBarco(int idSalida, String matricula) throws NotFoundException {
        //LA SALIDA NO EXISTE
        Optional<Salida> salidaExistente = salidaRepository.findSalidaById(idSalida);
        if (!salidaExistente.isPresent()) {
            throw new NotFoundException("No existe la salida");
        }
        //EL BARCO NO EXISTE
        Optional<Barco> barcoExistente = barcoRepository.findBarcoByMatriculaIgnoreCase(matricula);
        if (!barcoExistente.isPresent()) {
            throw new NotFoundException("El barco no está registrado");
        }
        //Se actualiza el barco en la salida
        Salida salidaCopia = salidaExistente.get();
        Barco barco = barcoExistente.get();
        salidaCopia.setBarco(barco);
        //Conversión de salida a DTO
        SalidaDTO salidaDTO = new SalidaDTO();
        salidaDTO.convierteDTO(salidaCopia);
        return salidaDTO;
    }

    @Override
    public SalidaDTO cambiaPatron(int idSalida, int idPatron) throws NotFoundException {
        //LA SALIDA NO EXISTE
        Optional<Salida> salidaExistente = salidaRepository.findSalidaById(idSalida);
        if (!salidaExistente.isPresent()) {
            throw new NotFoundException("No existe la salida");
        }
        //EL PATRON NO EXISTE
        Optional<Patron> patronExistente = patronRepository.findPatronById(idPatron);
        if (!patronExistente.isPresent()) {
            throw new NotFoundException("El barco no está registrado");
        }
        //Se actualiza el barco en la salida
        Salida salidaCopia = salidaExistente.get();
        Patron patron = patronExistente.get();
        salidaCopia.setPatron(patron);
        //Conversión de salida a DTO
        SalidaDTO salidaDTO = new SalidaDTO();
        salidaDTO.convierteDTO(salidaCopia);
        return salidaDTO;
    }


    @Override
    public void deleteSalida(int id) throws NotFoundException {
        //LA SALIDA NO EXISTE
        Optional<Salida> salidaExistente = salidaRepository.findSalidaById(id);
        if (!salidaExistente.isPresent()) {
            throw new NotFoundException("No existe la salida");
        }
        //Borrado de salida
        Salida salida = salidaExistente.get();
        salidaRepository.delete(salida);
    }

}
