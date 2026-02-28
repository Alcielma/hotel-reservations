package com.bd.hotel.reservations.application.service;

import com.bd.hotel.reservations.persistence.repository.QuartosDisponiveisViewRepository;
import com.bd.hotel.reservations.persistence.repository.QuartosDisponiveisViewRowDto;
import com.bd.hotel.reservations.web.dto.response.QuartoDisponivelResponse;
import com.bd.hotel.reservations.web.mapper.QuartoDisponivelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuartoService {

    private final QuartosDisponiveisViewRepository viewRepo;
    private final QuartoDisponivelMapper mapper;

    @Transactional(readOnly = true)
    public List<QuartoDisponivelResponse> listarDisponiveis(
            LocalDate start,
            LocalDate endExclusive,
            Long hotelId
    ) {

        List<QuartosDisponiveisViewRowDto> rows =
                viewRepo.listarDisponiveis(start, endExclusive, hotelId);

        if (rows.isEmpty()) return List.of();

        String dataExibicaoMock =
                start == null || endExclusive == null
                        ? null
                        : start + " - " + endExclusive;

        List<QuartoDisponivelResponse> out = new ArrayList<>(rows.size());
        for (QuartosDisponiveisViewRowDto r : rows) {
            out.add(mapper.toResponse(r, dataExibicaoMock));
        }

        return out;
    }
}