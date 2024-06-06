package com.tareaFinal.APIclubNautico.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data                   //Genera automáticamente getters and setters
@AllArgsConstructor     //Genera automáticamente un constructor con un argumento por atributo/campo
@NoArgsConstructor      //Genera automáticamente un constructor sin argumentos
@Builder                //Aplica el patrón de diseño "builder"
@Entity                 //Indica que la clase es una entidad JPA que se mapea a la BD
@Table(name="barco")    //Indica la tabla a la que se mapea ("name" debe coincidir con el nombre exacto de la tabla en la BD)
public class Barco {
    @Id                                                     //Indica la PK de la entidad
    private String matricula;

    @NotBlank (message ="Campo obligatorio")
    @Column
    private String nombre;

    @Column
    private int numAmarre;

    @Column
    private Double cuota;

    @ManyToOne
    @JoinColumn(name = "idSocio", nullable = false)
    //Indica que la es relación 1:N (un barco solo puede y debe pertenecer a un Socio)
    private Socio socio;

    @OneToMany(mappedBy = "barco", cascade = {CascadeType.ALL})
    //Indica que la es relación 1:N (Un barco puede tener varias salidas)
    // "mappedBy" indica que la relación está mapeada por "barco" en la entidad relacionada
    // "CascadeType.ALL" indica que todas las operaciones de persistencia se reflejan en Salida
    // Si se borra un barco, se borran también sus salidas asociadas
    private List<Salida> salidas;

}



