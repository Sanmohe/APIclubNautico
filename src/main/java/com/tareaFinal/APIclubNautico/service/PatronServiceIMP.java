package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Patron;
import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import com.tareaFinal.APIclubNautico.error.dto.PatronDTO;
import com.tareaFinal.APIclubNautico.error.dto.SocioDTO;
import com.tareaFinal.APIclubNautico.repository.PatronRepository;
import com.tareaFinal.APIclubNautico.repository.SocioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
//Indica que es un Servicio de Spring. Spring la detectará y gestionará como un bean.
public class PatronServiceIMP implements PatronService {

    @Autowired
    //Inyección de dependencias
    PatronRepository patronRepository;
    ///Se inyecta automáticamente una instancia del Repositorio en la variable
    //Permite que el controlador use los métodos del Repositorio sin necesidad de instanciarlo manualmente.

    @Autowired
    //Inyección de dependencias
    SocioRepository socioRepository;
    ///Se inyecta automáticamente una instancia del Repositorio en la variable
    //Permite que el controlador use los métodos del Repositorio sin necesidad de instanciarlo manualmente.


    @Override
    public List<PatronDTO> findAllPatrones() {
        List<Patron> patrones = patronRepository.findAll();
        List<PatronDTO> patronDTOs = new ArrayList<>();

        for (Patron patron : patrones) {
            PatronDTO patronDTO = new PatronDTO();
            patronDTO.convierteDTO(patron);
            //Este método pasa todos los valores de cada patron a un patronDTO
            patronDTOs.add(patronDTO);
            //Se añade cada patronDTO a la lista
        }
        return patronDTOs;
    }


    @Override
    public PatronDTO savePatron(Patron patron) throws AlreadyExistsException {
        //EL DNI PERTENECE A OTRO PATRON REGISTRADO
        Optional<Patron> patronExistente = patronRepository.findPatronByDniIgnoreCase(patron.getDni());
        //Optional se usa para evitar el manejo directo de valores null. Encapsula valores.
        //En lugar de devolver un "null" devuelve un "optional" que indica la posibilidad de ausencia de un valor
        //Si el valor optional existe, hay que usar GET para acceder.
        if (patronExistente.isPresent()) {
            throw new AlreadyExistsException("Ya existe un patrón registrado con ese DNI");
            //Si el patron ya está registrado con ese DNI, la operación lanza la excepción.
        }

        Optional<Socio> socioExistente = socioRepository.findSocioByDniIgnoreCase(patron.getDni());
        if (socioExistente.isPresent()) {
            //Si ya existe un socio con el mismo DNI...
            Socio socioCopia = socioExistente.get();
            //Se crea una copia de dicho socio
            patron.setSocio(socioCopia);                   //Se introduce el patrón (referencia)
            patron.setNombre(socioCopia.getNombre());
            patron.setApellido1(socioCopia.getApellido1());
            patron.setApellido2(socioCopia.getApellido2());
            patron.setDireccion(socioCopia.getDireccion());
            patron.setTelefono(socioCopia.getTelefono());
            patron.setEmail(socioCopia.getEmail());
            //Sustituye los datos del patron por los del socio existente, para que exista coherencia
            socioCopia.setPatron(patron);
            //En el socio se incluye el patron creado como referencia
        }

        Patron patronNuevo = patronRepository.save(patron);
        //Guarda el patron y copia sus datos a patronNuevo (SAVE)

        PatronDTO patronDTO = new PatronDTO();
        patronDTO.convierteDTO(patronNuevo);
        //Este método pasa todos los valores de patronNuevo a patronDTO

        return patronDTO;
        //Devuelve el DTO
    }


    @Transactional
    @Override
    public PatronDTO updatePatron(int id, Patron patron) throws NotFoundException, AlreadyExistsException {
        //EL PATRON YA EXISTE
        Optional<Patron> patronExistente = patronRepository.findPatronById(id);
        //Se busca un patron existente por la ID introducida
        if (!patronExistente.isPresent()) {
            //Si el patron no está presente...
            throw new NotFoundException("El patron no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }

        //EL DNI PERTENECE A OTRO PATRON REGISTRADO
        Optional<Patron> patronMismoDNI = patronRepository.findPatronByDniIgnoreCase(patron.getDni());
        //Se busca un patron existente que tenga el mismo DNI que el proporcionado en la petición
        if (patronMismoDNI.isPresent() && (patronMismoDNI.get().getId() != id)) {
            throw new AlreadyExistsException("El DNI introducido ya pertenece a otro patron con distinto ID");
            //Si el DNI proporcionado coincide con el DNI de otro Patron (con distinta id), lanza la excepción (el DNI debe ser único)
        }

        //Si no se da ningún caso anterior, se actualiza el patron:
        Patron patronCopia = patronExistente.get();
        //Se extrae una copia del Patron existente y se sustituyen los datos:
        if (patron.getDni() != null && !"".equalsIgnoreCase(patron.getDni())) {
            //Comprueba que no se está introduciendo un campo nulo
            patronCopia.setDni(patron.getDni());
            //Solo entonces sustituye el valor existente por el nuevo
        }
        if (patron.getNombre() != null && !"".equalsIgnoreCase(patron.getNombre())) {
            patronCopia.setNombre(patron.getNombre());
        }
        if (patron.getApellido1() != null && !"".equalsIgnoreCase(patron.getApellido1())) {
            patronCopia.setApellido1(patron.getApellido1());
        }
        if (patron.getApellido2() != null && !"".equalsIgnoreCase(patron.getApellido2())) {
            patronCopia.setApellido2(patron.getApellido2());
        }
        if (patron.getDireccion() != null && !"".equalsIgnoreCase(patron.getDireccion())) {
            patronCopia.setDireccion(patron.getDireccion());
        }
        if (patron.getTelefono() != 0) {
            patronCopia.setTelefono(patron.getTelefono());
        }
        if (patron.getEmail() != null && !"".equalsIgnoreCase(patron.getEmail())) {
            patronCopia.setEmail(patron.getEmail());
        }
        if (patron.getSocio() != null) {
            patronCopia.setSocio(patron.getSocio());
        }

        //Si el patron a actualizar también es socio, la actualización debe afectar también a dicho socio (coherencia)
        if (patronCopia.getSocio() != null) {
            //Si el patron existente está asociado a un ID de socio
            Optional<Socio> socioExistente = socioRepository.findSocioById(patronCopia.getSocio().getId());
            Socio socioCopia = socioExistente.get();
            //Se realiza copia del socio vinculado
            socioCopia.setDni(patronCopia.getDni());
            socioCopia.setNombre(patronCopia.getNombre());
            socioCopia.setApellido1(patronCopia.getApellido1());
            socioCopia.setApellido2(patronCopia.getApellido2());
            socioCopia.setDireccion(patronCopia.getDireccion());
            socioCopia.setTelefono(patronCopia.getTelefono());
            socioCopia.setEmail(patronCopia.getEmail());
            socioRepository.save(socioCopia);
            //Se guarda el socio con los campos actualizados del patron vinculado.
        }

        Patron patronActualizado = patronRepository.save(patronCopia);
        //Guarda el patron actualizado y copia sus datos a patronActualizado (UPDATE)

        PatronDTO patronDTO = new PatronDTO();
        patronDTO.convierteDTO(patronActualizado);
        //Este método pasa todos los valores de patronActualizado a patronDTO

        return patronDTO;
        //Devuelve el DTO
    }


    @Override
    public void deletePatron(int id) throws NotFoundException {
        //EL PATRON NO EXISTE
        Optional<Patron> patron = patronRepository.findPatronById(id);
        if (!patron.isPresent()) {
            throw new NotFoundException("El patron no está registrado");
        }

        Patron patronBorrado = patron.get();
        if (patronBorrado.getSocio() !=null) {
            //Si el patron tiene un patrón vinculado
            Socio socio = patronBorrado.getSocio();
            //Se realiza copia del socio
            socio.setPatron(null);
            //Se desvincula el patrón del socio
            patronBorrado.setSocio(null);
            //Se desvincula el socio del patron
            socioRepository.save(socio);
            //Se guarda el socio actualizado
        }

        patronRepository.deleteById(id);
        //Llama al método del Repositorio para borrar el patron por el "id" introducido (DELETE)
    }



    //CONSULTAS ESPECÍFICAS

    @Override
    public PatronDTO findPatronById(int id) throws NotFoundException {
        Optional<Patron> patron = patronRepository.findPatronById(id);
        if (!patron.isPresent()) {     //Si el patron no está presente...
            throw new NotFoundException("El patron no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }
        Patron patronBuscado = patron.get();
        PatronDTO patronDTO = new PatronDTO();
        patronDTO.convierteDTO(patronBuscado);
        //Este método pasa todos los valores de patronBuscado a patronDTO

        return patronDTO;
    }

    @Override
    public PatronDTO findPatronByDniIgnoreCase(String dni) throws NotFoundException {
        Optional<Patron> patron = patronRepository.findPatronByDniIgnoreCase(dni);
        if (!patron.isPresent()) {     //Si el patron no está presente...
            throw new NotFoundException("El patron no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }

        Patron patronBuscado = patron.get();
        PatronDTO patronDTO = new PatronDTO();
        patronDTO.convierteDTO(patronBuscado);
        //Este método pasa todos los valores de patronBuscado a patronDTO

        return patronDTO;
    }

}
