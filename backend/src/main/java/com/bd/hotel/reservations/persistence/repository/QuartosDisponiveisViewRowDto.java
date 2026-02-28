package com.bd.hotel.reservations.persistence.repository;

import java.math.BigDecimal;

public record QuartosDisponiveisViewRowDto(
        Long quartoId,
        String numero,
        String tipo,
        Integer capacidade,
        BigDecimal preco
) {}