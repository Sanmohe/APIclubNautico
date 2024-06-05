package com.tareaFinal.APIclubNautico.error.dto;

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
    private int idPatron;

}
