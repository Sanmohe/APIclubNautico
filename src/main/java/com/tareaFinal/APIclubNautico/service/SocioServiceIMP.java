package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Patron;
import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
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
            socioDTO.setId(socio.getId());
            socioDTO.setDni(socio.getDni());
            socioDTO.setNombre(socio.getNombre());
            socioDTO.setApellido1(socio.getApellido1());
            socioDTO.setApellido2(socio.getApellido2());
            //Se insertan los valores de la entidad en socioDTO

            // Se obtiene el ID del patrón y se incluye en el DTO
            if (socio.getPatron() != null) {
                //Solo si el socio tiene asignado un patrón
                socioDTO.setIdPatron(socio.getPatron().getId());
            }
            socioDTOs.add(socioDTO);
        }
        return socioDTOs;
    }


    @Override
    public SocioDTO saveSocio(Socio socio) throws AlreadyExistsException {
        Optional<Socio> socioExistente = socioRepository.findSocioByDniIgnoreCase(socio.getDni());
        //Optional se usa para evitar el manejo directo de valores null. Encapsula valores.
        //En lugar de devolver un "null" devuelve un "optional" que indica la posibilidad de ausencia de un valor
        //Si el valor optional existe, hay que usar GET para acceder.
        if (socioExistente.isPresent()) {
            throw new AlreadyExistsException("El socio que intenta crear ya existe");
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
        socioDTO.setId(socioNuevo.getId());
        socioDTO.setDni(socioNuevo.getDni());
        socioDTO.setNombre(socioNuevo.getNombre());
        socioDTO.setApellido1(socioNuevo.getApellido1());
        socioDTO.setApellido2(socioNuevo.getApellido2());

        if (socio.getPatron() != null) {
            //Solo si el socio tiene asignado un patrón
            socioDTO.setIdPatron(socioNuevo.getPatron().getId());
        }
        return socioDTO;
        //Devuelve el DTO
    }


    @Transactional
    @Override
    public SocioDTO updateSocio(int id, Socio socio) throws NotFoundException, AlreadyExistsException {
        Optional<Socio> socioExistente = socioRepository.findSocioById(id);
        //Se busca un socio existente por la ID introducida
        if (!socioExistente.isPresent()) {
            //Si el socio no está presente...
            throw new NotFoundException("El socio no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }

        Optional<Socio> socioMismoDNI = socioRepository.findSocioByDniIgnoreCase(socio.getDni());
        //Se busca un socio existente que tenga el mismo DNI que el proporcionado en la petición de actualización
        if (socioMismoDNI.isPresent() && (socioMismoDNI.get().getId() != id)) {
            throw new AlreadyExistsException("El DNI introducido ya pertenece a otro socio con distinto ID");
            //Si el DNI proporcionado coincide con el DNI de otro Socio (con distinta id), lanza la excepción (el DNI debe ser único)
        }

        //Si no se da ningún caso anterior, se actualiza el socio:
        Socio socioCopia = socioExistente.get();
        //Se extrae una copia del Socio existente y se sustituyen los datos:
        if (Objects.nonNull(socio.getDni()) && !"".equalsIgnoreCase(socio.getDni())) {
            //Comprueba que no se está introduciendo un nombre nulo
            socioCopia.setDni(socio.getDni());
            //Solo entonces sustituye el valor existente por el nuevo
        }
        if (Objects.nonNull(socio.getNombre()) && !"".equalsIgnoreCase(socio.getNombre())) {
            socioCopia.setNombre(socio.getNombre());
        }
        if (Objects.nonNull(socio.getApellido1()) && !"".equalsIgnoreCase(socio.getApellido1())) {
            socioCopia.setApellido1(socio.getApellido1());
        }
        if (Objects.nonNull(socio.getApellido2()) && !"".equalsIgnoreCase(socio.getApellido2())) {
            socioCopia.setApellido2(socio.getApellido2());
        }
        if (Objects.nonNull(socio.getDireccion()) && !"".equalsIgnoreCase(socio.getDireccion())) {
            socioCopia.setDireccion(socio.getDireccion());
        }
        if (Objects.nonNull(socio.getTelefono())) {
            socioCopia.setTelefono(socio.getTelefono());
        }
        if (Objects.nonNull(socio.getEmail()) && !"".equalsIgnoreCase(socio.getEmail())) {
            socioCopia.setEmail(socio.getEmail());
        }

        //Si el socio a actualizar también es patrón, la actualización debe afectar también a dicho patrón (coherencia)
        if (socioCopia.getPatron() != null) {
            //Si el socio existente está asociado a un ID de patrón
            Optional<Patron> patronExistente = patronRepository.findPatronById(socioCopia.getPatron().getId());
            Patron patronCopia = patronExistente.get();
            //Se realiza copia del patron asociado
            patronCopia.setDni(socio.getDni());
            patronCopia.setNombre(socio.getNombre());
            patronCopia.setApellido1(socio.getApellido1());
            patronCopia.setApellido2(socio.getApellido2());
            patronCopia.setDireccion(socio.getDireccion());
            patronCopia.setTelefono(socio.getTelefono());
            patronCopia.setEmail(socio.getEmail());
            patronRepository.save(patronCopia);
            //Se guarda el patrón con los campos actualizados del socio vinculado.
        }

        Socio socioActualizado = socioRepository.save(socioCopia);
        //Guarda el socio actualizado y copia sus datos a socioActualizado (UPDATE)

        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(socioActualizado.getId());
        socioDTO.setDni(socioActualizado.getDni());
        socioDTO.setNombre(socioActualizado.getNombre());
        socioDTO.setApellido1(socioActualizado.getApellido1());
        socioDTO.setApellido2(socioActualizado.getApellido2());

        if (socioCopia.getPatron() != null) {
        //Solo si el socio tiene asignado un patrón
            socioDTO.setIdPatron(socioActualizado.getPatron().getId());
    }
        return socioDTO;
        //Devuelve el DTO
}
    /*
    @Override
    public Socio updateSocio(int id, Socio socio) throws NotFoundException, AlreadyExistsException {
        Optional<Socio> socioExistenteOpcional = socioRepository.findSocioById(id);
        if (!socioExistenteOpcional.isPresent()) {     //Si el socio no está presente...
            throw new NotFoundException("El socio no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }
        Socio socioExistente = socioRepository.findById(id).get();     //Se extrae el Socio existente buscandolo por el "id" introducido
        if (Objects.nonNull(socio.getDni()) && !"".equalsIgnoreCase(socio.getDni())) {        //Comprueba que no se está introduciendo un nombre nulo
            socioExistente.setDni(socio.getDni());                                            //Sustituye el nombre del socio existente por el nuevo
        }
        if (Objects.nonNull(socio.getNombre()) && !"".equalsIgnoreCase(socio.getNombre())) {        //Comprueba que no se está introduciendo un nombre nulo
            socioExistente.setNombre(socio.getNombre());                                            //Sustituye el nombre del socio existente por el nuevo
        }
        if (Objects.nonNull(socio.getApellido1()) && !"".equalsIgnoreCase(socio.getApellido1())) {  //Comprueba que no se está introduciendo un apellido1 nulo
            socioExistente.setApellido1(socio.getApellido1());                                      //Sustituye el apellido1 del socio existente por el nuevo
        }
        if (Objects.nonNull(socio.getApellido2()) && !"".equalsIgnoreCase(socio.getApellido2())) {  //Comprueba que no se está introduciendo un apellido2 nulo
            socioExistente.setApellido2(socio.getApellido2());                                      //Sustituye el apellido2 del socio existente por el nuevo
        }
        if (Objects.nonNull(socio.getDireccion()) && !"".equalsIgnoreCase(socio.getDireccion())) {  //Comprueba que no se está introduciendo una dirección nula
            socioExistente.setDireccion(socio.getDireccion());                                      //Sustituye la dirección del socio existente por la nueva
        }
        if (Objects.nonNull(socio.getTelefono())) {    //Comprueba que no se está introduciendo un teléfono nulo
            socioExistente.setTelefono(socio.getTelefono());                                                        //Sustituye el teléfono del socio existente por el nuevo
        }
        if (Objects.nonNull(socio.getEmail()) && !"".equalsIgnoreCase(socio.getEmail())) {  //Comprueba que no se está introduciendo un email nulo
            socioExistente.setEmail(socio.getEmail());                                      //Sustityue el email del socio existente por el nuevo
        }
        return socioRepository.save(socioExistente);
        //Guarda el socio existente con los cambios realizados (UPDATE)
    }
     */

    @Override
    public void deleteSocio(int id) throws NotFoundException {
        Optional<Socio> socio = socioRepository.findSocioById(id);
        if (!socio.isPresent()) {
            throw new NotFoundException("El socio no está registrado");
        }
        socioRepository.deleteById(id);
        //Llama al método del Repositorio para borrar el socio por el "id" introducido (DELETE)
    }



    //CONSULTAS ESPECÍFICAS

    @Override
    public Socio findSocioById(int id) throws NotFoundException {
        Optional<Socio> socio = socioRepository.findSocioById(id);
        if (!socio.isPresent()) {     //Si el socio no está presente...
            throw new NotFoundException("El socio no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }
        return socio.get();
        //Si el socio existe, devuelve, el objeto Socio contenido en <Optional> mediante "get"
    }

    @Override
    public Socio findSocioByDniWithJPQL(String dni) throws NotFoundException {
        Optional<Socio> socio = socioRepository.findSocioByDniWithJPQL(dni);
        if (!socio.isPresent()) {     //Si el socio no está presente...
            throw new NotFoundException("El socio no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }
        return socio.get();
        //Si el socio existe, devuelve, el objeto Socio contenido en <Optional> mediante "get"
    }

    @Override
    public Socio findSocioByDniIgnoreCase(String dni) throws NotFoundException {
        Optional<Socio> socio = socioRepository.findSocioByDniIgnoreCase(dni);
        if (!socio.isPresent()) {     //Si el socio no está presente...
            throw new NotFoundException("El socio no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }
        return socio.get();
        //Si el socio existe, devuelve, el objeto Socio contenido en <Optional> mediante "get"
    }




}
