package com.bd.hotel.reservations.application.service;

import com.bd.hotel.reservations.persistence.entity.Cliente;
import com.bd.hotel.reservations.persistence.entity.Quarto;
import com.bd.hotel.reservations.persistence.entity.Reserva;
import com.bd.hotel.reservations.persistence.repository.*; // Importando os repositórios
import com.bd.hotel.reservations.web.dto.request.ReservaRequest;
import com.bd.hotel.reservations.web.dto.response.ReservasDetalhadasResponse;
import com.bd.hotel.reservations.web.mapper.ReservaDetalhadaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservasDetalhadasViewRepository viewRepo;
    private final ClienteRepository clienteRepo;
    private final ReservaRepository reservaRepo; // Adicionado
    private final QuartoRepository quartoRepo;   // Adicionado
    private final ReservaDetalhadaMapper mapper;

    // --- MÉTODOS DO SEU COLEGA ---

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
    
    @Transactional
    public Reserva salvar(ReservaRequest dto) {
        validarDatas(dto.getDataCheckinPrevisto(), dto.getDataCheckoutPrevisto());

        Reserva reserva = new Reserva();
        
        // Só busca o cliente se o ID for fornecido
        if (dto.getClienteId() != null) {
            Cliente cliente = clienteRepo.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            reserva.setCliente(cliente);
        }

        List<Quarto> quartos = quartoRepo.findAllById(dto.getQuartoIds());
        if (quartos.isEmpty()) {
            throw new RuntimeException("Pela menos um quarto deve ser selecionado");
        }

        reserva.setDataCheckinPrevisto(dto.getDataCheckinPrevisto());
        reserva.setDataCheckoutPrevisto(dto.getDataCheckoutPrevisto());
        reserva.setQuartos(new HashSet<>(quartos));

        return reservaRepo.save(reserva);
    }

    @Transactional
    public Reserva atualizar(Long id, ReservaRequest dto) {
        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        validarDatas(dto.getDataCheckinPrevisto(), dto.getDataCheckoutPrevisto());

        List<Quarto> quartos = quartoRepo.findAllById(dto.getQuartoIds());
        
        reserva.setDataCheckinPrevisto(dto.getDataCheckinPrevisto());
        reserva.setDataCheckoutPrevisto(dto.getDataCheckoutPrevisto());
        reserva.setQuartos(new HashSet<>(quartos));

        return reservaRepo.save(reserva);
    }

    @Transactional
    public void deletar(Long id) {
        if (!reservaRepo.existsById(id)) {
            throw new RuntimeException("Reserva não encontrada");
        }
        reservaRepo.deleteById(id);
    }

    private void validarDatas(LocalDate checkin, LocalDate checkout) {
        LocalDate hoje = LocalDate.now();
        if (checkin.isBefore(hoje)) {
            throw new RuntimeException("Check-in não pode ser no passado");
        }
        if (checkout.isBefore(checkin) || checkout.isEqual(checkin)) {
            throw new RuntimeException("Check-out deve ser após o check-in");
        }
    }
}