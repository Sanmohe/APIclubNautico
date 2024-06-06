package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Patron;
import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import com.tareaFinal.APIclubNautico.DTO.SocioDTO;
import com.tareaFinal.APIclubNautico.repository.PatronRepository;
import com.tareaFinal.APIclubNautico.repository.SocioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//Indica que es un Servicio de Spring. Spring la detectará y gestionará como un bean.
public class SocioServiceIMP implements SocioService {

    @Autowired
    //Inyección de dependencias
    SocioRepository socioRepository;
    ///Se inyecta automáticamente una instancia del Repositorio en la variable
    //Permite que el controlador use los métodos del Repositorio sin necesidad de instanciarlo manualmente.

    @Autowired
    //Inyección de dependencias
    PatronRepository patronRepository;
    ///Se inyecta automáticamente una instancia del Repositorio en la variable
    //Permite que el controlador use los métodos del Repositorio sin necesidad de instanciarlo manualmente.


    @Override
    public List<SocioDTO> findAllSocios() {
        List<Socio> socios = socioRepository.findAll();
        List<SocioDTO> socioDTOs = new ArrayList<>();

        for (Socio socio : socios) {
            SocioDTO socioDTO = new SocioDTO();
            socioDTO.convierteDTO(socio);
            //Este método pasa todos los valores de cada socio a un socioDTO
            socioDTOs.add(socioDTO);
            //Se añade cada socioDTO a la lista
        }
        return socioDTOs;
    }


    @Override
    public SocioDTO saveSocio(Socio socio) throws AlreadyExistsException {
        //EL DNI PERTENECE A OTRO SOCIO REGISTRADO
        Optional<Socio> socioExistente = socioRepository.findSocioByDniIgnoreCase(socio.getDni());
        //Optional se usa para evitar el manejo directo de valores null. Encapsula valores.
        //En lugar de devolver un "null" devuelve un "optional" que indica la posibilidad de ausencia de un valor
        //Si el valor optional existe, hay que usar GET para acceder.
        if (socioExistente.isPresent()) {
            throw new AlreadyExistsException("Ya existe un socio registrado con ese DNI");
            //Si el socio ya está registrado con ese DNI, la operación lanza la excepción.
        }

        Optional<Patron> patronExistente = patronRepository.findPatronByDniIgnoreCase(socio.getDni());
        if (patronExistente.isPresent()) {
            //Si ya existe un patrón con el mismo DNI...
            Patron patronCopia = patronExistente.get();
            //Se crea una copia de dicho patrón
            socio.setPatron(patronCopia);                   //Se introduce el patrón (referencia)
            socio.setNombre(patronCopia.getNombre());
            socio.setApellido1(patronCopia.getApellido1());
            socio.setApellido2(patronCopia.getApellido2());
            socio.setDireccion(patronCopia.getDireccion());
            socio.setTelefono(patronCopia.getTelefono());
            socio.setEmail(patronCopia.getEmail());
            //Sustituye los datos del socio por los del patrón existente, para que exista coherencia
            patronCopia.setSocio(socio);
            //En el patrón se incluye el socio creado como referencia
        }

        Socio socioNuevo = socioRepository.save(socio);
        //Guarda el socio y copia sus datos a socioNuevo (SAVE)

        SocioDTO socioDTO = new SocioDTO();
        socioDTO.convierteDTO(socioNuevo);
        //Este método pasa todos los valores de socioNuevo a socioDTO

        return socioDTO;
        //Devuelve el DTO
    }


    @Transactional
    @Override
    public SocioDTO updateSocio(int id, Socio socio) throws NotFoundException, AlreadyExistsException {
        //EL SOCIO NO EXISTE
        Optional<Socio> socioExistente = socioRepository.findSocioById(id);
        //Se busca un socio existente por la ID introducida
        if (!socioExistente.isPresent()) {
            //Si el socio no está presente...
            throw new NotFoundException("El socio no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }

        //EL DNI PERTENECE A OTRO SOCIO REGISTRADO
        Optional<Socio> socioMismoDNI = socioRepository.findSocioByDniIgnoreCase(socio.getDni());
        //Se busca un socio existente que tenga el mismo DNI que el proporcionado en la petición de actualización
        if (socioMismoDNI.isPresent() && (socioMismoDNI.get().getId() != id)) {
            throw new AlreadyExistsException("El DNI introducido ya pertenece a otro socio con distinto ID");
            //Si el DNI proporcionado coincide con el DNI de otro Socio (con distinta id), lanza la excepción (el DNI debe ser único)
        }

        //Si no se da ningún caso anterior, se actualiza el socio:
        Socio socioCopia = socioExistente.get();
        //Se extrae una copia del Socio existente y se sustituyen los datos:
        if (socio.getDni() != null && !"".equalsIgnoreCase(socio.getDni())) {
            //Comprueba que no se está introduciendo un campo nulo o vacío
            socioCopia.setDni(socio.getDni());
            //Solo entonces sustituye el valor existente por el nuevo
        }
        if (socio.getNombre() != null && !"".equalsIgnoreCase(socio.getNombre())) {
            socioCopia.setNombre(socio.getNombre());
        }
        if (socio.getApellido1() != null && !"".equalsIgnoreCase(socio.getApellido1())) {
            socioCopia.setApellido1(socio.getApellido1());
        }
        if (socio.getApellido2() != null && !"".equalsIgnoreCase(socio.getApellido2())) {
            socioCopia.setApellido2(socio.getApellido2());
        }
        if (socio.getDireccion() != null && !"".equalsIgnoreCase(socio.getDireccion())) {
            socioCopia.setDireccion(socio.getDireccion());
        }
        if (socio.getTelefono() != 0) {
            socioCopia.setTelefono(socio.getTelefono());
        }
        if (socio.getEmail() != null && !"".equalsIgnoreCase(socio.getEmail())) {
            socioCopia.setEmail(socio.getEmail());
        }
        if (socio.getPatron() != null) {
            socioCopia.setPatron(socio.getPatron());
        }

        //Si el socio a actualizar también es patrón, la actualización debe afectar también a dicho patrón (coherencia)
        if (socioCopia.getPatron() != null) {
            //Si el socio existente está asociado a un ID de patrón
            Optional<Patron> patronExistente = patronRepository.findPatronById(socioCopia.getPatron().getId());
            Patron patronCopia = patronExistente.get();
            //Se realiza copia del patron asociado
            patronCopia.setDni(socioCopia.getDni());
            patronCopia.setNombre(socioCopia.getNombre());
            patronCopia.setApellido1(socioCopia.getApellido1());
            patronCopia.setApellido2(socioCopia.getApellido2());
            patronCopia.setDireccion(socioCopia.getDireccion());
            patronCopia.setTelefono(socioCopia.getTelefono());
            patronCopia.setEmail(socioCopia.getEmail());
            patronRepository.save(patronCopia);
            //Se guarda el patrón con los campos actualizados del socio vinculado.
        }

        socioRepository.save(socioCopia);
        //Guarda el socio actualizado (UPDATE)

        SocioDTO socioDTO = new SocioDTO();
        socioDTO.convierteDTO(socioCopia);
        //Este método pasa todos los valores de socioCopia a socioDTO

        return socioDTO;
        //Devuelve el DTO
}

    @Override
    public void deleteSocio(int id) throws NotFoundException {
        //EL SOCIO NO EXISTE
        Optional<Socio> socio = socioRepository.findSocioById(id);
        if (!socio.isPresent()) {
            throw new NotFoundException("El socio no está registrado");
        }

        Socio socioBorrado = socio.get();
        if (socioBorrado.getPatron() !=null) {
            //Si el socio tiene un patrón vinculado
            Patron patron = socioBorrado.getPatron();
            //Se realiza copia del patrón
            patron.setSocio(null);
            //Se desvincula el patrón del socio
            socioBorrado.setPatron(null);
            //Se desvincula el patrón del socio
            patronRepository.save(patron);
            //Se guarda el patron actualizado
        }

        socioRepository.deleteById(id);
        //Llama al método del Repositorio para borrar el socio por el "id" introducido (DELETE)
    }



    //CONSULTAS ESPECÍFICAS

    @Override
    public SocioDTO findSocioById(int id) throws NotFoundException {
        Optional<Socio> socio = socioRepository.findSocioById(id);
        if (!socio.isPresent()) {     //Si el socio no está presente...
            throw new NotFoundException("El socio no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }
        Socio socioBuscado = socio.get();
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.convierteDTO(socioBuscado);
        //Este método pasa todos los valores de socioBuscado a socioDTO

        return socioDTO;
    }

    @Override
    public SocioDTO findSocioByDniWithJPQL(String dni) throws NotFoundException {
        Optional<Socio> socio = socioRepository.findSocioByDniWithJPQL(dni);
        if (!socio.isPresent()) {     //Si el socio no está presente...
            throw new NotFoundException("El socio no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }

        Socio socioBuscado = socio.get();
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.convierteDTO(socioBuscado);
        //Este método pasa todos los valores de socioBuscado a socioDTO

        return socioDTO;
    }

    @Override
    public SocioDTO findSocioByDniIgnoreCase(String dni) throws NotFoundException {
        Optional<Socio> socio = socioRepository.findSocioByDniIgnoreCase(dni);
        if (!socio.isPresent()) {     //Si el socio no está presente...
            throw new NotFoundException("El socio no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }

        Socio socioBuscado = socio.get();
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.convierteDTO(socioBuscado);
        //Este método pasa todos los valores de socioBuscado a socioDTO

        return socioDTO;
    }


}
