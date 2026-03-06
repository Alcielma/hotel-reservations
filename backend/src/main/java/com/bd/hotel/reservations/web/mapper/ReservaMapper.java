package com.bd.hotel.reservations.web.mapper;

import com.bd.hotel.reservations.persistence.entity.Quarto;
import com.bd.hotel.reservations.persistence.entity.Reserva;
import com.bd.hotel.reservations.web.dto.response.ReservaResponse;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ReservaMapper {

    public ReservaResponse toResponse(Reserva reserva) {
        Long quartoId = reserva.getQuartos()
                .stream()
                .findFirst()
                .map(Quarto::getId)
                .orElse(null);

        return new ReservaResponse(
                reserva.getId(),
                reserva.getCliente().getId(),
                reserva.getDataCheckinPrevisto(),
                reserva.getDataCheckoutPrevisto(),
                quartoId,
                Set.of()
        );
    }
}