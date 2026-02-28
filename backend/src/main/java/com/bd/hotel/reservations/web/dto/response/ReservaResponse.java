package com.bd.hotel.reservations.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponse {

    private Long id;
    private Long clienteId;
    private LocalDate dataCheckinPrevisto;
    private LocalDate dataCheckoutPrevisto;
    private Set<Long> quartoIds;
    private Set<Long> servicoIds;
}