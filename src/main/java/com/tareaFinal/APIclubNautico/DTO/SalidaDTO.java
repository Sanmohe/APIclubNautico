package com.tareaFinal.APIclubNautico.DTO;

import com.tareaFinal.APIclubNautico.entity.Salida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalidaDTO {
    private int id;
    private String fecha;
    private String hora;
    private String matriculaBarco;
    private int idPatron;
    /*
    DTO para mostrar todos los datos de una Barco y solo la ID de l barco y patron asociados
    De esta forma se evitan los bucles al consultar las Salidas y sus Barcos/Patrones aisgnados.
    Podrían simplificarse los datos arrojados pero lo dejo así por resultarme más didáctico
    a la hora de comprobar el funcionamiento de las operaciones CRUD.
     */

    //Método que convierte la entidad al DTO
    public void convierteDTO(Salida salida) {
        id = salida.getId();
        fecha = salida.getFecha();
        hora = salida.getHora();
        matriculaBarco = salida.getBarco().getMatricula();
        idPatron = salida.getPatron().getId();
    }

}
