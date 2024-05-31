package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import com.tareaFinal.APIclubNautico.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service                                    //Indica que es un Servicio de Spring. Spring la detectará y gestionará como un bean.
public class SocioServiceIMP implements SocioService {

    @Autowired                              //Inyección de dependencias
    SocioRepository socioRepository;
    ///Se inyecta automáticamente una instancia del Repositorio en la variable
    //Permite que el controlador use los métodos del Repositorio sin necesidad de instanciarlo manualmente.

    @Override
    public List<Socio> findAllSocios() {
        return socioRepository.findAll();   //Llama al método del Repositorio para listar todos los Socios (READ)
    }

    @Override
    public Socio saveSocio(Socio socio) throws AlreadyExistsException {
        Optional<Socio> socioExistente = socioRepository.findSocioByDniIgnoreCase(socio.getDni());
        if (socioExistente.isPresent()) {
            throw new AlreadyExistsException("El socio que intenta crear ya existe");
        }
        return socioRepository.save(socio);
    }

    @Override
    public Socio updateSocio(int id, Socio socio) {
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
        return socioRepository.save(socioExistente);    //Guarda el socio existente con los cambios realizados (UPDATE)
    }

    @Override
    public void deleteSocio(int id) {
        socioRepository.deleteById(id);                  //Llama al método del Repositorio para borrar el socio por el "id" introducido (DELETE)
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
