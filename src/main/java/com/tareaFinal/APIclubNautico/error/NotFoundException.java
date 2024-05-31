package com.tareaFinal.APIclubNautico.error;

public class NotFoundException extends Exception {
    //La clase se convierte en una excepción comprobada que debe declararse con "throws" o manejada en un bloque "try-catch"
    //En este caso, maneja el error cuando no se encuentra un objeto en una búsqueda
    public NotFoundException(String message) {    //Constructor implementado de Exception
        super(message);     //Llama al constructor d ela superclase con el mensaje
    }
}
