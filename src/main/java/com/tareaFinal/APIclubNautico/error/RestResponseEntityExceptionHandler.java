package com.tareaFinal.APIclubNautico.error;

import com.tareaFinal.APIclubNautico.error.dto.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice   //Indica que la clase captura y maneja las excepciones lanzadas en cualquier controlador de la app
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class) //El método manejará la excepción de "objeto no encontrado"
    @ResponseStatus(HttpStatus.NOT_FOUND)           //El estado HTTP de la respuesta será del tipo "NOT FOUND"
    public ResponseEntity<ErrorMessage> NotFoundException(NotFoundException exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        //Crea un objeto ErrorMessage con el estado HTTP.NOT_FOUND y el mensaje de la excepción
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        //Devuelve un ResponseEntity con el estado HTTP NOT FOUND y el cuerpo del mensaje.
    }

    @ExceptionHandler(AlreadyExistsException.class) //El método manejará la excepción de "socio no encontrado"
    @ResponseStatus(HttpStatus.CONFLICT)           //El estado HTTP de la respuesta será del tipo "CONFLICT"
    public ResponseEntity<ErrorMessage> AlreadyExistsException(AlreadyExistsException exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        //Crea un objeto ErrorMessage con el estado HTTP.CONFLICT y el mensaje de la excepción
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        //Devuelve un ResponseEntity con el estado HTTP NOT FOUND y el cuerpo del mensaje.
    }


    /*
    En la siguiente excepción no se usa @ExceceptionHandler porque Spring por defecto ya maneja medianamente la excepción.
    En este caso, se sobrecarga el método que usa ResponseEntityExceptionHandler para manejar la excepción
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        //Almacena los errores de validacion. La clave es el nombre del campo con el error y el valor es el mensaje asociado
        ex.getBindingResult().getFieldErrors().forEach(error ->{
           errors.put(error.getField(),error.getDefaultMessage());
        });
        /*
        Esto itera sobre los errores de campo (FieldError), introduciendo en el mapa
        el nombre del campo (error.getField()) y el mensaje del error asociado (error.getDefaultMessage).
         */
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        //Devuelve una respuesta HTTP 400 (Bad Request) con el cuerpo que contiene el mapa de errores
    }
}
