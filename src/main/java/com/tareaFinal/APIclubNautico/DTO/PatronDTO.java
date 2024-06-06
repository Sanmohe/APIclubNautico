package com.tareaFinal.APIclubNautico.DTO;

import com.tareaFinal.APIclubNautico.entity.Patron;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatronDTO {
    private int id;
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String direccion;
    private int telefono;
    private String email;
    private int idSocio;
    /*
    DTO para mostrar todos los datos de un Patron y solo la ID de su socio vinculado en una respuesta
    Podrían simplificarse los datos arrojados pero lo dejo así por resultarme más didáctico
    a la hora de comprobar el funcionamiento de las operaciones CRUD
     */

    //Método que convierte la entidad al DTO
    public void convierteDTO (Patron patron) {
        id = patron.getId();
        dni = patron.getDni();
        nombre= patron.getNombre();
        apellido1 = patron.getApellido1();
        apellido2 = patron.getApellido2();
        direccion = patron.getDireccion();
        telefono = patron.getTelefono();
        email = patron.getEmail();
        if (patron.getSocio() != null) {
            idSocio = patron.getSocio().getId();
        } else {
            idSocio = 0;
        }
    }
}

