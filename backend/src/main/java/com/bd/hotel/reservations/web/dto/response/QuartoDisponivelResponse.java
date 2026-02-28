package com.bd.hotel.reservations.web.dto.response;

import java.math.BigDecimal;

public record QuartoDisponivelResponse(
        Long id,
        String numero,
        String tipo,
        Integer capacidade,
        BigDecimal preco,
        String dataExibicao,
        String image
) {}