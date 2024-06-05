package com.tareaFinal.APIclubNautico.service;

import com.tareaFinal.APIclubNautico.entity.Patron;
import com.tareaFinal.APIclubNautico.entity.Socio;
import com.tareaFinal.APIclubNautico.error.AlreadyExistsException;
import com.tareaFinal.APIclubNautico.error.NotFoundException;
import com.tareaFinal.APIclubNautico.repository.PatronRepository;
import com.tareaFinal.APIclubNautico.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Patron> findAllPatrones() {
        return patronRepository.findAll();
        //Llama al método del Repositorio para listar todos los Patrons (READ)
    }


    @Override
    public Patron savePatron(Patron patron) throws AlreadyExistsException {
        Optional<Patron> patronExistente = patronRepository.findPatronByDniIgnoreCase(patron.getDni());
        //Optional se usa para evitar el manejo directo de valores null. Encapsula valores.
        //En lugar de devolver un "null" devuelve un "optional" que indica la posibilidad de ausencia de un valor
        //Si el valor optional existe, hay que usar GET para acceder.
        if (patronExistente.isPresent()) {
            throw new AlreadyExistsException("El patron que intenta crear ya existe");
            //Si el patron ya está registrado con ese DNI, la operación lanza la excepción.
        }

        Optional<Socio> socioExistente = socioRepository.findSocioByDniIgnoreCase(patron.getDni());
        if (socioExistente.isPresent()) {
            //Si ya existe un patrón con el mismo DNI...
            Socio socioCopia = socioExistente.get();
            //Se crea una copia de dicho patrón
            patron.setSocio(socioCopia);                 //Se introduce el socio (referencia)
            patron.setNombre(socioCopia.getNombre());
            patron.setApellido1(socioCopia.getApellido1());
            patron.setApellido2(socioCopia.getApellido2());
            patron.setDireccion(socioCopia.getDireccion());
            patron.setTelefono(socioCopia.getTelefono());
            patron.setEmail(socioCopia.getEmail());
            //Sustituye los datos del patrón por los del socio existente, para que exista coherencia
            socioCopia.setPatron(patron);
            //En el socio se incluye el patron creado como referencia


        }
        return patronRepository.save(patron);
        //Guarda el socio (CREATE)
    }

    /*
    @Override
    public Patron savePatron(Patron patron) throws AlreadyExistsException {
        Optional<Patron> patronExistente = patronRepository.findPatronByDniIgnoreCase(patron.getDni());
        //Optional se usa para evitar el manejo directo de valores null. Encapsula valores.
        //En lugar de devolver un "null" devuelve un "optional" que indica la posibilidad de ausencia de un valor
        //Si el valor optional existe, hay que usar GET para acceder.
        if (patronExistente.isPresent()) {
            throw new AlreadyExistsException("El patron que intenta crear ya existe");
        }
        return patronRepository.save(patron);
        //Guarda el patron (CREATE)
    }
    */


    @Override
    public Patron updatePatron(int id, Patron patron) throws NotFoundException {
        Optional<Patron> patronExistenteOpcional = patronRepository.findPatronById(id);
        if (!patronExistenteOpcional.isPresent()) {     //Si el patron no está presente...
            throw new NotFoundException("El patron no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }
        Patron patronExistente = patronRepository.findById(id).get();     //Se extrae el Patron existente buscandolo por el "id" introducido
        if (Objects.nonNull(patron.getDni()) && !"".equalsIgnoreCase(patron.getDni())) {        //Comprueba que no se está introduciendo un nombre nulo
            patronExistente.setDni(patron.getDni());                                            //Sustituye el nombre del patron existente por el nuevo
        }
        if (Objects.nonNull(patron.getNombre()) && !"".equalsIgnoreCase(patron.getNombre())) {        //Comprueba que no se está introduciendo un nombre nulo
            patronExistente.setNombre(patron.getNombre());                                            //Sustituye el nombre del patron existente por el nuevo
        }
        if (Objects.nonNull(patron.getApellido1()) && !"".equalsIgnoreCase(patron.getApellido1())) {  //Comprueba que no se está introduciendo un apellido1 nulo
            patronExistente.setApellido1(patron.getApellido1());                                      //Sustituye el apellido1 del patron existente por el nuevo
        }
        if (Objects.nonNull(patron.getApellido2()) && !"".equalsIgnoreCase(patron.getApellido2())) {  //Comprueba que no se está introduciendo un apellido2 nulo
            patronExistente.setApellido2(patron.getApellido2());                                      //Sustituye el apellido2 del patron existente por el nuevo
        }
        if (Objects.nonNull(patron.getDireccion()) && !"".equalsIgnoreCase(patron.getDireccion())) {  //Comprueba que no se está introduciendo una dirección nula
            patronExistente.setDireccion(patron.getDireccion());                                      //Sustituye la dirección del patron existente por la nueva
        }
        if (Objects.nonNull(patron.getTelefono())) {    //Comprueba que no se está introduciendo un teléfono nulo
            patronExistente.setTelefono(patron.getTelefono());                                                        //Sustituye el teléfono del patron existente por el nuevo
        }
        if (Objects.nonNull(patron.getEmail()) && !"".equalsIgnoreCase(patron.getEmail())) {  //Comprueba que no se está introduciendo un email nulo
            patronExistente.setEmail(patron.getEmail());                                      //Sustityue el email del patron existente por el nuevo
        }
        return patronRepository.save(patronExistente);
        //Guarda el patron existente con los cambios realizados (UPDATE)
    }

    @Override
    public void deletePatron(int id) throws NotFoundException {
        Optional<Patron> patron = patronRepository.findPatronById(id);
        if (!patron.isPresent()) {
            throw new NotFoundException("El patron no está registrado");
        }
        patronRepository.deleteById(id);
        //Llama al método del Repositorio para borrar el patron por el "id" introducido (DELETE)
    }

    //CONSULTAS ESPECÍFICAS

    @Override
    public Patron findPatronById(int id) throws NotFoundException {
        Optional<Patron> patron = patronRepository.findPatronById(id);
        if (!patron.isPresent()) {     //Si el patron no está presente...
            throw new NotFoundException("El patron no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }
        return patron.get();
        //Si el patron existe, devuelve, el objeto Patron contenido en <Optional> mediante "get"
    }

    @Override
    public Patron findPatronByDniIgnoreCase(String dni) throws NotFoundException {
        Optional<Patron> patron = patronRepository.findPatronByDniIgnoreCase(dni);
        if (!patron.isPresent()) {     //Si el patron no está presente...
            throw new NotFoundException("El patron no está registrado");
            //Instancia la excepción con su mensaje de respuesta
        }
        return patron.get();
        //Si el patron existe, devuelve, el objeto Patron contenido en <Optional> mediante "get"
    }
}
