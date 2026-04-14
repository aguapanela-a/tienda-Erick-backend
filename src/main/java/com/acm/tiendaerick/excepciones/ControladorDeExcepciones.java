package com.acm.tiendaerick.excepciones;

import com.acm.tiendaerick.dtoCompartido.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


@RestControllerAdvice
public class ControladorDeExcepciones {

    //manejar errores de negocio
     @ExceptionHandler(ExcepcionesTienda.class)
    public ResponseEntity<ErrorDTO> manejarErroresDeNegocio(ExcepcionesTienda ex){

         ErrorDTO errorTienda = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()); //Crea el objeto ErrorDTO llenando cada espacio con lo que gnera la excepción

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorTienda);
     }

     //Para manejar los errores de validación, como por ejemplo que llegó un json con un campo nulo que no debía estar nulo
     @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidacionesDeDatos(MethodArgumentNotValidException ex) {
         //Extracción de mensajes importantes
         String detalles = ex.getBindingResult().getFieldErrors().stream()
                 .map(error ->error.getField() + ": " + error.getDefaultMessage())
                 .collect(Collectors.joining());

         ErrorDTO errorValidacion = new ErrorDTO(HttpStatus.BAD_REQUEST.value(),"Error de validación:"+ detalles);

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorValidacion);
     }

     //Para manejar errores inesperados
     @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarCualquierError(Exception ex) {
         ErrorDTO error = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Ocurrió un error interno en el servidor. Inténtalo más tarde.");

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
     }
}
