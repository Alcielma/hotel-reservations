package com.bd.hotel.reservations.exception.notfound;

import com.bd.hotel.reservations.exception.ApiException;
import org.springframework.http.HttpStatus;

public class HotelNotFoundException extends ApiException {
    public HotelNotFoundException(Long hotelId) {
        super(
                "HOTEL_NOT_FOUND",
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado",
                "Hotel não encontrado com id: " + hotelId
        );
    }
}
