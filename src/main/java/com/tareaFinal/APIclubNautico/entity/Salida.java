package com.tareaFinal.APIclubNautico.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   //Genera automáticamente getters and setters
@AllArgsConstructor     //Genera automáticamente un constructor con un argumento por atributo/campo
@NoArgsConstructor      //Genera automáticamente un constructor sin argumentos
@Builder                //Aplica el patrón de diseño "builder"
@Entity                 //Indica que la clase es una entidad JPA que se mapea a la BD
@Table(name="salida")    //Indica la tabla a la que se mapea
public class Salida {

    @Id                                                     //Indica la PK de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //Indica que se genera automáticamente
    private int id;

    @NotBlank(message ="Campo obligatorio")
    @Column
    private String fecha;

    @NotBlank(message ="Campo obligatorio")
    @Column
    private String hora;

    @Column
    private String destino;


    @ManyToOne
    @JoinColumn(name = "idBarco", nullable = false)
    //Indica que la es relación 1:N (una salida tiene asociada un único Barco)
    private Barco barco;

    @ManyToOne
    @JoinColumn(name = "idPatron", nullable = false)
    //Indica que la es relación 1:N (una salida tiene asociada un único Patrón)
    private Patron patron;

}
