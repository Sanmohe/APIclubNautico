package com.tareaFinal.APIclubNautico.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   //Genera automáticamente getters and setters
@AllArgsConstructor     //Genera automáticamente un constructor con un argumento por atributo/campo
@NoArgsConstructor      //Genera automáticamente un constructor sin argumentos
@Builder                //Aplica el patrón de diseño "builder"
@Entity                 //Indica que la clase es una entidad JPA que se mapea a la BD
@Table(name="socio")    //Indica la tabla a la que se mapea ("name" debe coincidir con el nombre exacto de la tabla en la BD)
public class Socio {
    @Id                                                 //Indica la PK de la entidad
    @GeneratedValue(strategy = GenerationType.AUTO)     //Indica que se genera automáticamente
    private int id_socio;

    @Column
    private String nombre;

    @Column
    private String apellido1;

    @Column
    private String apellido2;

    @Column
    private String direccion;

    @Column
    private int telefono;

    @Column
    private String email;
}
