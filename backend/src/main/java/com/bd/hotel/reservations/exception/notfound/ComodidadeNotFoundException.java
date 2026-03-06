package com.bd.hotel.reservations.exception.notfound;

import com.bd.hotel.reservations.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ComodidadeNotFoundException extends ApiException {
    public ComodidadeNotFoundException(Long id) {
        super(
                "COMODIDADE_NOT_FOUND",
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado",
                "Comodidade não encontrada com o ID: " + id
        );
    }
}