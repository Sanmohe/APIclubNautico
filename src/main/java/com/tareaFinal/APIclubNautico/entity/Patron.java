package com.tareaFinal.APIclubNautico.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data                   //Genera automáticamente getters and setters
@AllArgsConstructor     //Genera automáticamente un constructor con un argumento por atributo/campo
@NoArgsConstructor      //Genera automáticamente un constructor sin argumentos
@Builder                //Aplica el patrón de diseño "builder"
@Entity                 //Indica que la clase es una entidad JPA que se mapea a la BD
@Table(name = "patron") //Indica la tabla a la que se mapea ("name" debe coincidir con el nombre exacto de la tabla en la BD)
public class Patron {
    @Id                                                     //Indica la PK de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //Indica que se genera automáticamente
    private int id;
    //Decido no usar el DNI como PK, para poder tener un registro de socios d ela propia aplicación
    //Un identificador de socio propio del ClubNáutico

    @NotBlank(message = "Campo obligatorio")    //Indica que el campo no pueda dejarse en blanco o nulo
    @Length(min = 9, max = 9, message = "Introduzca un DNI válido")     //Valida el valor del DNI
    @Column(unique = true, nullable = false)   //Especifica que el DNI debe ser único en la BD
    private String dni;

    @NotBlank(message = "Campo obligatorio")    //Indica que el campo no pueda dejarse en blanco o nulo
    @Column
    private String nombre;

    @NotBlank(message = "Campo obligatorio")    //Indica que el campo no pueda dejarse en blanco o nulo
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

    @OneToOne
    //Indica la relación 1:1
    @JoinColumn(name = "idSocio", referencedColumnName = "id")
    //Indica el campo de la BD que actúa como clave foránea (idSocio)
    //y la referencia al campo correspondiente de la otra entidad (id)
    private Socio socio;

}
