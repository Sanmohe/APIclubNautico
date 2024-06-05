package com.tareaFinal.APIclubNautico.error.dto;

import com.tareaFinal.APIclubNautico.entity.Barco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarcoDTO {
    private String matricula;
    private String nombre;
    private int numAmarre;
    private double cuota;
    private int idSocio;
    /*
    DTO para mostrar todos los datos de un Barco y solo la ID de su propietario
    De esta forma se evitan los bucles al consultae los Barcos y sus Socios aisgnados.
    Podrían simplificarse los datos arrojados pero lo dejo así por resultarme más didáctico
    a la hora de comprobar el funcionamiento de las operaciones CRUD.
     */

    public void convierteDTO(Barco barco) {
        matricula = barco.getMatricula();
        nombre = barco.getNombre();
        numAmarre = barco.getNumAmarre();
        cuota = barco.getCuota();
        idSocio = barco.getSocio().getId();
    }
}
