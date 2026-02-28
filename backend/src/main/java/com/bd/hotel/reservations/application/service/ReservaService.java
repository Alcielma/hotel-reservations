package com.bd.hotel.reservations.application.service;

import com.bd.hotel.reservations.persistence.entity.Cliente;
import com.bd.hotel.reservations.persistence.repository.ClienteRepository;
import com.bd.hotel.reservations.persistence.repository.ReservasDetalhadasViewRepository;
import com.bd.hotel.reservations.persistence.repository.ReservasDetalhadasViewRowDto;
import com.bd.hotel.reservations.web.dto.response.ReservasDetalhadasResponse;
import com.bd.hotel.reservations.web.mapper.ReservaDetalhadaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservasDetalhadasViewRepository viewRepo;
    private final ClienteRepository clienteRepo;
    private final ReservaDetalhadaMapper mapper;

    @Transactional(readOnly = true)
    public List<ReservasDetalhadasResponse> listarReservasDetalhadas() {

        List<ReservasDetalhadasViewRowDto> rows = viewRepo.listarTudo();
        if (rows == null || rows.isEmpty()) {
            return List.of();
        }

        List<Long> clienteIds = rows.stream()
                .map(ReservasDetalhadasViewRowDto::clienteId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        Map<Long, String> emailPorClienteId = new HashMap<>();

        if (!clienteIds.isEmpty()) {
            List<Cliente> clientes = clienteRepo.findAllByIdIn(clienteIds);

            for (Cliente c : clientes) {
                if (c.getUser() != null && c.getUser().getEmail() != null) {
                    emailPorClienteId.put(c.getId(), c.getUser().getEmail());
                }
            }
        }

        List<ReservasDetalhadasResponse> out = new ArrayList<>(rows.size());

        for (ReservasDetalhadasViewRowDto row : rows) {
            String clientEmail = emailPorClienteId.get(row.clienteId());
            out.add(mapper.toResponse(row, clientEmail));
        }

        return out;
    }
}