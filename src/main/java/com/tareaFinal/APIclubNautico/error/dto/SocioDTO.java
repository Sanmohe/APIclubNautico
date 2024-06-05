package com.tareaFinal.APIclubNautico.error.dto;

import com.tareaFinal.APIclubNautico.entity.Socio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocioDTO {
    private int id;
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String direccion;
    private int telefono;
    private String email;
    private int idPatron;
      /*
    DTO para mostrar todos los datos de un Socio y solo la ID de su patron vinculado en una respuesta.
    De esta forma se evitan los bucles al consulta los Socios o Patrones anidados.
    Podrían simplificarse los datos arrojados pero lo dejo así por resultarme más didáctico
    a la hora de comprobar el funcionamiento de las operaciones CRUD.
     */

    public void convierteDTO (Socio socio) {
        id = socio.getId();
        dni = socio.getDni();
        nombre= socio.getNombre();
        apellido1 = socio.getApellido1();
        apellido2 = socio.getApellido2();
        direccion = socio.getDireccion();
        telefono = socio.getTelefono();
        email = socio.getEmail();
        if (socio.getPatron() != null) {
            idPatron = socio.getPatron().getId();
        } else {
            idPatron = 0;
        }
    }
}
