package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Barco;
import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import com.tareaFinal.APIclubNautico.DTO.BarcoDTO;
import com.tareaFinal.APIclubNautico.repository.BarcoRepository;
import com.tareaFinal.APIclubNautico.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//Indica que es un Servicio de Spring. Spring la detectará y gestionará como un bean.
public class BarcoServiceIMP implements BarcoService {

    @Autowired
    //Inyección de dependencias
    BarcoRepository barcoRepository;
    ///Se inyecta automáticamente una instancia del Repositorio en la variable
    //Permite que el controlador use los métodos del Repositorio sin necesidad de instanciarlo manualmente.

    @Autowired
    //Inyección de dependencias
    SocioRepository socioRepository;
    ///Se inyecta automáticamente una instancia del Repositorio en la variable
    //Permite que el controlador use los métodos del Repositorio sin necesidad de instanciarlo manualmente.


    @Override
    public List<BarcoDTO> findAllBarcos() {
        List<Barco> barcos = barcoRepository.findAll();
        List<BarcoDTO> barcoDTOs = new ArrayList<>();
        //Conversión de salidas a DTO
        for (Barco barco:barcos) {
            BarcoDTO barcoDTO = new BarcoDTO();
            barcoDTO.convierteDTO(barco);
            //Este método pasa todos los valores de cada barco a un barcoDTO
            barcoDTOs.add(barcoDTO);
            //Se añade cada barcoDTO a la lista
        }
        return barcoDTOs;
    }


    @Override
    public BarcoDTO saveBarco(int idSocio, Barco barco) throws NotFoundException, AlreadyExistsException {
        //LA MATRÍCULA YA PERTENECE A OTRO BARCO REGISTRADO
        Optional<Barco> barcoExistente = barcoRepository.findBarcoByMatriculaIgnoreCase(barco.getMatricula());
        if (barcoExistente.isPresent()) {
            throw new AlreadyExistsException("Ya existe un barco registrado con esa matrícula");
        }

        //EL SOCIO PROPIETARIO NO EXISTE
        Optional<Socio> socioExistente = socioRepository.findSocioById(idSocio);
        if (!socioExistente.isPresent()) {
            throw new NotFoundException("El socio al que asigna el barco no existe");
        }

        Socio socioPropietario = socioExistente.get();
        barco.setSocio(socioPropietario);
        //Se asigna el socio existente al barco

        barcoRepository.save(barco);
        //Se guarda el barco

        BarcoDTO barcoDTO = new BarcoDTO();
        barcoDTO.convierteDTO(barco);
        return barcoDTO;
    }


    @Override
    public BarcoDTO updateBarco(String matricula, Barco barco) throws NotFoundException {
        //EL BARCO NO EXISTE
        Optional<Barco> barcoExistente = barcoRepository.findBarcoByMatriculaIgnoreCase(matricula);
        if (!barcoExistente.isPresent()) {
            throw new NotFoundException("El barco no está registrado");
        }

        Barco barcoCopia = barcoExistente.get();
        if (barco.getNombre() != null && !"".equalsIgnoreCase(barco.getNombre())) {
            barcoCopia.setNombre(barco.getNombre());
        }
        if (barco.getNumAmarre() != 0) {
            barcoCopia.setNumAmarre(barco.getNumAmarre());
        }
        if (barco.getCuota() != null) {
            barcoCopia.setCuota(barco.getCuota());
        }
        if (barco.getSocio() != null) {
            barcoCopia.setSocio(barco.getSocio());
        }
        //Los datos se actualizan solo si se proporcionan valores no nulos o no vacíos
        //En caso contrario, se mantienen los datos existentes

        barcoRepository.save(barcoCopia);
        //Se guarda el barco actualizado

        BarcoDTO barcoDTO = new BarcoDTO();
        barcoDTO.convierteDTO(barcoCopia);
        return barcoDTO;
    }

    @Override
    public BarcoDTO cambiaPropietarioBarco(String matricula, int idSocio) throws NotFoundException {
        //EL BARCO NO EXISTE
        Optional<Barco> barcoExistente = barcoRepository.findBarcoByMatriculaIgnoreCase(matricula);
        if (!barcoExistente.isPresent()) {
            throw new NotFoundException("El barco no está registrado");
        }

        //EL SOCIO NO EXISTE
        Optional<Socio> socioExistente = socioRepository.findSocioById(idSocio);
        if (!socioExistente.isPresent()) {
            throw new NotFoundException("El socio que se quiere asignar no está registrado");
        }

        Barco barcoCopia = barcoExistente.get();
        Socio nuevoPropietario = socioExistente.get();
        barcoCopia.setSocio(nuevoPropietario);
        //Se actualiza el socio propietario

        barcoRepository.save(barcoCopia);
        //Se guarda el barco actualizado

        BarcoDTO barcoDTO = new BarcoDTO();
        barcoDTO.convierteDTO(barcoCopia);
        return barcoDTO;
    }



    @Override
    public void deleteBarco(String matricula) throws NotFoundException {
        //EL BARCO NO EXISTE
        Optional<Barco> barcoExistente = barcoRepository.findBarcoByMatriculaIgnoreCase(matricula);
        if (!barcoExistente.isPresent()) {
            throw new NotFoundException("El barco no está registrado");
        }

        Barco barcoBorrado = barcoExistente.get();
        barcoRepository.delete(barcoBorrado);
    }


    //CONSULTAS ESPECÍFICAS

    @Override
    public BarcoDTO findBarcoByMatriculaIgnoreCase(String matricula) throws NotFoundException {
        Optional<Barco> barco = barcoRepository.findBarcoByMatriculaIgnoreCase(matricula);
        if (!barco.isPresent()) {
            //Si el barco no está presente
            throw new NotFoundException("El barco no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }
        Barco barcoBuscado = barco.get();
        BarcoDTO barcoDTO = new BarcoDTO();
        barcoDTO.convierteDTO(barcoBuscado);
        //Este método pasa todos los valores de barcoBuscado a barcoDTO

        return barcoDTO;
    }

    @Override
    public BarcoDTO findBarcoByNombreIgnoreCase(String nombre) throws NotFoundException {
        Optional<Barco> barco = barcoRepository.findSocioByNombreIgnoreCase(nombre);
        if (!barco.isPresent()) {
            throw new NotFoundException("El barco no está registrado");
        }

        Barco barcoBuscado = barco.get();
        BarcoDTO barcoDTO = new BarcoDTO();
        barcoDTO.convierteDTO(barcoBuscado);
        //Este método pasa todos los valores de barcoBuscado a barcoDTO

        return barcoDTO;
    }


}
