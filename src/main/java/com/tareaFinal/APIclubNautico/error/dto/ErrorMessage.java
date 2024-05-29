package com.tareaFinal.APIclubNautico.error.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data                   //Genera automáticamente getters, setters y métodos toString, equals...
@AllArgsConstructor     //Genera automáticamente un constructor con un argumento para cada campo
@NoArgsConstructor      //Genera automáticamente un constructor sin argumentos

public class ErrorMessage {     //Sirve de plantilla para mostrar errores al frontend/cliente
    private HttpStatus status;  //Representa el estado HTTP de la respuesta: error interno del servidor, not found, bad request...
    private String message;     //Mensaje de error que se devuelve

}
