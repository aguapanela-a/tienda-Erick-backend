package com.acm.tiendaerick.excepciones;

//Para errores manuales -> como que un cliente no puedetener cierto nombre, o un abono es mayor que la deuda, etc.
public class ExcepcionesTienda extends RuntimeException {
    public ExcepcionesTienda(String message) {
        super(message);
    }
}
