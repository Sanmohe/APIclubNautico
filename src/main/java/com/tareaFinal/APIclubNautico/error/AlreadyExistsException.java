package com.tareaFinal.APIclubNautico.error;

public class AlreadyExistsException extends Exception {
    //La clase se convierte en una excepción comprobada que debe declararse con "throws" o manejada en un bloque "try-catch"
    //En este caso, maneja el error cuando ya existe un objeto que se quiere crear
    public AlreadyExistsException(String message) {    //Constructor implementado de Exception
        super(message);     //Llama al constructor de la superclase con el mensaje
    }
}
